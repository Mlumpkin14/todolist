package com.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.todo.model.LoginModel;
import com.todo.utils.JDBCUtils;

public class LoginDao {
	public boolean validate(LoginModel loginModel) throws ClassNotFoundException {
		boolean status = false;
		
		Class.forName("com.mysql.jdbc.Driver");
		
		try (Connection connection = JDBCUtils.getConnection();
				
			PreparedStatement preparedStatement = connection.prepareStatement("select * users where username = ? and password = ? ")) {
			preparedStatement.setString(1, loginModel.getUsername());
			preparedStatement.setString(2, loginModel.getPassword());
			
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			status = rs.next();
			
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return status;
	}
}
