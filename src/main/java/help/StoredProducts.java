

package help;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Keene Cabahug - 2444791 
 * CIT 4423 01 
 * Nov 13, 2022 
 * Windows 11 Home
 */

public class StoredProducts {
    private Products[] products;
    public StoredProducts(){
        this.products = items();
    }

    private Products[] items(){
        ArrayList<Products> names = new ArrayList<>();
        	// stores product names and returns them using an array.
        String query = "SELECT product_name FROM product_info";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root",
					"Tarantadoako1999");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                names.add(new Products(resultSet.getString("product_name")));
            }
            
            return names.toArray(new Products[names.size()]);

        } catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
            return new Products[0];
        }
    }
    
    // getters

    public Products[] getProducts() {
        return this.products;
    }

    public Products getProducts(int index) {
        return products[index];
    }

    public Products[] getProducts(int begin, int end){
    	Products[] result = new Products[end - begin];
        int index = 0;
        
        // Iterates until the end of products
        for(int i = begin; i < end; i++){
            result[index] = products[i];
            index++;
        }
        return result;
    }

    public Products getProducts(String name){
        return new Products(name);
    }

    public int getAmountListed(){
        return products.length;
    }

}