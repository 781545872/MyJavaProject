package team.contacts.dao;

import team.contacts.domain.Contact;
import team.contacts.domain.ContactDetail;
import team.contacts.domain.User;
import team.contacts.util.JdbcTools;
import team.contacts.util.MyTools;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao类
 * 
 * @author ZZF
 *
 */
public class MyDao {
	/**
	 * 重建数据库
	 * contacts.sql这个文件是从Navicat文件中生成的，有一点要注意的是，我是通过从sql文件中提取sql语句然后在PrepareStatement中进行
	 * 处理，而sql语句的执行顺序对于外键是否能正确生成又有很大关系，比如那个contact表中需要和user建立外键关系，但是从Navicat中
	 * 生成的sql文件执行顺序是先删除contact然后生成contact表，这样的话会发生错误，因而需要调整对user表的操作到前面来，才可以保证
	 * sql语句的正确依次执行
	 * @throws IOException 
	 * 
	 * @throws Exception
	 */
	public void rebuildDatabase() throws SQLException, IOException {
		String sqlPath = MyTools.getSourcePath("contacts.sql");
		List<String> list = MyTools.loadSql(sqlPath);
		for (int i = 0; i < list.size(); i++) {
			execSQLNoRs(list.get(i));
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句中填充的参数
	 * @return
	 * @throws Exception
	 */
	private ResultSet execSQL(String sql, Object... args) throws SQLException {
		PreparedStatement ps;
		ResultSet rs = null;
		ps = JdbcTools.getConnection().prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i + 1, args[i]);
		}
		ps.execute();
		rs = ps.getResultSet();
		return rs;
	}
	
	public void execSQLNoRs(String sql,Object...args ) throws SQLException{
		PreparedStatement ps;
		ps = JdbcTools.getConnection().prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i + 1, args[i]);
		}
		ps.execute();
		ps.close();
	}

	/**
	 * 保存一个用户信息，包括手机号和验证码
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void saveUser(User user) throws Exception {
		String sql = "insert into user(phone,token) values(?,?)";
		execSQLNoRs(sql, user.getPhone(), user.getToken());
	}

	/**
	 * 保存联系人信息
	 * 
	 * @param contact
	 * @throws Exception
	 */
	public void saveContact(Contact contact) throws SQLException {
		String sql = "insert into contact(user_id,id,name,icon,is_black,state) values(?,?,?,?,?,?)";
		execSQLNoRs(sql, contact.getUserId(), contact.getId(), contact.getName(), contact.getIcon(), contact.getIsBlack(),
				contact.getFrom());
	}

	/**
	 * 保存联系人详情
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveContactDetail(ContactDetail detail) throws SQLException {
		String sql = "insert into contact_detail values(?,?,?,?)";
		execSQLNoRs(sql, detail.getContactId(), detail.getPhone(), detail.getProvince(), detail.getCity());
	}

	/**
	 * 根据客户手机号删除顾客
	 * 
	 * @param phone
	 *            客户手机号
	 * @throws Exception
	 */
	public void deleteUser(String phone) throws Exception {
		String sql = "delete from user where phone=?";
		execSQLNoRs(sql, phone);
	}

	/**
	 * 根据特定顾客的联系人名字删除联系人信息
	 * 
	 * @param userId
	 *            顾客id
	 * @param name
	 *            联系人名字
	 * @throws Exception
	 */
	public void deleteContact(Integer userId, String name) throws SQLException {
		String sql = "delete from contact where user_id=? and name=?";
		execSQLNoRs(sql, userId, name);
	}

	/**
	 * 根据客户手机号查询用户
	 * 
	 * @param phone
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public User queryUser(String phone) throws SQLException {
		String sql = "select*from user where phone=?";
		ResultSet rs =  execSQL(sql, phone);
		User user = null;
		if(rs.next()){
			user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
		}
		if(rs!=null){
			rs.close();
		}
		return user;
	}
	
	/**
	 * 根据客户id和联系人名字查询
	 * @param userId
	 * @param name
	 * @return 如果没找到返回null
	 * @throws Exception 
	 */
	public Contact queryContact(Integer userId,String name) throws SQLException{
		String sql = "select*from contact where user_id=? and name=?";
		ResultSet rs = execSQL(sql,userId,name);
		Contact contact = null;
		if(rs.next()){
			contact = new Contact(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getInt(7));
		}
		if(rs!=null){
			rs.close();
		}
		return contact;
	}
	
	/**
	 * 根据顾客id查询contact表
	 * 
	 * @param userId
	 *            客户id
	 * @return
	 * @throws Exception
	 */
	public List<Contact> queryContacts(Integer userId) throws SQLException {
		String sql = "select*from contact where user_id=?";
		ResultSet rs = execSQL(sql,userId);
		List<Contact> list = new ArrayList<>();
		while(rs.next()){
			Contact contact = new Contact(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getInt(7));
			list.add(contact);
		}
		if(rs!=null){
			rs.close();
		}
		return list;
	}
	
	/**
	 * 根据contact表主键contactId查询contact_detail表
	 * @param contactId 
	 * @return
	 * @throws SQLException 
	 */
	public List<ContactDetail> queryContactDetail(Integer contactId) throws SQLException{
		String sql  = "select*from contact_detail where contact_id=?";
		ResultSet rs = execSQL(sql,contactId);	
		List<ContactDetail> list = new ArrayList<>();
		while(rs.next()){
			ContactDetail detail = new ContactDetail(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			list.add(detail);
		}
		if(rs!=null){
			rs.close();
		}
		return list;
	}
	
}
