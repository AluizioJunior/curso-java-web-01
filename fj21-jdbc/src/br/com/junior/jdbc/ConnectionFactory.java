package br.com.junior.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//Retorna uma conex�o
	public Connection getConnection() throws ClassNotFoundException{
		
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/fj21", "root","root");
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
