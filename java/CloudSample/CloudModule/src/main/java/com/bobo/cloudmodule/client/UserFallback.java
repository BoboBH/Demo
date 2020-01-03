package com.bobo.cloudmodule.client;

import com.bobo.cloudmodule.bean.ResponseUser;
import com.bobo.cloudmodule.bean.User;

import feign.hystrix.FallbackFactory;

public class UserFallback implements FallbackFactory<UserFeignClient>{

	@Override
    public UserFeignClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new UserFeignClient() {
            @Override
            public ResponseUser findById(Integer id) {
            	User user = new User(0, "Default User");
            	ResponseUser response = new ResponseUser(user);
                return response;
            }

            @Override
            public ResponseUser addUser(User user) {
            	ResponseUser response = new ResponseUser(user);
                return response;
            }
        };
    }

}
