package com.service;

import com.DB.Database;
import com.model.Client;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristian
 * Clase que realiza operaciones a la tabla costumer en la DB
 * de MySQL 
 */
public class ClientService extends Database {
    
    //* Obtener el Cliente por su id
    public Client getClient(int id) throws SQLException {
        String sql = "SELECT * FROM costumer WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        Client client = null;
        if(result.next())
            client = new Client(result.getInt("id"), result.getString("full_name"), result.getString("rif"), result.getString("email"), result.getString("phone"));
        closeConnection();
        return client;
    }
    
    //* Buscar el cliente por su rif
    public Client getClient(String rif) throws SQLException {
        String sql = "SELECT * FROM costumer WHERE rif = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setString(1, rif);
        result = statement.executeQuery();
        Client client = null;
        if(result.next())
            client = new Client(result.getInt("id"), result.getString("full_name"), result.getString("rif"), result.getString("email"), result.getString("phone"));
        closeConnection();
        return client;
    }
    
    //* Obtener todos los clientes en orden de creacion
    public List<Client> getClients() throws SQLException {
        String sql = "SELECT * FROM costumer ORDER BY id DESC";
        applyConnection();
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        List<Client> clients = new ArrayList<>();
        while(result.next())
            clients.add(new Client(result.getInt("id"), result.getString("full_name"), result.getString("rif"), result.getString("email"), result.getString("phone")));
        closeConnection();
        return clients;
    }
    
    //* Buscar los clientes por sus distintos tipos de datos, fullName, rif, email o phone
    public List<Client> searchClient(List<String[]> sentencesAndValues) throws SQLException {
        String sql = "SELECT * FROM costumer";
        if(!sentencesAndValues.isEmpty()) {
            sql += " WHERE ";
            for(String[] sentence: sentencesAndValues) 
                sql += (sql.endsWith("? ")) ? "AND " + sentence[0] : sentence[0];
        }
        applyConnection();
        statement = connection.prepareStatement(sql);
        for(int i=0; i<sentencesAndValues.size(); i++) statement.setString(i+1, sentencesAndValues.get(i)[1]);
        result = statement.executeQuery();
        List<Client> clients = new ArrayList<>();
        while(result.next())
            clients.add(new Client(result.getInt("id"), result.getString("full_name"), result.getString("rif"), result.getString("email"), result.getString("phone")));
        closeConnection();
        return clients;
    }
    
    //* Crear Cliente
    public int createClient(String fullName, String rif, String phone, String email) throws SQLException {
        String sql = "INSERT INTO costumer(full_name, rif, phone, email) VALUES (?, ?, ?, ?)";
        applyConnection();
        statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, fullName);
        statement.setString(2, rif);
        statement.setString(3, phone);
        statement.setString(4, email);
        statement.executeUpdate();
        result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        return id;
    }
    
    //* Editar cliente
    public void updateClient(Client client) throws SQLException {
        String sql = "UPDATE costumer SET full_name = ?, rif = ?, phone = ?, email = ? WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setString(1, client.getFullName());
        statement.setString(2, client.getRif());
        statement.setString(3, client.getPhone());
        statement.setString(4, client.getEmail());
        statement.setInt(5, client.getId());
        statement.executeUpdate();
        closeConnection();
    }
}
