package team.contacts.domain;
/**
 * ContactDetail联系人详情表
 * @author ZZF
 *
 */
public class ContactDetail {
	/**
	 * contact表主键
	 */
	private Integer contactId;
	/**
	 * 联系人电话
	 */
	private String phone;
	/**
	 * 电话所在省
	 */
	private String province;
	/**
	 * 电话所在市
	 */
	private String city;

	public ContactDetail(Integer contactId, String phone, String province, String city) {
		super();
		this.contactId = contactId;
		this.phone = phone;
		this.province = province;
		this.city = city;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
