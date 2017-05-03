package team.contacts.domain;

import java.util.List;

/**
 * 与客户端交换数据的对象
 * @author ZZF
 *
 */
public class TransmissionData {
	/**
	 * 客户
	 */
	private User user;
	/**
	 * 联系人数据
	 */
	private List<Contact> data;
	/**
	 * 返回结果代码
	 */
	private Integer code;


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Contact> getData() {
		return data;
	}

	public void setData(List<Contact> data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
