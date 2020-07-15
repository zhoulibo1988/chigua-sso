package com.chigua.sso.server.controller;

import com.chigua.sso.core.login.SsoTokenLoginHelper;
import com.chigua.sso.core.store.SsoLoginStore;
import com.chigua.sso.core.user.ChiGuaSsoUser;
import com.chigua.sso.core.store.SsoSessionIdHelper;
import com.chigua.sso.server.core.model.UserInfo;
import com.chigua.sso.server.core.result.ReturnT;
import com.chigua.sso.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * sso server (for app)
 *
 * @author xuxueli 2018-04-08 21:02:54
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;


    /**
     * Login
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ReturnT<String> login(String username, String password) {

        // valid login
        ReturnT<UserInfo> result = userService.findUser(username, password);
        if (result.getCode() != ReturnT.SUCCESS_CODE) {
            return new ReturnT<String>(result.getCode(), result.getMsg());
        }

        // 1、make xxl-sso user
        ChiGuaSsoUser xxlUser = new ChiGuaSsoUser();
        xxlUser.setUserid(String.valueOf(result.getData().getUserid()));
        xxlUser.setUsername(result.getData().getUsername());
        xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        xxlUser.setExpireMinute(SsoLoginStore.getRedisExpireMinute());
        xxlUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);

        // 3、login, store storeKey
        SsoTokenLoginHelper.login(sessionId, xxlUser);

        // 4、return sessionId
        return new ReturnT<String>(sessionId);
    }


    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ReturnT<String> logout(String sessionId) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(sessionId);
        return ReturnT.SUCCESS;
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logincheck")
    @ResponseBody
    public ReturnT<ChiGuaSsoUser> logincheck(String sessionId) {

        // logout
        ChiGuaSsoUser xxlUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (xxlUser == null) {
            return new ReturnT<ChiGuaSsoUser>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<ChiGuaSsoUser>(xxlUser);
    }

}
