package com.service;

import com.DB.Database;
import com.model.UserInfo;
import java.sql.SQLException;

/**
 *
 * @author Cristian
 * Clase que realiza operaciones a la tabla user_account_info en la DB
 * de MySQL 
 */
public class UserInfoService extends Database {
    
    //* Obtener datos personales por el id del usuario
    public UserInfo getUserInfo(int userId) throws SQLException {
        String sql = "SELECT * FROM user_account_info WHERE user_id = ?";
        applyConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        result = statement.executeQuery();
        UserInfo data = null;
        if(result.next())
            data = new UserInfo(result.getInt("id"), result.getInt("user_id"), result.getString("name"),
                                result.getString("last_name"), result.getString("ficha"), result.getString("tlf"));
        closeConnection();
        return data;
    }
    
    //* Crear datos personales de un usuario
    public void createUserInfo(UserInfo data) throws SQLException {
        String sql = "INSERT INTO user_account_info(first_name, last_name, phone, email, user_id) VALUES (?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, data.getFirstName());
        statement.setString(2, data.getLastName());
        statement.setString(3, data.getPhone());
        statement.setString(4, data.getEmail());
        statement.setInt(5, data.getUserId());
        statement.executeUpdate();
        connection.commit();
        closeConnection();
    }
    
    //* Editar datos personales de un usuario
    public void updateUserInfo(UserInfo data) throws SQLException {
        String sql = "UPDATE user_account_info SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, data.getFirstName());
        statement.setString(2, data.getLastName());
        statement.setString(3, data.getPhone());
        statement.setString(4, data.getEmail());
        statement.setInt(5, data.getId());
        statement.executeUpdate();
        connection.commit();
        closeConnection();
    }
}
