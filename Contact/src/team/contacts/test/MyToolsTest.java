package team.contacts.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import team.contacts.domain.Contact;
import team.contacts.domain.TransmissionData;
import team.contacts.domain.User;
import team.contacts.service.ResultCode;
import team.contacts.util.JdbcTools;
import team.contacts.util.MyTools;

@SuppressWarnings("unchecked")
public class MyToolsTest {

	/**
	 * 测试通过配置文件配置连接池
	 */
	@Test
	public void testDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource("c3p0configure");
		System.out.println(dataSource);
	}

	/**
	 * 测试从c3p0连接池中获得连接
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testJdbcConnection() throws SQLException {
		Connection connection = JdbcTools.getConnection();
		System.out.println(connection);
	}

	/**
	 * 测试将sql文件分解成sql语句
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoadSql() throws Exception {
		MyToolsTest test = new MyToolsTest();
		String sqlPath = test.getClass().getResource("/contacts.sql").getPath();
		System.out.println(sqlPath);
	}
	
	/**
	 * 测试将Bean对象转化为json字符串
	 */
	@Test
	public void testGetJson(){
		TransmissionData data = new TransmissionData();
		data.setUser(new User("15626485455", "234243"));
		List<Contact> contacts = new ArrayList<>();
		for(int i=0;i<3;i++){
			Contact contact = new Contact(3, 3, "zzf"+i, "sdf", false, 3);
			contacts.add(contact);
		}
		data.setData(contacts);
		System.out.println(MyTools.getJson(data, TransmissionData.class));;
	}
	
	/**
	 * 测试从字符串中构建Bean对象
	 */
	@Test
	public void testGetBean(){
		String json = "{'user':{'phone':'15626485455','token':'3424234'},'data':[{'userId':1,'id':2,'name':'zzf','icon':'sdff','isBlack':false,'state':1,'details':[{'contactId':1,'phone':'15626485466','province':'广东省','city':'汕头市'}]}]}";		
		System.out.println(MyTools.getBean(json, TransmissionData.class));
	}

	public static void main(String[] args) {
		ResultCode code = ResultCode.LOGIN_SUCCESS;
		System.out.println(code.getCode());
	}
}
