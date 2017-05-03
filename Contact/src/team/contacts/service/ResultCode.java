package team.contacts.service;

public enum ResultCode {
	/**
	 * 登录成功，账号验证码检查一致
	 */
	LOGIN_SUCCESS(1),
	/**
	 * 不存在该手机号
	 */
	PHONE_NOT_EXIST(2),
	/**
	 * 手机号对应的验证码不一致
	 */
	WRONG_TOKEN(3),
	/**
	 * 注销成功
	 */
	LOGOUT_SUCCESS(4),
	/**
	 * 其它错误
	 */
	OTHER_ERROR(5),
	/**
	 * 获取验证码失败
	 */
	GET_TOKEN_FAIL(6),
	/**
	 * 数据库执行语句错误
	 */
	SQL_ERROR(7),
	/**
	 * 上传成功
	 */
	UPLOAD_SUCCESS(8),
	/**
	 * 成功获得验证码
	 */
	GET_TOKEN_SUCCESS(9),
	/**
	 * 成功获得联系人信息
	 */
	GET_CONTACTS_SUCCESS(10),
	/**
	 * 获取联系人信息失败
	 */
	GET_CONTACTS_FAIL(11),
	/**
	 * 注销失败
	 */
	LOGOUT_FIAL(12),
	/**
	 * 手机号已经被注册
	 */
	PHONE_REGISTERED(13);

	private Integer code;

	private ResultCode(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
