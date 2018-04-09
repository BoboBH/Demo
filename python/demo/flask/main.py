from flask import Flask,Response,json;
from config import DevConfig
#import json;

app = Flask(__name__)
app.config.from_object("config.DevConfig")
print(json.__file__);

tempObj = {'name':'bobo huang'};
globalJsonString = json.dumps(tempObj);

@app.route('/')
def home():
    return '<h1>Hello World!</h1>'

@app.route('/bobo')
def bobo():
    return '<h1>Hello Bobo Huang!</h1>'

@app.route('/ross/')
def ross():
    return '<h1>Hello CRM/ROSS/CMSg!</h1>'

@app.route('/json/')
def httpjson():
    jsonObj = {
        'name':'bobo huang',
        'mobile':'13632794089'
    };
    return Response(json.dumps(jsonObj), mimetype='application/json');

#print(app.__file__);
if __name__ == '__main__':
    app.run(port=DevConfig.PORT)