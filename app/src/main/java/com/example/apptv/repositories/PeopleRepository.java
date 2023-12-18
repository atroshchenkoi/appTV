package com.example.apptv.repositories;

import com.example.apptv.models.User;
import com.example.apptv.data_base_connection.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeopleRepository {
    private Connection connection;

    public PeopleRepository() {
        try {
            this.connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User selectUserByPhone(String phone) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("select * from users where number = ?");
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return new User(resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"));
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(User user) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("insert into users(phone, name, password) values (?, ?, ?)");
            statement.setString(1, user.getPhone());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
