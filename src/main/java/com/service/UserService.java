package com.service;

import com.DB.Database;
import com.demo.Demo;
import com.model.User;
import com.model.UserInfo;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristian
 * Clase que realiza operaciones a la tabla user_account en la DB
 * de MySQL 
 */
public class UserService extends Database {
    
    //* Obtener usuario por su id
    public Object[] getUser(int id) throws SQLException {
        String sql = "SELECT * FROM user_account AS u JOIN user_account_info AS ui WHERE u.id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        result = statement.executeQuery();
        Object[] user = null;
        if(result.next())
            user = new Object[]{new User(result.getInt("u.id"), result.getString("u.username"), result.getString("u.keyword"), 
                                    result.getString("position"), result.getBoolean("u.access")),
                                new UserInfo(result.getInt("ui.id"), result.getInt("ui.user_account_id"), result.getString("ui.first_name"),
                                    result.getString("ui.last_name"), result.getString("ui.phone"), result.getString("ui.ci"))};
        closeConnection();
        return user;
    }
    
    //* Buscar usuario por su username
    public User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM user_account WHERE username = ? AND access = 1";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        result = statement.executeQuery();
        User user = null;
        if(result.next())
            user = new User(result.getInt("id"), result.getString("username"), result.getString("keyword"),
                            result.getString("position"), result.getBoolean("access"));
        closeConnection();
        return user;
    }
    
    //* Obtener la cantidad de usuarios que hay en la base de datos
    public int getCountUsers() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM user_account";
        applyConnection();
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        int count = 0;
        if(result.next())
            count = result.getInt("count");
        closeConnection();
        return count;
    }
    
    //* Obtener todos los usuarios en orden de creación 
    public List<Object[]> getUsers() throws SQLException {
        String sql = "SELECT * FROM user_account AS u JOIN user_account_info AS ui ON u.id = ui.user_id WHERE u.access = 1 AND u.id <> ? ORDER BY u.id DESC";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, Demo.user.getId());
        result = statement.executeQuery();
        List<Object[]> users = new ArrayList<>();
        while(result.next())
            users.add(new Object[]{new User(result.getInt("u.id"), result.getString("u.username"), result.getString("u.keyword"),
                                        result.getString("u.position"), result.getBoolean("u.access")),
                                   new UserInfo(result.getInt("ui.id"), result.getInt("ui.user_id"), result.getString("ui.first_name"),
                                        result.getString("ui.last_name"), result.getString("ui.phone"), result.getString("pd.ci"))});
        closeConnection();
        return users;
    }
    
    //* Buscar usuarios por sus distintos datos, como username, role, nombre o apellido etc
    public List<Object[]> searchUsers(List<String[]> sentencesAndValues) throws SQLException {
        String sql = "SELECT * FROM user_account AS u JOIN user_account_info AS ui ON u.id = ui.user_id";
        if(!sentencesAndValues.isEmpty()) {
            sql += " WHERE ";
            for(String[] sentence: sentencesAndValues) 
                sql += (sql.endsWith("? ")) ? "AND " + sentence[0] : sentence[0];
        }
        applyConnection();
        statement = connection.prepareStatement(sql);
        int i;
        for(i=0; i<sentencesAndValues.size(); i++) statement.setString(i+1, sentencesAndValues.get(i)[1]);
        statement.setInt(i+2, Demo.user.getId());
        result = statement.executeQuery();
        List<Object[]> users = new ArrayList<>();
        while(result.next()) 
            users.add(new Object[]{new User(result.getInt("u.id"), result.getString("u.username"), result.getString("u.keyword"),
                                        result.getString("u.position"), result.getBoolean("u.access")),
                                   new UserInfo(result.getInt("ui.id"), result.getInt("ui.user_id"), result.getString("ui.first_name"),
                                        result.getString("ui.last_name"), result.getString("ui.phone"), result.getString("pd.ci"))});
        closeConnection();
        return users;
    }
    
    //* Crear usuario 
    public int createUser(String username, String password, String position) throws SQLException {
        String sql = "INSERT INTO user_account(username, keyword, position) VALUES (?,?,?)";
        applyConnection();
        if(connection.getAutoCommit()) connection.setAutoCommit(false);
        statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, position);
        statement.executeUpdate();
        result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        return id;
    }
    
    //* Editar usuario
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE user_account SET username = ?, keyword = ?, position = ? WHERE id = ?";
        applyConnection();
        if(connection.getAutoCommit()) connection.setAutoCommit(false);
        statement = connection.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getPosition());
        statement.setInt(4, user.getId());
        statement.executeUpdate();
    }
    
    //* Desactivar usuario (no sera visible en el listado y tampoco podra entrar en la app)
    public void removeUser(int id) throws SQLException {
        String sql = "UPDATE user_account SET access = 0 WHERE id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        closeConnection();
    }
}
