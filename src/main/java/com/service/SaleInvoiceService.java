package com.service;

import com.DB.Database;
import com.model.Client;
import com.model.SaleInvoice;
import com.model.User;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristian
 * Clase que realiza operaciones a la tabla sale_invoice en la DB
 * de MySQL 
 */
public class SaleInvoiceService extends Database {
    
    //* Obtener factura por su id
    public Object[] getSaleInvoice(int id) throws SQLException {
        String sql = "SELECT * FROM sale_invoice AS si JOIN user_account AS ua ON si.user_account_id = ua.id JOIN costumer AS c ON si.costumer_id = c.id WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        Object[] invoice = null;
        if(result.next())
            invoice = new Object[]{new SaleInvoice(result.getInt("si.id"), result.getInt("si.user_account_id"), result.getInt("si.costumer_id"),
                                                    result.getBigDecimal("si.total"), result.getBigDecimal("si.tax"), result.getTimestamp("si.create_at")),
                                   new User(result.getInt("ua.id"), result.getString("ua.username"), result.getString("ua.keyword"),
                                            result.getString("ua.position"), result.getBoolean("ua.access")),
                                   new Client(result.getInt("c.id"), result.getString("c.full_name"), result.getString("c.rif"),
                                                result.getString("c.email"), result.getString("c.phone"))};
        closeConnection();
        return invoice;
    }
    
    //* Obtener facturas
    public List<Object[]> getSalesInvoices() throws SQLException {
        String sql = "SELECT * FROM sale_invoice AS si JOIN user_account AS ua ON si.user_account_id = ua.id "
                + "JOIN costumer AS c ON si.costumer_id = c.id ORDER si.id DESC";
        applyConnection();
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        List<Object[]> invoices = new ArrayList<>();
        while(result.next())
            invoices.add(new Object[]{new SaleInvoice(result.getInt("si.id"), result.getInt("si.user_account_id"), result.getInt("si.costumer_id"),
                                                        result.getBigDecimal("si.total"), result.getBigDecimal("si.tax"), result.getTimestamp("si.create_at")),
                                      new User(result.getInt("ua.id"), result.getString("ua.username"), result.getString("ua.keyword"),
                                                result.getString("ua.position"), result.getBoolean("ua.access")),
                                      new Client(result.getInt("c.id"), result.getString("c.full_name"), result.getString("c.rif"),
                                                    result.getString("c.email"), result.getString("c.phone"))});
        closeConnection();
        return invoices;
    }
    
    //* Buscar facturas por su distintos tipos de datos, createAt, total, tax, etc
    public List<Object[]> searchSalesInvoices(List<String[]> sentencesAndValues) throws SQLException {
        String sql = "SELECT * FROM sale_invoice AS si JOIN user_account AS ua ON si.user_account_id = ua.id JOIN costumer AS c ON si.costumer_id = c.id";
        if(!sentencesAndValues.isEmpty()) {
            sql += " WHERE ";
            for(String[] sentence: sentencesAndValues) 
                sql += (sql.endsWith("? ")) ? "AND " + sentence[0] : sentence[0];
        }
        applyConnection();
        statement = connection.prepareStatement(sql);
        for(int i=0; i<sentencesAndValues.size(); i++) statement.setString(i+1, sentencesAndValues.get(i)[1]);
        result = statement.executeQuery();
        List<Object[]> invoices = new ArrayList<>();
        while(result.next())
            invoices.add(new Object[]{new SaleInvoice(result.getInt("si.id"), result.getInt("si.user_account_id"), result.getInt("si.costumer_id"),
                                                        result.getBigDecimal("si.total"), result.getBigDecimal("si.tax"), result.getTimestamp("si.create_at")),
                                      new User(result.getInt("ua.id"), result.getString("ua.username"), result.getString("ua.keyword"),
                                                result.getString("ua.position"), result.getBoolean("ua.access")),
                                      new Client(result.getInt("c.id"), result.getString("c.full_name"), result.getString("c.rif"),
                                                    result.getString("c.email"), result.getString("c.phone"))});
        closeConnection();
        return invoices;
    }
    
    //* Crear factura de venta
    public int createSaleInvoice(BigDecimal total, BigDecimal tax, int userId, int clientId) throws SQLException {
        String sql = "INSERT INTO sale_invoice(total, tax, user_account_id, costumer_id) VALUES (?,?,?,?)";
        if(connection.isClosed()) applyConnection();
        if(connection.getAutoCommit()) connection.setAutoCommit(false);
        statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setBigDecimal(1, total);
        statement.setBigDecimal(2, tax);
        statement.setInt(3, userId);
        statement.setInt(4, clientId);
        statement.executeUpdate();
        result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        return id;
    }
    
    //* Editar factura de venta
    public void updateSaleInvoice(SaleInvoice invoice) throws SQLException {
        String sql = "UPDATE sale_invoice SET total = ?, tax = ? user_account_id = ?, costumer_id = ?, create_at = ? WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setBigDecimal(1, invoice.getTotal());
        statement.setBigDecimal(2, invoice.getTax());
        statement.setInt(3, invoice.getUserId());
        statement.setInt(4, invoice.getClientId());
        statement.setTimestamp(5, invoice.getCreateAt());
        statement.setInt(6, invoice.getId());
        statement.executeUpdate();
        closeConnection();
    }
    
    //* Remover factura de venta ¡SOLO APLICA SI LAS VENTAS DE LA FACTURA FUERON REMOVIDAS!
    public void removeSaleInvoice(int id) throws SQLException {
        String sql = "DELETE * FROM sale_invoice WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        closeConnection();
    }
}
