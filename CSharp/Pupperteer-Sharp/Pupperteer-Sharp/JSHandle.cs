﻿using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json.Linq;
using PuppeteerSharp.Helpers;
using PuppeteerSharp.Messaging;

namespace PuppeteerSharp
{
    /// <summary>
    /// JSHandle represents an in-page JavaScript object. JSHandles can be created with the <see cref="Page.EvaluateExpressionHandleAsync(string)"/> and <see cref="Page.EvaluateFunctionHandleAsync(string, object[])"/> methods.
    /// </summary>
    public class JSHandle
    {
        internal JSHandle(ExecutionContext context, CDPSession client, JToken remoteObject)
        {
            ExecutionContext = context;
            Client = client;
            Logger = Client.Connection.LoggerFactory.CreateLogger(GetType());
            RemoteObject = remoteObject;
        }

        /// <summary>
        /// Gets the execution context.
        /// </summary>
        /// <value>The execution context.</value>
        public ExecutionContext ExecutionContext { get; }
        /// <summary>
        /// Gets or sets a value indicating whether this <see cref="JSHandle"/> is disposed.
        /// </summary>
        /// <value><c>true</c> if disposed; otherwise, <c>false</c>.</value>
        public bool Disposed { get; private set; }
        /// <summary>
        /// Gets or sets the remote object.
        /// </summary>
        /// <value>The remote object.</value>
        public JToken RemoteObject { get; }
        /// <summary>
        /// Gets the client.
        /// </summary>
        /// <value>The client.</value>
        protected CDPSession Client { get; }
        /// <summary>
        /// Gets the logger.
        /// </summary>
        /// <value>The logger.</value>
        protected ILogger Logger { get; }

        /// <summary>
        /// Fetches a single property from the referenced object
        /// </summary>
        /// <param name="propertyName">property to get</param>
        /// <returns>Task of <see cref="JSHandle"/></returns>
        public async Task<JSHandle> GetPropertyAsync(string propertyName)
        {
            var objectHandle = await ExecutionContext.EvaluateFunctionHandleAsync(@"(object, propertyName) => {
              const result = { __proto__: null};
              result[propertyName] = object[propertyName];
              return result;
            }", this, propertyName).ConfigureAwait(false);
            var properties = await objectHandle.GetPropertiesAsync().ConfigureAwait(false);
            properties.TryGetValue(propertyName, out var result);
            await objectHandle.DisposeAsync().ConfigureAwait(false);
            return result;
        }

        /// <summary>
        /// Returns a <see cref="Dictionary{TKey, TValue}"/> with property names as keys and <see cref="JSHandle"/> instances for the property values.
        /// </summary>
        /// <returns>Task which resolves to a <see cref="Dictionary{TKey, TValue}"/></returns>
        /// <example>
        /// <code>
        /// var handle = await page.EvaluateExpressionHandle("({window, document})");
        /// var properties = await handle.GetPropertiesAsync();
        /// var windowHandle = properties["window"];
        /// var documentHandle = properties["document"];
        /// await handle.DisposeAsync();
        /// </code>
        /// </example>
        public async Task<Dictionary<string, JSHandle>> GetPropertiesAsync()
        {
            var response = await Client.SendAsync("Runtime.getProperties", new
            {
                objectId = RemoteObject[MessageKeys.ObjectId].AsString(),
                ownProperties = true
            }).ConfigureAwait(false);

            var result = new Dictionary<string, JSHandle>();

            foreach (var property in response[MessageKeys.Result])
            {
                if (property[MessageKeys.Enumerable] == null)
                {
                    continue;
                }

                result.Add(property[MessageKeys.Name].AsString(), ExecutionContext.CreateJSHandle(property[MessageKeys.Value]));
            }
            return result;
        }

        /// <summary>
        /// Returns a JSON representation of the object
        /// </summary>
        /// <returns>Task</returns>
        /// <remarks>
        /// The method will return an empty JSON if the referenced object is not stringifiable. It will throw an error if the object has circular references
        /// </remarks>
        public async Task<object> JsonValueAsync() => await JsonValueAsync<object>().ConfigureAwait(false);

        /// <summary>
        /// Returns a JSON representation of the object
        /// </summary>
        /// <typeparam name="T">A strongly typed object to parse to</typeparam>
        /// <returns>Task</returns>
        /// <remarks>
        /// The method will return an empty JSON if the referenced object is not stringifiable. It will throw an error if the object has circular references
        /// </remarks>
        public async Task<T> JsonValueAsync<T>()
        {
            var objectId = RemoteObject[MessageKeys.ObjectId];

            if (objectId != null)
            {
                var response = await Client.SendAsync("Runtime.callFunctionOn", new Dictionary<string, object>
                {
                    ["functionDeclaration"] = "function() { return this; }",
                    [MessageKeys.ObjectId] = objectId,
                    ["returnByValue"] = true,
                    ["awaitPromise"] = true
                }).ConfigureAwait(false);
                return (T)RemoteObjectHelper.ValueFromRemoteObject<T>(response[MessageKeys.Result]);
            }

            return (T)RemoteObjectHelper.ValueFromRemoteObject<T>(RemoteObject);
        }

        /// <summary>
        /// Disposes the Handle. It will mark the JSHandle as disposed and release the <see cref="JSHandle.RemoteObject"/>
        /// </summary>
        /// <returns>The async.</returns>
        public async Task DisposeAsync()
        {
            if (Disposed)
            {
                return;
            }

            Disposed = true;
            await RemoteObjectHelper.ReleaseObject(Client, RemoteObject, Logger).ConfigureAwait(false);
        }

        /// <inheritdoc/>
        public override string ToString()
        {
            if ((RemoteObject)[MessageKeys.ObjectId] != null)
            {
                var type = RemoteObject[MessageKeys.Subtype] ?? RemoteObject[MessageKeys.Type];
                return "JSHandle@" + type;
            }

            return "JSHandle:" + RemoteObjectHelper.ValueFromRemoteObject<object>(RemoteObject)?.ToString();
        }

        internal object FormatArgument(ExecutionContext context)
        {
            if (ExecutionContext != context)
            {
                throw new PuppeteerException("JSHandles can be evaluated only in the context they were created!");
            }

            if (Disposed)
            {
                throw new PuppeteerException("JSHandle is disposed!");
            }

            var unserializableValue = RemoteObject[MessageKeys.UnserializableValue];

            if (unserializableValue != null)
            {
                return unserializableValue;
            }

            if (RemoteObject[MessageKeys.ObjectId] == null)
            {
                var value = RemoteObject[MessageKeys.Value];

                return new { value };
            }

            var objectId = RemoteObject[MessageKeys.ObjectId];

            return new { objectId };
        }
    }
}