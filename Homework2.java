package ro.emanuel.oop.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Homework2 {
	
	public static void main(String[] args) throws SQLException {
		
		//OPEN CONNECTION
		Properties connectionProps = new Properties();
		connectionProps.put("user", "root");
		connectionProps.put("password","");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/crud-test",
				connectionProps);
		Statement stmt = conn.createStatement(); //CREATE SQL STATEMENT
		
		//INSERT - CREATE OPERATION
		int order_id = 355;
        int user_id = 41;
        int product_id = 105;
        
        final String insertOrders = "INSERT INTO `orders`(`order_id`, `user_id`, `product_id`) VALUES (?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(insertOrders);
		ps.setInt(1, order_id);
		ps.setInt(2, user_id);
		ps.setInt(3, product_id);
		
		int rowsAffected = ps.executeUpdate();
		System.out.println(rowsAffected + " rows affected");
		
		
		//UPDATE - modify one or more rows from table
		conn.setAutoCommit(false);
		try {
			Statement s1 = conn.createStatement();
			s1.executeUpdate("UPDATE `orders` SET `product_id` = `product_id` -1 WHERE `user_id` = 1");
			conn.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			conn.rollback();
		}
		
		
		//DELETE 
		final String orderDelete = "DELETE FROM orders WHERE user_id >= ? ";
        PreparedStatement psd = conn.prepareStatement(orderDelete);
        psd.setInt(1, 5);
        int result = psd.executeUpdate();  
        System.out.println(result);
		
		
		//RESULTS - READ OPERATION
		ResultSet rs = stmt.executeQuery("select * from orders");
		while(rs.next()) {
			int o_id = rs.getInt("order_id");
			int u_id = rs.getInt("user_id");
			int p_id = rs.getInt("product_id");
			System.out.println(o_id + "|" + u_id + "|" +  p_id);
		}
		conn.close();
	}
}
