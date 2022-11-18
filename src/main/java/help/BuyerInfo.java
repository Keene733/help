package help;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.javafaker.Faker;


/**
 * Keene Cabahug - 2444791 
 * CIT 4423 01 
 * Nov 13, 2022 
 * Windows 11 Home
 */

public class BuyerInfo {

	Connection conn = null;
	Statement stmt = null;

	// faker data
	Faker faker = new Faker();
	
	String fullName;
	String mailingAddress;
	String billingAddress;
	String creditCard;
	
	


	public BuyerInfo() {
		//faker data
		
		this.fullName = faker.name().fullName();
		this.mailingAddress = faker.address().streetAddress();
		this.billingAddress = faker.address().streetAddress();
		this.creditCard = faker.business().creditCardNumber();
		

	}

	public void connectSQL() {
		try

		{

			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "Tarantadoako1999");
			

			stmt = conn.createStatement();

			StringBuilder sql = new StringBuilder(
					"INSERT INTO buyer_info(full_name, billing_address, residential_address, credit_card) ");
			String values = String.format("values('%s','%s','%s','%s')", fullName, billingAddress,
					mailingAddress, creditCard);
			sql.append(values);

			stmt.executeUpdate(sql.toString());
			System.out.println("connected to database");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
