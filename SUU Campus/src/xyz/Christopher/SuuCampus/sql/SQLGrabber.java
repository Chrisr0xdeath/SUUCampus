//SUU CAMPUS MINECRAFT PLUGIN PROJECT
//DEVELOPED BY: Christopher Newton
//VERSION 1.42
//LAST UPDATED 4/22/2021
//CREATED FOR CS4800 TAUGHT BY DR. CANTRELL IN THE CS DEPARTMENT AT SOUTHERN UTAH UNIVERSITY
package xyz.Christopher.SuuCampus.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.Christopher.SuuCampus.Main;

public class SQLGrabber {
	
	private Main plugin;
	
	public SQLGrabber(Main plugin) {
		this.plugin = plugin;
	}
	
	public void createTable1() {
		PreparedStatement statement;
		try {
			//Makes the waypoints table if it doesn't already exist with the name being the primary key, using a name and a Location which contains the actual location data
			statement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS waypoints (NAME VARCHAR(100),LOCATION VARCHAR(100), PRIMARY KEY(NAME))");
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable2() {
		PreparedStatement statement;
		try {
			//Makes the departmentinfo table if it doesn't already exist with the name being the primary key, using a name and a String that holds the department info data
			statement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS departmentinfo (NAME VARCHAR(100),INFO VARCHAR(1000), VIDEO VARCHAR(1000), PRIMARY KEY(NAME))");
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable3() {
		PreparedStatement statement;
		try {
			//Makes the players table if it doesn't already exist with the name being the primary key, using a name and uuid
			statement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS players (NAME VARCHAR(100),UUID VARCHAR(100), PRIMARY KEY(NAME))");
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable4() {
		PreparedStatement statement;
		try {
			//Makes the players table if it doesn't already exist with the name being the primary key, using a name and uuid
			statement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS professors (FIRSTNAME VARCHAR(100),LASTNAME VARCHAR(100),EMAIL VARCHAR(100),DEPARTMENT VARCHAR(100),ROOM VARCHAR (100), PRIMARY KEY(LASTNAME))");
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable5() {
		PreparedStatement statement;
		try {
			//Makes the players table if it doesn't already exist with the name being the primary key, using a name and uuid
			statement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS classroom (ROOMNUMBER VARCHAR(100), BUILDING VARCHAR(100), ZOOM VARCHAR(100), PRIMARY KEY(ROOMNUMBER))");
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addWaypoint(String name, Location loc) {
		String value = loc.getWorld().getName() + ":" + String.valueOf(loc.getX()) + ":" + String.valueOf(loc.getY()) + ":" + String.valueOf(loc.getZ());
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("INSERT INTO waypoints VALUES(?,?)");
			statement.setString(1, name);
			statement.setString(2, value);
			statement.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteWaypoint(String name) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("DELETE FROM waypoints WHERE NAME=?");
			statement.setString(1, name);
			statement.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		}
	
	public void getWaypoints(CommandSender sender) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM waypoints");
			ResultSet results = statement.executeQuery();
			//GOES THROUGH THE RESULTS LIST AND DISPLAYS EVERY WAYPOINT NAME TO THE SENDER OF THE COMMAND
			while(results.next()) {
				String temp = results.getString("NAME");
				sender.sendMessage(temp);
				//results.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean waypointExists(String name, CommandSender sender){
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM waypoints");
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				String temp = results.getString("NAME");
				if(temp.equals(name)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Location getWaypointLocation(String name, CommandSender sender) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT LOCATION FROM waypoints WHERE NAME=?");
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			String value;
			if(result.next()) {
				value = result.getString("LOCATION");
				String[] values = value.split(":");
				World world = Bukkit.getWorld(values[0]);
				Double x = Double.parseDouble(values[1]);
				Double y = Double.parseDouble(values[2]);
				Double z = Double.parseDouble(values[3]);
				
				Location loc = new Location(world, x, y, z);
				
				return loc;
			}
		}catch (SQLException e) {
			sender.sendMessage("No waypoint with that name was found, use /listwaypoints for a complete list of current waypoints! :)))");
		}
		return null;
	}
	
	public boolean addDepartmentInfo(String department, String info, String video) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("INSERT INTO departmentinfo VALUES(?,?,?)");
			statement.setString(1, department);
			statement.setString(2, info);
			statement.setString(3, video);
			statement.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addClassroom(String number, String building, String zoomlink) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("INSERT INTO classroom VALUES(?,?,?)");
			statement.setString(1, number);
			statement.setString(2, building);
			statement.setString(3, zoomlink);
			statement.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editClassroomZoom(CommandSender sender, String number, String building, String zoomlink) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("UPDATE classroom SET ZOOM=? WHERE ROOMNUMBER=? AND BUILDING=?");
			statement.setString(1, zoomlink);
			statement.setString(2, number);
			statement.setString(3, building);
			statement.executeUpdate();
			return true;
		}catch (SQLException e) {
			sender.sendMessage("No Room found with that information! Use /getClassrooms to find classrooms");
		}
		return false;
	}
	
	public void getClassrooms(CommandSender sender) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM classroom");
			ResultSet results = statement.executeQuery();
			//GOES THROUGH THE RESULTS LIST AND DISPLAYS EVERY WAYPOINT NAME TO THE SENDER OF THE COMMAND
			while(results.next()) {
				String temp = results.getString("BUILDING");
				String temp2 = results.getString("ROOMNUMBER");
				sender.sendMessage(temp + ", Room Number: " + temp2);
				//results.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getClassroomZoom(CommandSender sender, String Building, String number) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT ZOOM FROM classroom WHERE BUILDING=? AND ROOMNUMBER=?");
			statement.setString(1, Building);
			statement.setString(2, number);
			ResultSet results = statement.executeQuery();
			//GOES THROUGH THE RESULTS LIST AND DISPLAYS EVERY WAYPOINT NAME TO THE SENDER OF THE COMMAND
			while(results.next()) {
				String temp = results.getString("ZOOM");
				sender.sendMessage("Zoom Link: " + temp);
				//results.next();
			}
		} catch (SQLException e) {
			sender.sendMessage("No Room found with that information! Use /getClassrooms to find classrooms");
		}
	}
	
	
	public String getDepartmentInfo(CommandSender sender, String name) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT INFO FROM departmentinfo WHERE NAME=?");
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			String information;
			if(result.next()) {
				information = result.getString("INFO");
				return information;
			}
		}catch (SQLException e) {
			sender.sendMessage("No department with that name found! Try using /listdepartments to get a list of current departments");
		}
		return null;
	}
	
	public String getDepartmentVideo(CommandSender sender, String name) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT VIDEO FROM departmentinfo WHERE NAME=?");
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			String video;
			if(result.next()) {
				video = result.getString("VIDEO");
				return video;
			}
		}catch (SQLException e) {
			sender.sendMessage("No department with that name found! Try using /listdepartments to get a list of current departments");
		}
		return null;
	}
	
	public boolean addProfessorInfo(String firstName, String lastName, String email, String department, String room) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("INSERT INTO professors VALUES(?,?,?,?,?)");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, email);
			statement.setString(4, department);
			statement.setString(5, room);
			statement.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getProfessorEmail(CommandSender sender, String firstName, String lastName) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM professors WHERE FIRSTNAME=? AND LASTNAME=?");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet result = statement.executeQuery();
			String information;
			if(result.next()) {
				information = result.getString("EMAIL");
				return information;
			}
		}catch (SQLException e) {
			sender.sendMessage("No professor with that name found! Use /getProfessors to get a list of current professors");
		}
		return null;
	}
	
	public String getProfessorRoom(String firstName, String lastName) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM professors WHERE FIRSTNAME=? AND LASTNAME=?");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet result = statement.executeQuery();
			String information;
			if(result.next()) {
				information = result.getString("ROOM");
				return information;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProfessorDepartment(String firstName, String lastName) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM professors WHERE FIRSTNAME=? AND LASTNAME=?");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet result = statement.executeQuery();
			String information;
			if(result.next()) {
				information = result.getString("DEPARTMENT");
				return information;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void getProfessors(CommandSender sender) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM professors");
			ResultSet results = statement.executeQuery();
			//GOES THROUGH THE RESULTS LIST AND DISPLAYS EVERY WAYPOINT NAME TO THE SENDER OF THE COMMAND
			while(results.next()) {
				String temp = results.getString("FIRSTNAME");
				String temp2 = results.getString("LASTNAME");
				sender.sendMessage(temp + " " + temp2);
				//results.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getDeptProfessors(String department, CommandSender sender) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM professors WHERE DEPARTMENT=?");
			statement.setString(1, department);
			ResultSet results = statement.executeQuery();
			//GOES THROUGH THE RESULTS LIST AND DISPLAYS EVERY WAYPOINT NAME TO THE SENDER OF THE COMMAND
			while(results.next()) {
				String temp = results.getString("FIRSTNAME");
				String temp2 = results.getString("LASTNAME");
				sender.sendMessage(temp + " " + temp2);
				//results.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getDepartments(CommandSender sender) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM departmentinfo");
			ResultSet results = statement.executeQuery();
			//GOES THROUGH THE RESULTS LIST AND DISPLAYS EVERY WAYPOINT NAME TO THE SENDER OF THE COMMAND
			while(results.next()) {
				String temp = results.getString("NAME");
				sender.sendMessage(temp);
				//results.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}