from flask import Flask,Response,json,request;
from config import DevConfig
#import json;

myapp = Flask(__name__)
myapp.config.from_object("config.DevConfig")
print(json.__file__);

tempObj = {'name':'bobo huang'};
globalJsonString = json.dumps(tempObj);

@myapp.route('/')
def home():
    return '<h1>Hello World!</h1>'

@myapp.route('/bobo/')
def bobo():
    return '<h1>Hello Bobo Huang!</h1>'
    
@myapp.route('/sayhello/<string:name>')
def sayhello(name):
    return '<h1>Hello {name}!</h1>'.format(name=name)

@myapp.route('/post/<string:name>', methods=['POST'])
def post(name):
    data = request.data;
    print(data);
    return '<h1>Hello {name}!</h1>'.format(name=name)

@myapp.route('/ross/')
def ross():
    return '<h1>Hello CRM/ROSS/CMSg!</h1>'

@myapp.route('/json/')
def httpjson():
    jsonObj = {
        'name':'bobo huang',
        'mobile':'13632794089'
    };
    return Response(json.dumps(jsonObj), mimetype='application/json');

#print(app.__file__);
if __name__ == '__main__':
    myapp.run(port=DevConfig.PORT)