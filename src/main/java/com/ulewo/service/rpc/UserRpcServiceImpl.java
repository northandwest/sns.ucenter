package com.ulewo.service.rpc;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bucuoa.ucenter.api.UserRpcService;
import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.User;
import com.ulewo.service.UlewoUserService;

@Service("userRpcServiceStub")
public class UserRpcServiceImpl implements UserRpcService {
	@Resource
	private UlewoUserService ulewoUserService;
	
	@Override
	public UlewoUser findUserByEmail(String value) {
		User user = null;
		try {
			user = ulewoUserService.findUserByEmail(value);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		UlewoUser userx = new UlewoUser();
		userConvert2(user,userx);
		return userx;
	}

	@Override
	public UlewoUser findUserByUserId(String userId) {
		User user = null;
		try {
			user = ulewoUserService.findEntityById(Long.parseLong(userId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UlewoUser userx = new UlewoUser();
		userConvert2(user,userx);
		return userx;
	}

	@Override
	public UlewoUser findUserByUserName(String userName) {
		User user = null;
		try {
			user = ulewoUserService.findUserByUserName(userName);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		UlewoUser userx = new UlewoUser();
		userConvert2(user,userx);
		
		return userx;
	}

	@Override
	public void updateInfo(UlewoUser user) {
		User userx = new User();
		userConvert(user,userx);
		
		try {
			ulewoUserService.updateEntity(userx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private User userConvert(UlewoUser user,User user2)
	{
		 BeanUtils.copyProperties(user, user2);
		 
		 return user2;
	}
	
	private UlewoUser userConvert2(User user,UlewoUser user2)
	{
		 BeanUtils.copyProperties(user, user2);
		 
		 return user2;
	}

}
