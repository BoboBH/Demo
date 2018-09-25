import sys;
sys.path.append("..");
from sqlAlchemy.UserDao import UserDao;
from sqlAlchemy.user_test2 import UserInfo;
from flask import Flask,Response,json,request;
from config import DevConfig;
from flask.ext.restful import Api, Resource;

myapp = Flask(__name__)
myapp.config.from_object("config.DevConfig")
api = Api(myapp);
print(json.__file__);

class User(object):
    name = "";
    def __init__(self, name):
        self.name = name;
    def __str__(self):
        return "User: name={name}".format(name=self.name);

class UserAPI(Resource):
    userInfoDao = None;
    def __init__(self):
        userInfoDao = UserDao();
    def get(self, name):
        return json.dumps({"name":name});
    def post(self):
        json_data = request.data;
        print(json_data);
        jobj = json.loads(json_data);
        userInfo = UserInfo();
        for key in jobj.keys:
            setattr(userInfo, key, jobj[key]);
        userId =  userInfoDao.updateUserInfo(userInfo);
        return "post:add a user(userid={userid})!".format(name=userid);
    def delete(self, name):
        return json.dumps({"name":name});
    def put(self, name):
        return "put:Hello {name}!".format(name=name);
api.add_resource(UserAPI, "/users/<string:name>", endpoint="users");
if __name__ == "__main__":    
    myapp.run(port=DevConfig.PORT);