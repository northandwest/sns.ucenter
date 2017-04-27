package com.bucuoa.passport.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bucuoa.passport.base.enums.ResultCode;
import com.bucuoa.passport.base.utils.Constant;
import com.bucuoa.passport.base.utils.ErrorReport;

@Controller
@RequestMapping("/")
public class MainAction {
	
	@RequestMapping(value = "/login")
	public String login() {

		return "login";
	}

	@RequestMapping(value = "/restpwd")
	public String restPwd() {

		return "rest_pwd";
	}

	@RequestMapping(value = "/register")
	public String register() {

		return "register";
	}

	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Map<String, Object> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("cookieInfo", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		session.invalidate();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("code", ResultCode.SUCCESS.getCode());
		return modelMap;
	}

	/**
	 * 重置密码跳转页
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/findPwd")
	public String findPwd(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			String account = request.getParameter("account");
			String code = request.getParameter("code");
			mv.addObject("account", account);
			mv.addObject("code", code);
			mv.setViewName("rest_pwd_2");
		} catch (Exception e) {
			String errorMethod = "UserAction-->findPwd()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			// mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return "redirect:" + Constant.ERRORPAGE;
		}
		return "rest_pwd_2";
	}

	@RequestMapping(value = "/error")
	public String error() {
		return "error";
	}
}
