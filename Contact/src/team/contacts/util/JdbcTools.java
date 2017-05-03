package team.contacts.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcTools {
	/**
	 * 只需要一次静态初始化即可，因为整个程序运行中一个连接池就够了
	 */
	private static ComboPooledDataSource dataSource = null;
	/**
	 * 对数据库连接池进行初始化
	 */
	static {
		dataSource = new ComboPooledDataSource("c3p0configure");
	}

	/**
	 * 从连接池中获得一个连接
	 * 
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}