package com.chigua.sso.server.service;

import com.chigua.sso.server.core.model.UserInfo;
import com.chigua.sso.server.core.result.ReturnT;

public interface UserService {

    public ReturnT<UserInfo> findUser(String username, String password);

}
