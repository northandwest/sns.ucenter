//package com.ulewo.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.bucuoa.ucenter.model.UlewoUser;
//import com.ulewo.enums.ResponseCode;
//import com.ulewo.exception.BusinessException;
//import com.ulewo.service.UlewoUserService;
//import com.ulewo.vo.AjaxResponse;
//@Controller
//@RequestMapping("/api")
//public class ApiController extends BaseAction {
//	@Autowired
//	private UlewoUserService userService;
//    
//	@ResponseBody
//	@RequestMapping(value = "/load/{uid}", method = RequestMethod.GET)
//	public AjaxResponse<UlewoUser> login(@PathVariable Long uid,HttpSession session,
//			HttpServletRequest request, HttpServletResponse response) {
//		AjaxResponse<UlewoUser> result = new AjaxResponse<UlewoUser>();
//			UlewoUser data;
//			try {
//				data = userService.findEntityById(uid);
//				result.setData(data);
//				result.setResponseCode(ResponseCode.SUCCESS);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		
//		return result;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public AjaxResponse<UlewoUser> update(HttpSession session,
//			HttpServletRequest request, HttpServletResponse response) { 
//		AjaxResponse<UlewoUser> result = new AjaxResponse<UlewoUser>();
//		try {
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("work",request.getParameter("work"));
//			map.put("birthday",request.getParameter("birthday"));
//			map.put("sex",request.getParameter("sex"));
//			map.put("characters",request.getParameter("characters"));
//			map.put("address",request.getParameter("address"));
////			map.put("userBg",request.getParameter("userBg"));
////			map.put("userIcon",request.getParameter("userIcon"));
//			
//			String userid= request.getParameter("userid");
//
////			User data = userService.updateUserInfo(map, Integer.parseInt(userid));
//			UlewoUser uu = new UlewoUser();
//			uu.setWork(request.getParameter("work"));
////			uu.setBirthday(request.getParameter("birthday"));
//			uu.setSex(request.getParameter("sex"));
//			uu.setCharacters(request.getParameter("characters"));
//			uu.setAddress(request.getParameter("work"));
//			uu.setUserId(Long.parseLong(userid));
//			
//			userService.updateEntity(uu);
//			
//			result.setData(uu);
//			result.setResponseCode(ResponseCode.SUCCESS);
//		} catch (Exception e) {
//			result.setResponseCode(ResponseCode.BUSINESSERROR);
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/updatebg", method = RequestMethod.GET)
//	public AjaxResponse<UlewoUser> updatebg(HttpSession session,
//			HttpServletRequest request, HttpServletResponse response) { 
//		AjaxResponse<UlewoUser> result = new AjaxResponse<UlewoUser>();
//		try {
//			Map<String,String> map = new HashMap<String,String>();
////			map.put("work",request.getParameter("work"));
////			map.put("birthday",request.getParameter("birthday"));
////			map.put("sex",request.getParameter("sex"));
////			map.put("characters",request.getParameter("characters"));
//			map.put("userBg",request.getParameter("userBg"));
////			map.put("userIcon",request.getParameter("userIcon"));
//			
//			String userid= request.getParameter("userid");
//			
////			User data = userService.updateUserInfoBg(map, Integer.parseInt(userid));
//			UlewoUser uu = new UlewoUser();
//			uu.setUserBg(request.getParameter("userBg"));
//			uu.setUserId(Long.parseLong(userid));
//			
//			userService.updateEntity(uu);
//			
//			result.setData(uu);
//			result.setResponseCode(ResponseCode.SUCCESS);
//		} catch (Exception e) {
//			result.setResponseCode(ResponseCode.BUSINESSERROR);
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/updateicon", method = RequestMethod.GET)
//	public AjaxResponse<UlewoUser> updateicon(HttpSession session,
//			HttpServletRequest request, HttpServletResponse response) { 
//		AjaxResponse<UlewoUser> result = new AjaxResponse<UlewoUser>();
//		try {
//			Map<String,String> map = new HashMap<String,String>();
////			map.put("work",request.getParameter("work"));
////			map.put("birthday",request.getParameter("birthday"));
////			map.put("sex",request.getParameter("sex"));
////			map.put("characters",request.getParameter("characters"));
////			map.put("userBg",request.getParameter("userBg"));
//			map.put("userIcon",request.getParameter("userIcon"));
//			
//			String userid= request.getParameter("userid");
//			UlewoUser uu = new UlewoUser();
//			uu.setUserIcon(request.getParameter("userIcon"));
//			uu.setUserId(Long.parseLong(userid));
//			
//			userService.updateEntity(uu);
//			result.setData(uu);
//			result.setResponseCode(ResponseCode.SUCCESS);
//		} catch (Exception e) {
//			result.setResponseCode(ResponseCode.BUSINESSERROR);
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/loade/{email}", method = RequestMethod.GET)
//	public AjaxResponse<UlewoUser> loginByEmail(@PathVariable String email,HttpSession session,
//			HttpServletRequest request, HttpServletResponse response) {
//		AjaxResponse<UlewoUser> result = new AjaxResponse<UlewoUser>();
//		try {
//			UlewoUser data = userService.findUserByEmail(email);
//			result.setData(data);
//			result.setResponseCode(ResponseCode.SUCCESS);
//		} catch (BusinessException e) {
//			result.setResponseCode(ResponseCode.BUSINESSERROR);
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/loadu/{username}", method = RequestMethod.GET)
//	public AjaxResponse<UlewoUser> loginByUsername(@PathVariable String username,HttpSession session,
//			HttpServletRequest request, HttpServletResponse response) {
//		AjaxResponse<UlewoUser> result = new AjaxResponse<UlewoUser>();
//		try {
//			UlewoUser data = userService.findUserByUserName(username);
//			result.setData(data);
//			result.setResponseCode(ResponseCode.SUCCESS);
//		} catch (BusinessException e) {
//			result.setResponseCode(ResponseCode.BUSINESSERROR);
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
//}
