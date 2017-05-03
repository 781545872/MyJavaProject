package team.contacts.domain;


/**
 * User客户表
 * 
 * @author ZZF
 *
 */
@SuppressWarnings("serial")
public class User implements java.io.Serializable {
	/**
	 * user表主键
	 */
	private Integer userId;
	/**
	 * 客户手机号
	 */
	private String phone;
	/**
	 * 客户验证码
	 */
	private String token;

	public User(Integer userId, String phone, String token) {
		super();
		this.userId = userId;
		this.phone = phone;
		this.token = token;
	}

	public User(String phone, String token) {
		super();
		this.phone = phone;
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
