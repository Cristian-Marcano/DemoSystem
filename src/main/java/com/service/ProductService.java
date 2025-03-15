package com.service;

import com.DB.Database;
import com.model.Product;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristian
 * Clase que realiza operaciones a la tabla product en la DB
 * de MySQL 
 */
public class ProductService extends Database {
    
    //* Obtener el Product por su id
    public Product getProduct(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        Product product = null;
        if(result.next())
            product = new Product(result.getInt("id"), result.getInt("availability"), result.getString("title"),
                                result.getString("detail"), result.getBigDecimal("price"));
        closeConnection();
        return product;
    }
    
    //* Obtener todos los productos
    public List<Product> getProducts() throws SQLException {
        String sql = "SELECT * FROM product";
        applyConnection();
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while(result.next())
            products.add(new Product(result.getInt("id"), result.getInt("availability"), result.getString("title"),
                                    result.getString("detail"), result.getBigDecimal("price")));
        closeConnection();
        return products;
    }
    
    //* Buscar los productos por sus distintos tipos de datos, title, detail, price
    public List<Product> searchProduct(List<String[]> sentencesAndValues) throws SQLException {
        String sql = "SELECT * FROM product";
        if(!sentencesAndValues.isEmpty()) {
            sql += " WHERE ";
            for(String[] sentence: sentencesAndValues) 
                sql += (sql.endsWith("? ")) ? "AND " + sentence[0] : sentence[0];
        }
        applyConnection();
        statement = connection.prepareStatement(sql);
        for(int i=0; i<sentencesAndValues.size(); i++) statement.setString(i+1, sentencesAndValues.get(i)[1]);
        result = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while(result.next())
            products.add(new Product(result.getInt("id"), result.getInt("availability"), result.getString("title"),
                                    result.getString("detail"), result.getBigDecimal("price")));
        closeConnection();
        return products;
    }
    
    //* Crear Producto
    public void createProduct(String title, String detail, int availability, BigDecimal price) throws SQLException {
        String sql = "INSERT INTO product(title, detail, availability, price) VALUES (?,?,?,?)";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setString(2, detail);
        statement.setInt(3, availability);
        statement.setBigDecimal(4, price);
        statement.executeUpdate();
        closeConnection();
    }
    
    //* Editar Producto
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE product SET title = ?, detail = ?, avalability = ?, price = ? WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setString(1, product.getTitle());
        statement.setString(2, product.getDetail());
        statement.setInt(3, product.getAvailability());
        statement.setBigDecimal(4, product.getPrice());
        statement.executeUpdate();
        closeConnection();
    }
    
    public void decreaseStock(int id, int quantity) throws SQLException {
        String sql = "UPDATE product SET availability = availability - ? WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, quantity);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
}
