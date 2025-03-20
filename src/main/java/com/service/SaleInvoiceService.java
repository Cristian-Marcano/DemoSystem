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
 */
public class SaleInvoiceService extends Database {
    
    public Object[] getSaleInvoice(int id) throws SQLException {
        String sql = "SELECT * FROM sale_invoice AS si JOIN user_account AS ua ON si.user_account_id = ua.id JOIN costumer AS c ON si.costumer_id = c.id WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        Object[] invoice = null;
        if(result.next())
            invoice = new Object[]{new SaleInvoice(result.getInt("si.id"), result.getInt("si.user_account_id"), result.getInt("si.costumer_id"),
                                                    result.getBigDecimal("si.total"), result.getTimestamp("si.create_at")),
                                   new User(result.getInt("ua.id"), result.getString("ua.username"), result.getString("ua.keyword"),
                                            result.getString("ua.position"), result.getBoolean("ua.access")),
                                   new Client(result.getInt("c.id"), result.getString("c.full_name"), result.getString("c.rif"),
                                                result.getString("c.email"), result.getString("c.phone"))};
        closeConnection();
        return invoice;
    }
    
    public List<Object[]> getSalesInvoices() throws SQLException {
        String sql = "SELECT * FROM sale_invoice AS si JOIN user_account AS ua ON si.user_account_id = ua.id "
                + "JOIN costumer AS c ON si.costumer_id = c.id ORDER si.id DESC";
        applyConnection();
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        List<Object[]> invoices = new ArrayList<>();
        while(result.next())
            invoices.add(new Object[]{new SaleInvoice(result.getInt("si.id"), result.getInt("si.user_account_id"), result.getInt("si.costumer_id"),
                                                        result.getBigDecimal("si.total"), result.getTimestamp("si.create_at")),
                                      new User(result.getInt("ua.id"), result.getString("ua.username"), result.getString("ua.keyword"),
                                                result.getString("ua.position"), result.getBoolean("ua.access")),
                                      new Client(result.getInt("c.id"), result.getString("c.full_name"), result.getString("c.rif"),
                                                    result.getString("c.email"), result.getString("c.phone"))});
        closeConnection();
        return invoices;
    }
    
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
                                                        result.getBigDecimal("si.total"), result.getTimestamp("si.create_at")),
                                      new User(result.getInt("ua.id"), result.getString("ua.username"), result.getString("ua.keyword"),
                                                result.getString("ua.position"), result.getBoolean("ua.access")),
                                      new Client(result.getInt("c.id"), result.getString("c.full_name"), result.getString("c.rif"),
                                                    result.getString("c.email"), result.getString("c.phone"))});
        closeConnection();
        return invoices;
    }
    
    public int createSaleInvoice(BigDecimal total, int userId, int clientId) throws SQLException {
        String sql = "INSERT INTO sale_invoice(total, user_account_id, costumer_id) VALUES (?,?,?)";
        if(connection.isClosed()) applyConnection();
        if(connection.getAutoCommit()) connection.setAutoCommit(false);
        statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setBigDecimal(1, total);
        statement.setInt(2, userId);
        statement.setInt(3, clientId);
        statement.executeUpdate();
        result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        return id;
    }
    
    public void updateSaleInvoice(SaleInvoice invoice) throws SQLException {
        String sql = "UPDATE sale_invoice SET total = ?, user_account_id = ?, costumer_id = ?, create_at = ? WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setBigDecimal(1, invoice.getTotal());
        statement.setInt(2, invoice.getUserId());
        statement.setInt(3, invoice.getClientId());
        statement.setTimestamp(4, invoice.getCreateAt());
        statement.setInt(5, invoice.getId());
        statement.executeUpdate();
        closeConnection();
    }
    
    public void removeSaleInvoice(int id) throws SQLException {
        String sql = "DELETE * FROM sale_invoice WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        closeConnection();
    }
}
