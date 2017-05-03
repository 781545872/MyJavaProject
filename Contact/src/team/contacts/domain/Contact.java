package team.contacts.domain;

import java.util.List;

/**
 * Contact联系人表
 * @author ZZF
 *
 */
public class Contact {

	/**
	 * contact表主键,自增
	 */
	private Integer contactId;
	/**
	 * user表主键
	 */
	private Integer userId;
	/**
	 * id字段
	 */
	private Integer id;
	/**
	 * 联系人名字
	 */
	private String name;
	/**
	 * 联系人头像
	 */
	private String icon;
	/**
	 * 是否被拉黑
	 */
	private Boolean isBlack;
	/**
	 * 数据存储地点，服务器/本地/服务器和本地
	 */
	private Integer from;
	
	/**
	 * 联系人详情列表
	 */
	private List<ContactDetail> details;

	public Contact(Integer contactId, Integer userId, Integer id, String name, String icon, Boolean isBlack,
			Integer from) {
		super();
		this.contactId = contactId;
		this.userId = userId;
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.isBlack = isBlack;
		this.from = from;
	}

	public Contact(Integer userId, Integer id, String name, String icon, Boolean isBlack, Integer from) {
		super();
		this.userId = userId;
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.isBlack = isBlack;
		this.from = from;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Boolean isBlack) {
		this.isBlack = isBlack;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public List<ContactDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ContactDetail> details) {
		this.details = details;
	}
	
}
