package com.bucuoa.passport.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.passport.base.enums.MaxLengthEnums;
import com.bucuoa.passport.base.enums.PageSize;
import com.bucuoa.passport.base.enums.QueryUserType;
import com.bucuoa.passport.base.enums.ThemeTypeEnums;
import com.bucuoa.passport.base.exception.BusinessException;
import com.bucuoa.passport.base.utils.Constant;
import com.bucuoa.passport.base.utils.DateUtil;
import com.bucuoa.passport.base.utils.SendMail;
import com.bucuoa.passport.base.utils.SimplePage;
import com.bucuoa.passport.base.utils.StringUtils;
import com.bucuoa.passport.base.utils.UlewoPaginationResult;
import com.bucuoa.passport.base.utils.UlewoResult;
import com.bucuoa.passport.dao.UlewoUserDao;
import com.bucuoa.passport.entity.SessionUser;
import com.bucuoa.passport.entity.User;
import com.bucuoa.ucenter.model.UlewoUser;
import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.app.extend.SingleBaseService;
import com.bucuoa.west.orm.app.utils.RequestConverter;

@Service("ulewoUserService")
public class UlewoUserService extends SingleBaseService<User, Long> {
	@Resource
	private UlewoUserDao dao;

	public UlewoUserDao getDao() {
		return dao;
	}

	protected String getReqValByParam(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		return (value == null) ? "" : value.trim();
	}

	public List<Map<String, Object>> queryListMap(String sql) {
		return getDao().queryListMap(sql);
	}

	@Transactional(readOnly = true)
	public WPage getEntityPage(HttpServletRequest request) throws Exception {
		// filters_like_name filters_equals_categoryName
		int pageNO = 0;
		Map map = request.getParameterMap();

		RequestConverter rq = new RequestConverter(map);

		List<Expression> wheres = rq.getWhereCondition();
		pageNO = rq.getPageNO();

		WPage page = new WPage();
		page.setPageSize(rq.getPageSize());
		page.setPageNo(pageNO);

		int count = getDao().getEntityCount(wheres);
		List<User> data = getDao().findEntityList(wheres, page);

		page.setTotalCount(count);
		page.setData(data);

		return page;
	}
	
	private static final int EMAIL_LENGTH = 50;
	private static final int USERNAME_LENGTH = 20;
	private static final int PWD_MAX_LENGTH = 16;
	private static final int PWD_MIN_LENGTH = 6;

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public User register(Map<String, String> map, String sessionCode)
			throws BusinessException {
		String userName = map.get("userName");
		String email = map.get("email");
		String password = map.get("pwd");
		String checkCode = map.get("code");
		if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		} else if (!StringUtils.checkEmail(email) || StringUtils.isEmpty(email)
				|| email.length() > EMAIL_LENGTH) {
			throw new BusinessException("邮箱地址不符合规范");
		} else if (!StringUtils.checkUserName(userName)
				|| StringUtils.isEmpty(userName.trim())
				|| StringUtils.getRealLength(userName) < 1
				|| StringUtils.getRealLength(userName) > USERNAME_LENGTH) {
			throw new BusinessException("用户名不符合规范");
		} else if (!StringUtils.checkPassWord(password)
				|| StringUtils.isEmpty(password)
				|| password.length() < PWD_MIN_LENGTH
				|| password.length() > PWD_MAX_LENGTH) {
			throw new BusinessException("密码不符合规范");
		} else if (null != checkEmail(email)) {// 后台检测邮箱是否唯一
			throw new BusinessException("邮箱已经被占用");
		} else if (null != checkUserName(userName)) { // 后台检测用户昵称是否唯一
			throw new BusinessException("用户名已经被占用");
		} else {
			User user = new User();
			user.setUserName(userName);
			user.setPassword(StringUtils.encodeByMD5(password));
			user.setEmail(email);
			final Date now = new Date();
			String curDate = StringUtils.dateFormater.format(now);
			user.setRegisterTime(now);
			user.setPreVisitTime(now);
			user.setUserIcon(Constant.DEFALUTICON);
//			userMapper.insert(user);
			try {
				dao.saveEntity(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return user;
		}
	}

	private UlewoUser checkUserName(String userName) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("userName", userName);
		UlewoUser user = null;
		try {
//			user = dao.findEntityBy("userName", userName);
			UlewoUser query = new UlewoUser();
			query.setUserName(userName);
			 List<UlewoUser> lists = dao.findListBy(query);//.findEntityBy("email", email);
			 if(lists != null && lists.size() > 0)
			 {
				 user = lists.get(0);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}// userMapper.selectBaseInfo(map);
		return user;
	}

	private UlewoUser checkEmail(String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		UlewoUser user = null;
		try {
			UlewoUser query = new UlewoUser();
			query.setEmail(email);
			 List<UlewoUser> lists = dao.findListBy(query);//.findEntityBy("email", email);
			 if(lists != null && lists.size() > 0)
			 {
				 user = lists.get(0);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}//  userMapper.selectBaseInfo(map);
		return user;
	}

	
	public User login(Map<String, String> map, String sessionCode)
			throws BusinessException {
		String account = map.get("account");
		String password = map.get("password");
		String checkCode = map.get("checkCode");
		if (StringUtils.isEmpty(account)) {
			throw new BusinessException("账号不能为空");
		} else if (StringUtils.isEmpty(password)) {
			throw new BusinessException("密码不能为空");
		} else if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		} else {
			password = StringUtils.encodeByMD5(password);
			User user = null;
			if (account.contains("@")) {
				user = findUser(account, QueryUserType.EMAIL);
			} else {
				user = findUser(account, QueryUserType.USERNAME);
			}
			if (null == user || !user.getPassword().equals(password)) {
				throw new BusinessException("帐号或者密码错误");
			} else {
				return user;
			}
		}
	}

	
	public User sendRestPwd(Map<String, String> map, String sessionCode)
			throws Exception, BusinessException {
		String account = map.get("account");
		String checkCode = map.get("code");
		UlewoResult<User> result = new UlewoResult<User>();
		StringBuilder msg = new StringBuilder();
		if (StringUtils.isEmpty(account)) {
			throw new BusinessException("账号不能为空");
		}
		if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		}
		User user = null;
		if (account.contains("@")) {
			user = findUser(account, QueryUserType.EMAIL);
		} else {
			user = findUser(account, QueryUserType.USERNAME);
		}
		if (null == user) {
			throw new BusinessException("帐号不存在");
		}
		// 发送邮件
		String activationCode = createCode();
		user.setActivationCode(activationCode);
//		userMapper.updateSelective(user);
		dao.updateEntity(user);
		sendMile(user.getEmail(), activationCode);
		return user;
	}

	/**
	 * 重置密码
	 */
	public void resetPwd(Map<String, String> map, String sessionCode)
			throws BusinessException {
		String account = map.get("account");
		String activationCode = map.get("activationCode");
		String pwd = map.get("pwd");
		String checkCode = map.get("code");

		if (StringUtils.isEmpty(account)) {
			throw new BusinessException("帐号不能为空");
		}
		if (StringUtils.isEmpty(activationCode)) {
			throw new BusinessException("激活码不能为空");
		}
		if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		}

		if (!StringUtils.checkPassWord(pwd) || StringUtils.isEmpty(pwd)
				|| pwd.length() < PWD_MIN_LENGTH
				|| pwd.length() > PWD_MAX_LENGTH) {
			throw new BusinessException("密码不符合规范");
		}
		User user = null;
		if (account.contains("@")) {
			user = findUser(account, QueryUserType.EMAIL);
		} else {
			user = findUser(account, QueryUserType.USERNAME);
		}
		if (null == user) {
			throw new BusinessException("用户不存在");
		} else if (!user.getActivationCode().equals(activationCode)) {
			throw new BusinessException("激活码不匹配");
		} else {
			user.setPassword(StringUtils.encodeByMD5(pwd));
//			userMapper.updateSelective(user);
			try {
				dao.updateEntity(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void updatePassword(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
		String oldpwd = map.get("oldpwd");
		String newpwd = map.get("newpwd");

		if (!StringUtils.checkPassWord(newpwd) || StringUtils.isEmpty(newpwd)
				|| newpwd.length() < MaxLengthEnums.MAXLENGTH6.getLength()
				|| newpwd.length() > MaxLengthEnums.MAXLENGTH16.getLength()) {
			throw new BusinessException("新密码不符合规范");
		}
		Long userId = sessionUser.getUserId();
		User resultUser = findUser(userId.toString(), QueryUserType.USERID);
		if (null != resultUser
				&& resultUser.getPassword().equals(
						StringUtils.encodeByMD5(oldpwd))) {
			User user = new User();
			user.setUserId(userId);
			user.setPassword(StringUtils.encodeByMD5(newpwd));
//			this.userMapper.updateSelective(user);
			try {
				dao.updateEntity(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new BusinessException("你输入的旧密码错误，修改密码失败");
		}
	}
	
	
	public User findUserByEmail(String value) throws BusinessException
	{
		
		User user = findUser(value,QueryUserType.EMAIL);
		 if (user == null) {
				throw new BusinessException("用户不存在");
			}
		 
		return user;
	}
	
	
	public User findUserByUserName(String value) throws BusinessException
	{
		
		User user = findUser(value,QueryUserType.USERNAME);
		 if (user == null) {
				throw new BusinessException("用户不存在");
			}
		 
		return user;
	}


	public User findUser(String value, QueryUserType type)
			throws BusinessException {
		if (null == value) {
			return null;
		}
//		Map<String, String> map = new HashMap<String, String>();
		User query = new User();
		 
		String name = "";
		if (type == QueryUserType.EMAIL) {
			name = "mail";
			query.setEmail(value);
		} else if (type == QueryUserType.USERNAME) {
			name = "userName";
			query.setUserName(value);
		} else if (type == QueryUserType.USERID) {
			name = "userId";
			query.setUserId(Long.parseLong(value));
		}
//		List<Expression> wheres = new ArrayList<Expression>();
//		Expression expression = new Expression("userName",value);
//		wheres.add(expression);
		
//		dao.findEntityList(wheres, null);
		User user = null;
		try {
			 List<User> lists = dao.findListBy(query);//.findEntityBy("email", email);
			 if(lists != null && lists.size() > 0)
			 {
				user = lists.get(0);
//				BeanUtils.copyProperties(userx, user);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		return user;

	}

	public void setTheme(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
		String theme = map.get("theme");
		if (!ThemeTypeEnums.TYPE0.getValue().equals(theme)
				&& !ThemeTypeEnums.TYPE1.getValue().equals(theme)) {
			throw new BusinessException("风格参数错误");
		}
		Long userId = sessionUser.getUserId();
		User user = new User();
		user.setUserId(userId);
		user.setCenterTheme(theme);
		
		try {
			dao.updateEntity(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		userMapper.updateSelective(user);
	}


	public User updateUserInfo(Map<String, String> map, Long userId)
			throws BusinessException {

//		Integer userId = sessionUser.getUserId();
		String birthday = map.get("birthday");
		String character = map.get("characters");
		String sex = map.get("sex");
		String address = map.get("address");
		String work = map.get("work");
//		if (!StringUtils.isNumber(age) && StringUtils.isEmpty(age)) {
//			throw new BusinessException("年龄必须是数字");
//		}
		if (address != null
				&& address.trim().length() > MaxLengthEnums.MAXLENGTH50
						.getLength()) {
			throw new BusinessException("地址信息不能超过50字符");
		}
		if (work != null
				&& work.trim().length() > MaxLengthEnums.MAXLENGTH50
						.getLength()) {
			throw new BusinessException("工作信息不能超过50字符");
		}
		if (null != character
				&& character.trim().length() > MaxLengthEnums.MAXLENGTH150
						.getLength()) {
			throw new BusinessException("个性签名不能超过150字符");
		}
		User user = new User();
		user.setUserId(userId);
//		user.setAge(age);
		Date birth = DateUtil.parse(birthday,"yyyy-MM-dd");
		user.setBirthday(birth);
		user.setCharacters(character);
		user.setAddress(address);
		user.setSex(sex);
		user.setWork(work);
//		userMapper.updateSelective(user);
		try {
			dao.updateEntity(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	public User updateUserInfoBg(Map<String, String> map, Long userId)
			throws BusinessException {

//		Integer userId = sessionUser.getUserId();
		String userBg = map.get("userBg");


		User user = new User();
		user.setUserId(userId);
		user.setUserBg(userBg);
		try {
			dao.updateEntity(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		userMapper.updateSelective(user);
		return user;
	}
	
	
	public User updateUserInfoIcon(Map<String, String> map, Long userId)
			throws BusinessException {

//		Integer userId = sessionUser.getUserId();
		String userIcon = map.get("userIcon");

		User user = new User();
		user.setUserId(userId);
		user.setUserIcon(userIcon);

//		userMapper.updateSelective(user);
		try {
			dao.updateEntity(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteUserBatch(Map<String, String> map)
			throws BusinessException {
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("userId", key);
			int count = 10;//this.userMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}

	public User login(String value, String password) throws BusinessException {

		User user = null;
		if (value.contains("@")) {
			user = findUser(value, QueryUserType.EMAIL);
		} else {
			user = findUser(value, QueryUserType.USERNAME);
		}
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			return null;
		}
	}

	public User queryUserBaseInfo(Integer userId) throws BusinessException {
		User user = null;
		try {
			user = dao.findEntityById(userId.longValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return  null;//this.userMapper.selectUserBaseInfo(userId);
		return user;
	}

	// 获取发送邮件的域
	private String MailAdress(String email) throws Exception {

		String maillAdress = "www.bucuoa.com";
		int start = email.indexOf("@");
		int end = email.indexOf(".");
		String web = email.substring(start + 1, end);
		if ("gmail".equalsIgnoreCase(web)) {
			maillAdress = "http://www.gmail.com";
		} else {
			maillAdress = "http://mail." + web + ".com";
		}
		return maillAdress;
	}

	private String createCode() {

		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String sRand = "";
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(s.length());
			String rand = String.valueOf(s.charAt(x));
			sRand += rand;
		}
		return sRand;
	}

	// 发送激活邮件
	private void sendMile(String email, String activationCode) throws Exception {

		String url = "http://bucuoa.com/user/findPwd?account=" + email
				+ "&code=" + activationCode;
		String title = "ulewo邮箱找回密码邮件";
		StringBuilder content = new StringBuilder("亲爱的" + email + "<br><br>");
		content.append("欢迎使用ulewo找回密码功能。(http://bucuoa.com)!<br><br>");
		content.append("请点击链接重置密码：<br><br>");
		content.append("<a href=\"" + url + "\">" + url + "</a><br><br>");
		content.append("如果你的email程序不支持链接点击，请将上面的地址拷贝至你的浏览器(如IE)的地址栏进入。<br><br>");
		content.append("您的注册邮箱是:" + email + "<br><br>");
		content.append("希望你在有乐窝社区的体验有益和愉快！<br><br>");
		content.append("- 有乐窝社区(http://bucuoa.com)");
		String[] address = new String[] { email };
		SendMail.sendEmail(title, String.valueOf(content), address);
	}

	public UlewoPaginationResult<User> findAllUsers(Map<String, String> map) {
		map.put("orderBy", "register_time desc");
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int count = 10;// this.userMapper.selectBaseInfoCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<User> list = new ArrayList();// this.userMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<User> result = new UlewoPaginationResult<User>(
				page, list);
		return result;
	}

	public List<User> selectUsersByMark() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderBy", "mark desc");
		SimplePage page = new SimplePage(0, 50);
		List<User> list =  new ArrayList();// userMapper.selectBaseInfoList(map, page);
		return list;
	}

	public void updateSelective(User user) {
//		this.userMapper.updateSelective(user);
		try {
			dao.updateEntity(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
//	public UserVo4Api api_login(Map<String, String> map)
//			throws BusinessException {
//		String account = map.get("userName");
//		String password = map.get("password");
//		if (StringUtils.isEmpty(account)) {
//			throw new BusinessException("账号不能为空");
//		} else if (StringUtils.isEmpty(password)) {
//			throw new BusinessException("密码不能为空");
//		} else {
//			password = StringUtils.encodeByMD5(password);
//			User user = null;
//			if (account.contains("@")) {
//				user = findUser(account, QueryUserType.EMAIL);
//			} else {
//				user = findUser(account, QueryUserType.USERNAME);
//			}
//			if (null == user || !user.getPassword().equals(password)) {
//				throw new BusinessException("帐号或者密码错误");
//			} else {
//				UserVo4Api userVo = new UserVo4Api();
//				userVo.setUserId(user.getUserId());
//				userVo.setUserName(user.getUserName());
//				userVo.setUserIcon(user.getUserIcon());
//				userVo.setMark(user.getMark());
//				userVo.setPrevisitTime(user.getShowPreVisitTime());
//				userVo.setRegisterTime(user.getShowPreVisitTime());
//				userVo.setMark(user.getMark());
//				return userVo;
//			}
//		}
//	}


//	public Map<String, Object> queryUserInfo4Api(String userId)
//			throws BusinessException {
//		if (StringUtils.isEmpty(userId)) {
//			throw new BusinessException("参数错误");
//		}
//		Map<String, Object> result = new HashMap<String, Object>();
//		User user = null;//userMapper.selectUserBaseInfo(Integer.parseInt(userId));
//		UserVo4Api vo = new UserVo4Api();
//		vo.setUserIcon(user.getUserIcon());
//		vo.setUserId(user.getUserId());
//		vo.setUserName(user.getUserName());
//		vo.setCharacters(user.getCharacters());
//		vo.setFansCount(user.getFansCount());
//		vo.setFocusCount(user.getFocusCount());
//		vo.setMark(user.getMark());
//		vo.setPrevisitTime(user.getShowPreVisitTime());
//		vo.setRegisterTime(user.getShowRegisterTime());
//		vo.setSex(user.getSex());
//		vo.setJob(user.getWork());
//		result.put("user", vo);
//		return result;
//	}

}
				 
								 
								