package com.xxl.sso.sample.controller;

import com.chigua.sso.core.conf.Conf;
import com.chigua.sso.core.entity.ReturnT;
import com.chigua.sso.core.user.ChiGuaSsoUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxueli 2017-08-01 21:39:47
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public ReturnT<ChiGuaSsoUser> index(HttpServletRequest request) {
        ChiGuaSsoUser xxlUser = (ChiGuaSsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT<ChiGuaSsoUser>(xxlUser);
    }

}