FROM microsoft/dotnet:2.2-aspnetcore-runtime AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM microsoft/dotnet:2.2-sdk AS build
WORKDIR /src
COPY ["csharp/AppDocker/AppDocker.csproj", "csharp/AppDocker/"]
RUN dotnet restore "csharp/AppDocker/AppDocker.csproj"
COPY . .
WORKDIR "/src/csharp/AppDocker"
RUN dotnet build "AppDocker.csproj" -c Release -o /app

FROM build AS publish
RUN dotnet publish "AppDocker.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "AppDocker.dll"]