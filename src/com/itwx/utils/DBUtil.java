package com.itwx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBUtil {

	private String jdbcName="com.mysql.jdbc.Driver";
	private String dbUrl="jdbc:mysql://localhost:3306/db_studentinfo";
	private String dbUsername="root";
	private String dbPassword="123456";
	
	/**
	 * 获取数据库连接
	 * @throws ClassNotFoundException 
	 */
	public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
		return con;
	}
	/**
	 * 关闭数据
	 * @throws SQLException 
	 */
	public void closeCon(Connection con) throws SQLException {
		if(con != null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		DBUtil dbUtil = new DBUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
