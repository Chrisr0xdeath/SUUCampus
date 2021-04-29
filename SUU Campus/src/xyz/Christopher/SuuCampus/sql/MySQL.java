//SUU CAMPUS MINECRAFT PLUGIN PROJECT
//DEVELOPED BY: Christopher Newton
//VERSION 1.42
//LAST UPDATED 4/22/2021
//CREATED FOR CS4800 TAUGHT BY DR. CANTRELL IN THE CS DEPARTMENT AT SOUTHERN UTAH UNIVERSITY
package xyz.Christopher.SuuCampus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;

public class MySQL {

	private String host = "localhost";
	private String port = "3306";
	private String database = "suucampus";
	private String username = "root";
	private String password = "";
	
	private Connection connection;
	
	public boolean isConnected() {
		//if null, return false, otherwise return true
		return (connection == null ? false: true);
	}
	
	public void isConnected(CommandSender player) {
		if(connection == null) {
			player.sendMessage("Not Connected");
		} else{
			player.sendMessage("Connected");
		}
	}
	
	public void connect() throws ClassNotFoundException, SQLException {
		//only connect if not already connected to another database
		if(!isConnected()) {
			connection = DriverManager.getConnection("jdbc:mysql://" +
				     host + ":" + port + "/" + database + "?useSSL=false",
				     username, password);
		}
	}
	
	public void disconnect() {
		if(isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
}
