from user_test2 import UserInfo;
from UserDao import UserDao;

if __name__ == "__main__":
    print("this is main function");
    userId = 11000;
    userDao = UserDao();
    userInfo = userDao.getUserInfoById(userId);
    if userInfo != None:
        print("query result :", userInfo);
        userInfo.name = "Happy Huang, updated";
        userid = userDao.updateUserInfo(userInfo);
        print("update user({user}) successfully".format(user=userInfo));
    else:
        print("Can not find any user by user id=", userId);
        userInfo = UserInfo("bobo H, insert", "address");
        userid = userDao.updateUserInfo(userInfo);
        print("insert a new user({user}) successfully".format(user=userInfo));
