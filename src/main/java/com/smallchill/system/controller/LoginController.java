/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.system.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.LoginLog;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.constant.Const;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.captcha.Captcha;
import com.smallchill.core.toolbox.kit.LogKit;
import com.smallchill.core.toolbox.log.LogManager;
import com.smallchill.system.meta.intercept.LoginValidator;

@Controller
public class LoginController extends BaseController implements Const{

	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/")
	public String index() {
		return indexRealPath;
	}
	
	/**
	 * GET 登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		if (ShiroKit.isAuthenticated()) {
			return redirect + "/";
		}
		return loginRealPath;
	}

	/**
	 * POST 登录
	 */
	@Before(LoginValidator.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(HttpServletRequest request, HttpServletResponse response) {
		String account = getParameter("account");
		String password = getParameter("password");
		String imgCode = getParameter("imgCode");
		
		if (!Captcha.validate(request, response, imgCode)) {
			return error("验证码错误");
		}
		Subject currentUser = ShiroKit.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
		token.setRememberMe(false);
		try {
			currentUser.login(token);
			Session session = ShiroKit.getSession();
			LogKit.println("sessionID	: {} ", session.getId());
			LogKit.println("sessionHost	: {}", session.getHost());
			LogKit.println("sessionTimeOut	: {}", session.getTimeout());
		} catch (UnknownAccountException e) {
			log.error("账号不存在：{}", e);
			return error("账号不存在");
		} catch (DisabledAccountException e) {
			log.error("账号未启用：{}", e);
			return error("账号未启用");
		} catch (IncorrectCredentialsException e) {
			log.error("密码错误：{}", e);
			return error("密码错误");
		} catch (RuntimeException e) {
			log.error("未知错误,请联系管理员：{}", e);
			return error("未知错误,请联系管理员");
		}
		doLog(ShiroKit.getSession(), "登录");
		return success("登录成功");
	}

	@RequestMapping("/logout")
	public String logout() {
		doLog(ShiroKit.getSession(), "登出");
		Subject currentUser = ShiroKit.getSubject();
		currentUser.logout();
		return redirect + "/login";
	}

	@RequestMapping(value = "/unauth")
	public String unauth() {
		if (ShiroKit.notAuthenticated()) {
			return redirect + "/login";
		}
		return noPermissionPath;
	}

	@RequestMapping("/captcha")
	public void captcha(HttpServletResponse response) {
		Captcha.init(response).render();
	}

	public void doLog(Session session, String type){
		if(!LogManager.isDoLog()){
			return;
		}
		try{
			LoginLog log = new LoginLog();
			String msg = Func.format("[sessionID]: {} [sessionHost]: {} [sessionHost]: {}", session.getId(), session.getHost(), session.getTimeout());
			log.setLogname(type);
			log.setMethod(msg);
			log.setCreatetime(new Date());
			log.setSucceed("1");
			log.setUserid(Func.toStr(ShiroKit.getUser().getId()));
			Blade.create(LoginLog.class).save(log);
		}catch(Exception ex){
			LogKit.logNothing(ex);
		}
	}
	
}
