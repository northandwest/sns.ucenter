package com.bucuoa.passport.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bucuoa.passport.base.enums.ResultCode;
import com.bucuoa.passport.base.exception.BusinessException;
import com.bucuoa.passport.base.vo.UserVo;
import com.bucuoa.passport.entity.SessionUser;
import com.bucuoa.passport.entity.User;
import com.bucuoa.passport.service.UlewoUserService;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseUserAction {
	
	@Autowired
	private UlewoUserService userService;

	private Logger log = LoggerFactory.getLogger(UserAction.class);

	/**
	 * 注册
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public Map<String, Object> register(HttpSession session,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long userId = 0l;
		try {
			final Map<String, String> builderParams = builderParams(request, true);
			final Object checkCode = session.getAttribute("checkCode");
			User user = userService.register(builderParams,	String.valueOf(checkCode));
			// 保存Cookie
			String infor = URLEncoder.encode(String.valueOf(user.getUserId()),	"utf-8") + "," + user.getPassword();
			// 清除之前的Cookie 信息
			Cookie cookie = new Cookie("cookieInfo", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);

			// 建用户信息保存到Cookie中
			Cookie cookieInfo = new Cookie("cookieInfo", infor);
			cookieInfo.setPath("/");
			// 设置最大生命周期为1年。
			cookieInfo.setMaxAge(31536000);
			response.addCookie(cookieInfo);
			
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserId(user.getUserId());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserIcon(user.getUserIcon());
			session.setAttribute("user", sessionUser);
			
			userId = user.getUserId();
			
			modelMap.put("userId", userId);
			modelMap.put("code", ResultCode.SUCCESS.getCode());
			
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常");
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		}

	}

	/**
	 * 登陆
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public Map<String, Object> login(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, true);
			User user = userService.login(parms,String.valueOf(session.getAttribute("checkCode")));
			if ("Y".equals(parms.get("autoLogin"))) {
				// 自动登录，保存用户名密码到 Cookie
				final String params = parms.get("account").toString();
				String infor = URLEncoder.encode(params, "utf-8") + "," + user.getPassword();

				// 清除之前的Cookie 信息
				Cookie cookie = new Cookie("cookieInfo", null);
				cookie.setPath("/");
				cookie.setMaxAge(0);

				// 建用户信息保存到Cookie中
				Cookie cookieInfo = new Cookie("cookieInfo", infor);
				cookieInfo.setPath("/");
				// 设置最大生命周期为1年。
				cookieInfo.setDomain(".bucuoa.com");
				cookieInfo.setMaxAge(31536000);
				response.addCookie(cookieInfo);
			} else {
				Cookie cookie = new Cookie("cookieInfo", null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
			}
			
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserId(user.getUserId());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserIcon(user.getUserIcon());
			session.setAttribute("user", sessionUser);
			
			// 更新最后登录时间
			User loginUser = new User();
			loginUser.setUserId(user.getUserId());
			loginUser.setPreVisitTime(new Date());
			
			userService.updateSelective(loginUser);
			modelMap.put("code", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			modelMap.put("msg", e.getMessage());
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage());
			modelMap.put("msg", "系统异常");
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		}

	}

	

	/**
	 * 发送重置密码连接
	 * 
	 * @param userName
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendRestPwd.do", method = RequestMethod.POST)
	public Map<String, Object> sendRestPwd(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, true);
			final Object checkCode = session.getAttribute("checkCode");
			User user = userService.sendRestPwd(parms,	String.valueOf(checkCode));
			modelMap.put("address", user.getEmail());
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	

	@ResponseBody
	@RequestMapping(value = "/resetpwd.do", method = RequestMethod.POST)
	public Map<String, Object> resetpwd(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, true);
			userService.resetPwd(parms,	String.valueOf(session.getAttribute("checkCode")));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage());
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("message", "系统异常");
			return modelMap;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/loadUserInfo.action")
	public Map<String, Object> loadUserInfo(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Long userId = this.getSessionUserId(session);
//			List<UserFriend> focusList = userFriendService
//					.queryFocus4List(userId);
//			List<UserFriend> fansList = userFriendService
//					.queryFans4List(userId);
			UserVo userVo = checkUserInfo(userId.toString(), session);
			modelMap.put("userVo", userVo);
//			modelMap.put("focusList", focusList);
//			modelMap.put("fansList", fansList);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}
}
