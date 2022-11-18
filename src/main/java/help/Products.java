package help;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.javafaker.Faker;

/**
 * Keene Cabahug - 2444791
 * CIT 4423 01
 * Nov 13, 2022
 * Windows 11 Home
 */

public class Products {

	private String productName;
	private String productDescription;
	private double price;
	private int quantity;

	public Products() {
		//faker data
		
		Faker faker = new Faker();
		this.productName = faker.commerce().productName();
		this.productDescription = faker.lorem().sentence();
		this.price = (int) faker.number().randomNumber();
		this.quantity = 10;

		if (this.quantity < 0) {
			this.quantity *= -1;
		}
		if (this.price < 0) {
			this.price *= -1;
		}
	}

	public Products(String productName) {
		this.productName = productName;

		StringBuilder descriptionBuild = new StringBuilder("SELECT * FROM project_database.product_info ");
		descriptionBuild.append(String.format("WHERE product_name = '%s'", this.productName));
		String description = descriptionBuild.toString();
		System.out.println("Retrieving..");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root",
					"Tarantadoako1999");
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(description);
			result.next();
			this.productDescription = result.getString("product_description");
			this.price = result.getDouble("price");
			this.quantity = result.getInt("quantity");

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int addToSQL() {

		try {
			//Adds product properties to SQL

			StringBuilder build = new StringBuilder(
					"INSERT INTO product_info(product_name, product_description, quantity, price) ");
			build.append(String.format(" values('%s', '%s', %s, %s)", this.productName, this.productDescription,
					this.price, this.quantity));
			String query = build.toString();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root",
					"Tarantadoako1999");
			Statement statement = con.createStatement();
			statement.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	//getters

	public String getProductName() {
		return productName;
	}

	public String getDescription() {
		return productDescription;
	}

	public String getPriceAsString() {

		return String.format("$%,.2f", this.price);
	}

	public Double getPrice() {
		return this.price;
	}

	public int getQuantity() {
		return quantity;
	}
}