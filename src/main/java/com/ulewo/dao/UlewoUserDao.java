package com.ulewo.dao;

import org.springframework.stereotype.Repository;

import com.bucuoa.west.orm.app.extend.ISingleBaseDao;
import com.bucuoa.west.orm.app.extend.spring.SpringSingleBaseDao;
import com.ulewo.model.User;
@Repository
public class UlewoUserDao extends SpringSingleBaseDao<User, Long> implements ISingleBaseDao<User, Long>{
}						 
								 
									 
								 
								 
								 
								