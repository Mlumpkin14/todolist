package com.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.todo.model.User;
import com.todo.utils.JDBCUtils;

public class UserDao {
	
	public int registerEmployee(User employee) throws ClassNotFoundException {
		
		String INSERT_USERS_SQL = "INSERT INTO USERS" + " (first_name, last_name, username; password VALUES " + 
		" (?, ?, ?, ?);";
		
		int result = 0; 
		try (Connection connection = JDBCUtils.getConnection();
			
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, employee.getfirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getUserName());
			preparedStatement.setString(4, employee.getPassword());
			
			System.out.println(preparedStatement);
			
			result = preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
			
		}
				
		return result;
	}

}
