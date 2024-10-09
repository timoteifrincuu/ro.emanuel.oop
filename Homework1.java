package ro.emanuel.oop.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Homework1 {
	
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
		int productId = 35;
        String name = "Coca-Cola";
        double price = 4.5;
        int stockQuantity = 10;
        
        final String insertProducts = "INSERT INTO `products`(`product_id`, `name`, `price`, `stock_quantity`) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(insertProducts);
		ps.setInt(1, productId);
		ps.setString(2, name);
		ps.setDouble(3, price);
		ps.setInt(4, stockQuantity);
		
		int rowsAffected = ps.executeUpdate();
		System.out.println(rowsAffected + " rows affected");
		
		
		//UPDATE - modify one or more rows from table
		conn.setAutoCommit(false);
		try {
			Statement s1 = conn.createStatement();
			s1.executeUpdate("UPDATE `products` SET `stock_quantity` = `stock_quantity` -5 WHERE `product_id` = 1");
			conn.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			conn.rollback();
		}
		
		
		//DELETE 
		final String productDelete = "DELETE FROM products WHERE product_id >= ? ";
        PreparedStatement psd = conn.prepareStatement(productDelete);
        psd.setInt(1, 5);
        int result = psd.executeUpdate();  
        System.out.println(result);
		
		
		//RESULTS - READ OPERATION
		ResultSet rs = stmt.executeQuery("select * from products");
		while(rs.next()) {
			int p_id = rs.getInt("product_id");
			String p_name = rs.getString("name");
			double p_price = rs.getDouble("price");
			int stock_q = rs.getInt("stock_quantity");
			System.out.println(p_id + "|" + p_name + "|" + p_price + "|" + stock_q);
		}
		conn.close();
	}
}
