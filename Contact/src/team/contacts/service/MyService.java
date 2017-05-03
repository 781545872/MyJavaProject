package team.contacts.service;


import java.sql.SQLException;
import java.util.List;

import team.contacts.dao.MyDao;
import team.contacts.domain.Contact;
import team.contacts.domain.ContactDetail;
import team.contacts.domain.TransmissionData;
import team.contacts.domain.User;
import team.contacts.util.MyTools;


public class MyService {

	private MyDao myDao = new MyDao();
	
	/**
	 * 客户第一次登陆请求验证码
	 * @param phone
	 * @return 验证码
	 * @throws Exception 
	 */
	public ResultCode register(String phone){
		ResultCode code = ResultCode.GET_TOKEN_SUCCESS;
		//先判断该手机号是否已经注册
		try {
			User user = myDao.queryUser(phone);
			//如果该手机号已经被注册，则获取验证码失败
			if(user!=null){
				return ResultCode.PHONE_REGISTERED;
			}
			String token = MyTools.getAToken();
			myDao.saveUser(new User(phone, token));
			MyTools.sendMessage("您的验证码是："+token, phone);
		} catch (Exception e) {
			code = ResultCode.GET_TOKEN_FAIL;
			e.printStackTrace();
		}
		return code;
	}
	

	/**
	 * 客户登录对手机号和验证码进行验证
	 * @param phone 手机
	 * @param token 验证码
	 * @return 结果码
	 * @throws Exception
	 */
	public ResultCode login(String phone,String token) {
		User user;
		try {
			user = myDao.queryUser(phone);
			if(user == null){
				return ResultCode.PHONE_NOT_EXIST;
			}
			if(!user.getToken().equals(token)){
				return ResultCode.WRONG_TOKEN;
			}
			return ResultCode.LOGIN_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultCode.OTHER_ERROR;
		}
	}
	
	/**
	 * 客户注销
	 * @param phone
	 */
	public ResultCode logout(String phone){
		try {
			myDao.deleteUser(phone);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultCode.OTHER_ERROR;
		}
		return ResultCode.LOGOUT_SUCCESS;
	}
	
	/**
	 * 获取指定客户的所有联系人信息
	 * @param phone
	 * @return 返回一个json格式的字符串
	 * @throws Exception 
	 */
	public String getContacts(String phone){
		String result = "";
		ResultCode code = ResultCode.GET_CONTACTS_SUCCESS;
		Integer userId;
		try {
			userId = myDao.queryUser(phone).getUserId();
			TransmissionData data = new TransmissionData();
			List<Contact> contacts = myDao.queryContacts(userId);
			for(Contact contact:contacts){
				List<ContactDetail> details = myDao.queryContactDetail(contact.getContactId());
				contact.setDetails(details);
			}
			data.setData(contacts);
			result = MyTools.getJson(data, TransmissionData.class);
			
		} catch (SQLException e) {
			code = ResultCode.SQL_ERROR;
			e.printStackTrace();
		}	
		return result;
	}

	/**
	 * 更新上传数据，如果数据库中已经存在的就更新，如果不存在就新建
	 * @param phone
	 * @param contacts
	 * @param callLogs
	 */
	public ResultCode upload(String upload) {
		ResultCode code = ResultCode.UPLOAD_SUCCESS;
		TransmissionData data = (TransmissionData) MyTools.getBean(upload, TransmissionData.class);
		if(data!=null){
			String phone = data.getUser().getPhone();
			try {
				Integer userId = myDao.queryUser(phone).getUserId();
				//获得Android客户端传过来的联系人信息数组
				List<Contact> contacts = data.getData();
				//遍历数组，每存一条contact对应存储一或多条contact_detail
				for(Contact c:contacts){
					//如果数据库中已经存在该联系人的信息，则将该联系人信息删除
					if(myDao.queryContact(userId, c.getName()) != null){
						myDao.deleteContact(userId, c.getName());
					}
					//从Android客户端传过来的不含user_id,contact_id等用于表之间进行连接的字段，需要重新设置
					c.setUserId(userId);
					//将设置好后的contact表的数据存入数据库
					myDao.saveContact(c);
					//查询刚才插入的contact表的数据的主键contact_id
					Integer contactId = myDao.queryContact(userId, c.getName()).getContactId();
					List<ContactDetail>  details = c.getDetails();
					for(ContactDetail detail:details){
						//为每个detail设置contact_id
						detail.setContactId(contactId);
						//保存联系人详情
						myDao.saveContactDetail(detail);
					}
				}
			} 
			catch (SQLException e) {
				//如果发生异常，则将返回结果设置为相应的错误
				code = ResultCode.SQL_ERROR;
				e.printStackTrace();
			} 
		}
		return code;
	}
}
