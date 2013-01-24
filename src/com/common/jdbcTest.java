package com.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.CommunicationsException;

public class jdbcTest {
	public static void main(String[] args) throws Exception {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		Connection conn = getDBConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM user");
		
		while (rs.next()) {
			String name = rs.getString("name"); 
			System.out.println("userName : " + name);
		}
	}
	
	public static Connection getDBConnection() throws SQLException, ClassNotFoundException, CommunicationsException{
		Properties props = new Properties();
		props.put("user", "skywing");
		props.put("password", "triumph82");
		String url = "jdbc:mysql://ec2-122-248-220-176.ap-southeast-1.compute.amazonaws.com:33067/test";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, props);
		
		return conn;
		
	}
}
