package com.swlc.gadgetmart.backend.main.repo.impl;



import com.swlc.gadgetmart.backend.main.database.DBConnection;
import com.swlc.gadgetmart.backend.main.dto.UserDto;
import com.swlc.gadgetmart.backend.main.repo.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    @Override
    public UserDto authenticateUser(String username) throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select * from user_login where userName=?";

        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        UserDto userResponse = null;

        while (resultSet.next()) {
            userResponse = new UserDto();
            userResponse.setUserId(resultSet.getInt(1));
            userResponse.setName(resultSet.getString(2));
            userResponse.setUserType(resultSet.getString(3));
            userResponse.setUserName(resultSet.getString(4));
            userResponse.setPassword(resultSet.getString(5));
            userResponse.setAddress(resultSet.getString(6));
            userResponse.setContact(resultSet.getString(7));
            userResponse.setEmail(resultSet.getString(8));
        }
        closeConnection();
        return userResponse;
    }

    @Override
    public boolean createUser(UserDto user) throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "INSERT INTO user_login(name, userType , userName , password , address , contact,email) VALUES (?,?,?,?,?,?,?) ";

        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, "customer");
        preparedStatement.setString(3, user.getUserName());
        preparedStatement.setString(4, DigestUtils.md5Hex(user.getPassword()));
        preparedStatement.setString(5, user.getAddress());
        preparedStatement.setString(6, user.getContact());
        preparedStatement.setString(7, user.getEmail());
        int i = preparedStatement.executeUpdate();
        closeConnection();
        return i > 0;
    }

    @Override
    public UserDto findUser(String userName, String userType) throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL1 = "select * from user_login where userName=?";
        if (userType != null) SQL1 = "select * from user_login where userName=? && userType=?";

        preparedStatement = connection.prepareStatement(SQL1);
        preparedStatement.setString(1, userName);
        if (userType != null) preparedStatement.setString(2, userType);
        resultSet = preparedStatement.executeQuery();
        UserDto userResponse = null;
        if (resultSet.next()) {
            userResponse = new UserDto();
            userResponse.setUserId(resultSet.getInt(1));
            userResponse.setName(resultSet.getString(2));
            userResponse.setUserType(resultSet.getString(3));
            userResponse.setUserName(resultSet.getString(4));
            userResponse.setPassword(resultSet.getString(5));
            userResponse.setAddress(resultSet.getString(6));
            userResponse.setContact(resultSet.getString(7));
            userResponse.setEmail(resultSet.getString(8));
        }
        closeConnection();
        return userResponse;
    }

    @Override
    public boolean updateUser(UserDto user) throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "update user_login set name=?, userName=? , contact=? , address=? , email=? where user_id=?";

        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getContact());
        preparedStatement.setString(4, user.getAddress());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setInt(6, user.getUserId());
        int i = preparedStatement.executeUpdate();
        closeConnection();
        return i > 0;
    }

    @Override
    public List<UserDto> getAllUsers() throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select * from user_login where userType='CUSTOMER' ";

        preparedStatement = connection.prepareStatement(SQL);
        resultSet = preparedStatement.executeQuery();

        List<UserDto> userDTOS = new ArrayList<>();

        while (resultSet.next()) {
            UserDto userResponse = new UserDto();
            userResponse.setUserId(resultSet.getInt(1));
            userResponse.setName(resultSet.getString(2));
            userResponse.setUserType(resultSet.getString(3));
            userResponse.setUserName(resultSet.getString(4));
            userResponse.setAddress(resultSet.getString(6));
            userResponse.setContact(resultSet.getString(7));
            userResponse.setEmail(resultSet.getString(8));
            userDTOS.add(userResponse);
        }
        closeConnection();
        return userDTOS;
    }

    private void closeConnection() {
        try {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.close(connection);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
