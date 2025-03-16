package com.service;

import com.DB.Database;
import com.model.Product;
import com.model.Sale;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristian
 * Clase que realiza operaciones a la tabla product en la DB
 * de MySQL 
 */
public class SaleService extends Database {
    
    //* Obtener venta por el id
    public Object[] getSale(int id) throws SQLException {
        String sql = "SELECT * FROM sale AS s JOIN product AS p ON s.product_id = p.id WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        Object[] sale = null;
        if(result.next())
            sale = new Object[]{new Sale(result.getInt("s.id"), result.getInt("s.sale_invoice_id"),result.getInt("s.product_id"),
                                            result.getInt("s.quantity"), result.getBigDecimal("s.amount")),
                                new Product(result.getInt("p.id"), result.getInt("p.availability"), result.getString("p.title"),
                                            result.getString("p.detail"), result.getBigDecimal("p.price"))};
        closeConnection();
        return sale;
    }
    
    //* Obtener ventas por el id de factura (invoice)
    public List<Object[]> getSalesByInvoice(int saleInvoiceId) throws SQLException {
        String sql = "SELECT * FROM sale WHERE sale_invoice_id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, saleInvoiceId);
        result = statement.executeQuery();
        List<Object[]> sales = new ArrayList<>();
        while(result.next())
            sales.add(new Object[]{new Sale(result.getInt("s.id"), result.getInt("s.sale_invoice_id"),result.getInt("s.product_id"),
                                            result.getInt("s.quantity"), result.getBigDecimal("s.amount")),
                                   new Product(result.getInt("p.id"), result.getInt("p.availability"), result.getString("p.title"),
                                                result.getString("p.detail"), result.getBigDecimal("p.price"))});
        closeConnection();
        return sales;
    }
    
    //* Crear ventas
    public void createSales(List<Sale> sales) throws SQLException {
        String sql = "INSERT INTO sale(quantity, amount, product_id, sale_invoice_id) VALUES (?,?,?,?)";
        statement = connection.prepareStatement(sql);
        for(Sale sale: sales) {
            statement.setInt(1, sale.getQuantity());
            statement.setBigDecimal(2, sale.getAmount());
            statement.setInt(3, sale.getProductId());
            statement.setInt(4, sale.getSaleInvoiceId());
            statement.addBatch();
        }
        statement.executeBatch();
        connection.commit();
        closeConnection();
    }
    
    //* Editar ventas
    public void updateSales(List<Sale> sales) throws SQLException {
        String sql = "UPADATE sale SET quantity = ?, amount = ? WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        for(Sale sale: sales) {
            statement.setInt(1, sale.getQuantity());
            statement.setBigDecimal(2, sale.getAmount());
            statement.setInt(3, sale.getId());
            statement.addBatch();
        }
        statement.executeBatch();
        closeConnection();
    }
    
    //* remover venta
    public void removeSale(int id) throws SQLException {
        String sql = "DELETE FROM sale WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        closeConnection();
    }
}
