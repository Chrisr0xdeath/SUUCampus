//SUU CAMPUS MINECRAFT PLUGIN PROJECT
//DEVELOPED BY: Christopher Newton
//VERSION 1.42
//LAST UPDATED 4/22/2021
//CREATED FOR CS4800 TAUGHT BY DR. CANTRELL IN THE CS DEPARTMENT AT SOUTHERN UTAH UNIVERSITY
package xyz.Christopher.SuuCampus;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.Christopher.SuuCampus.sql.MySQL;
import xyz.Christopher.SuuCampus.sql.SQLGrabber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Main extends JavaPlugin {
	
	public MySQL SQL;
	public SQLGrabber stuff;
	public Pathfinder path;
	
	public void onEnable() {
		//enables sql, REQUIRED FOR DATABASE TO WORK
		this.SQL = new MySQL();
		//enables sql data gathering
		this.stuff = new SQLGrabber(this);
		//enables pathfinding object
		this.path = new Pathfinder();
		
		try {
			SQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			//e.printStackTrace();
			//Login info is wrong, or, not using a database
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database not connected successfully");
		}
		
		if(SQL.isConnected()) {
			getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Database connected successfully");
			stuff.createTable1();
			stuff.createTable2();
			stuff.createTable3();
			stuff.createTable4();
			stuff.createTable5();
		}
	     
	     
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin has been enabled");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable() {
		//Disconnecting the database on disable
		SQL.disconnect();
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin has been disabled");
	}
	//array holding the path if it was found
	Location[] containsThePath;
	
	public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
		if(sender instanceof Player) {
			Player pl = (Player) sender;
		//Code for walking via coordinates
		if(command.getName().equalsIgnoreCase("walk")) {
			if(args.length > 4) {
				sender.sendMessage("Too many arguments, please format the command as: /walk <x-coordinate> <y-coordinate> <z-coordinate> <moving enabled T/F>");
				return true;
			} else if(args.length < 4) {
				sender.sendMessage("Too few arguments, please format the command as: /walk <x-coordinate> <y-coordinate> <z-coordinate> <moving enabled T/F>");
				return true;
			}
			//enter code for walking in here
			//MUST PATHFIND USING TYPE PLAYER AND NOT COMMAND SENDER, USE if(sender instanceof Player) in order to make sure that the sender is a player!!
			if(args.length == 4) {
				String walking = args[3];
				Boolean walk;
				if(walking.equals("T")) {
					walk = true;
				} else {
					walk = false;
				}
				
				Location start = pl.getLocation();
				double goalX = Double.parseDouble(args[0]);
				double goalY = Double.parseDouble(args[1]);
				double goalZ = Double.parseDouble(args[2]);
				Location goal = new Location(start.getWorld(), goalX, goalY, goalZ);
				
				//Settings for the pathfinder
				MagicPath run = new MagicPath(start, goal, 20000, 1);
				//MagicPath run = new MagicPath(start, goal, 10000, 2);
				//finding the actual path
				containsThePath = run.findThePath(pl);
				
				//If the path was found, put a green carpet for every location in the path
				if(containsThePath.length > 0) {
					//Place the path
					Random rand = new Random();
					int color = rand.nextInt(16);
					switch(color) {
					case 0 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.BLUE_CARPET);
					} break;
					case 1 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.RED_CARPET);
					} break;
					case 2 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.YELLOW_CARPET);
					} break;
					case 3 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.GRAY_CARPET);
					} break;
					case 4 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.PURPLE_CARPET);
					} break;
					case 5 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.GREEN_CARPET);
					} break;
					case 6 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.CYAN_CARPET);
					} break;
					case 7 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.BLACK_CARPET);
					} break;
					case 8 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.WHITE_CARPET);
					} break;
					case 9 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.ORANGE_CARPET);
					} break;
					case 10 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.BROWN_CARPET);
					} break;
					case 11 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.LIGHT_GRAY_CARPET);
					} break;
					case 12 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.LIME_CARPET);
					} break;
					case 13 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.LIGHT_BLUE_CARPET);
					} break; 
					case 14 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.MAGENTA_CARPET);
					} break;
					case 15 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.PINK_CARPET);
					} break;
					}
				} else {
					sender.sendMessage("No path found!");
				}
				//If wanted, this will move the player through the path, by slow teleportation
				if(walk) {
				for(int b = 0; b < containsThePath.length; b++)
				{					
					pl.teleport(containsThePath[b]);
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
				
//				double goalX = Double.parseDouble(args[0]);
//				double goalY = Double.parseDouble(args[1]);
//				//set the players goal Y position to the block beneath the goal location, since the goal location will be where their feet are.
//				goalY--;
//				double goalZ = Double.parseDouble(args[2]);
//				Location start = pl.getLocation();
//				//set the players current location to the block beneath their feet
//				start.setY(start.getY()-1);
//				Location goal = new Location(start.getWorld(), goalX, goalY, goalZ);
//				long startTime = System.nanoTime();
//				pl.sendMessage("Got Here");
//				ArrayList<Location> paths = path.findPath(pl, start, goal);
//				pl.sendMessage("Got Here 2");
//				if(paths != null) {
//				long endTime = System.nanoTime();
//				long totalTime = endTime - startTime;
//				pl.sendMessage("Currently finding a path to your destination...");
//				pl.sendMessage("Path found in " + totalTime / 1000000 + " ms...");
//				//Move the player through the path TBD
//				//pl.sendMessage("Moving you to your destination...");
//				//moveThroughPath(pl, paths);
//				} else {
//					pl.sendMessage("Path was not found :(");
//				}
			}
			return true;
			
			
			//Code for waypoint walking command
		}else if(command.getName().equalsIgnoreCase("waypointwalk")) {
			
			if(args.length > 2) {
				sender.sendMessage("Too many arguments, please format the command as: /waypointwalk <waypoint name> <moving enabled T/F>");
				return true;
			} else if(args.length < 2) {
				sender.sendMessage("Too few arguments, please format the command as: /waypointwalk <waypoint name> <moving enabled T/F>");
				return true;
			}
			//enter code for waypoint walking in here
			//MUST PATHFIND USING TYPE PLAYER AND NOT COMMAND SENDER, USE if(sender instanceof Player) in order to make sure that the sender is a player!!
			String walking = args[1];
			Boolean walk;
			if(walking.equals("T")) {
				walk = true;
			} else {
				walk = false;
			}
			String name = args[0];
			if(stuff.waypointExists(name, pl)) {
				//pl.sendMessage("Got Here");
				Location goal = stuff.getWaypointLocation(name, sender);
				Location start = pl.getLocation();
				
				//Settings for the pathfinder
				MagicPath run = new MagicPath(start, goal, 20000, 1);
				//MagicPath run = new MagicPath(start, goal, 10000, 2);
				//finding the actual path
				containsThePath = run.findThePath(pl);
				
				//If the path was found, put a green carpet for every location in the path
				if(containsThePath.length > 0) {
									
					//Place the path
					Random rand = new Random();
					int color = rand.nextInt(16);
					switch(color) {
					case 0 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.BLUE_CARPET);
					} break;
					case 1 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.RED_CARPET);
					} break;
					case 2 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.YELLOW_CARPET);
					} break;
					case 3 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.GRAY_CARPET);
					} break;
					case 4 :for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.PURPLE_CARPET);
					} break;
					case 5 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.GREEN_CARPET);
					} break;
					case 6 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.CYAN_CARPET);
					} break;
					case 7 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.BLACK_CARPET);
					} break;
					case 8 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.WHITE_CARPET);
					} break;
					case 9 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.ORANGE_CARPET);
					} break;
					case 10 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.BROWN_CARPET);
					} break;
					case 11 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.LIGHT_GRAY_CARPET);
					} break;
					case 12 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.LIME_CARPET);
					} break;
					case 13 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.LIGHT_BLUE_CARPET);
					} break; 
					case 14 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.MAGENTA_CARPET);
					} break;
					case 15 : for(int b = 0; b < containsThePath.length; b++){
						containsThePath[b].getBlock().setType(Material.PINK_CARPET);
					} break;
					}
				} else {
					sender.sendMessage("No path found!");
				}
				//If wanted, this will move the player through the path, by slow teleportation
				if(walk) {
				for(int b = 0; b < containsThePath.length; b++)
				{					
					pl.teleport(containsThePath[b]);
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
				
				
//				long startTime = System.nanoTime();
//				ArrayList<Location> paths = path.findPath(pl, start, goal);
//				if(paths != null) {
//				long endTime = System.nanoTime();
//				long totalTime = endTime - startTime;
//				pl.sendMessage("Currently finding a path to " + name + "...");
//				pl.sendMessage("Path found in " + totalTime / 1000000 + " ms...");
//				//Moving player through the path TBD
//				//pl.sendMessage("Moving you to your destination...");
//				//moveThroughPath(pl, paths);
				
			} else {
				pl.sendMessage("No waypoint with that name was found, use /listwaypoints for a complete list of current waypoints! :))");
			}
			return true;
			
			//Code for touring an array of waypoints
			}else if(command.getName().equalsIgnoreCase("campuswalk")) {
				if(args.length <= 0) {
					sender.sendMessage("Too few arguments, please format the command as: /campuswalk <waypoint 1> <waypoint 2> <waypoint n> <moving enabled T/F>");
					return true;
				}
				String walking = args[3];
				Boolean walk;
				if(walking.equals("T")) {
					walk = true;
				} else {
					walk = false;
				}
				int waypointCount = args.length;
				String name2 = null;
				for(int i = 0; i < waypointCount; i++) {
					String name = args[i];
					if(i > 0) {
						name2 = args[i - 1];
					}
					if(stuff.waypointExists(name, pl)) {
						//pl.sendMessage("Got Here");
						Location start;
						Location goal;
						if(i == 0) {
							goal = stuff.getWaypointLocation(name, sender);
							start = pl.getLocation();
						} else {
							start = stuff.getWaypointLocation(name2, sender);
							goal = stuff.getWaypointLocation(name, sender);
						}
						//Settings for the pathfinder
						MagicPath run = new MagicPath(start, goal, 20000, 1);
						//MagicPath run = new MagicPath(start, goal, 10000, 2);
						//finding the actual path
						containsThePath = run.findThePath(pl);
						
						//If the path was found, put a green carpet for every location in the path
						if(containsThePath.length > 0) {
											
							//Place the path
							Random rand = new Random();
							int color = rand.nextInt(16);
							switch(color) {
							case 0 :for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.BLUE_CARPET);
							} break;
							case 1 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.RED_CARPET);
							} break;
							case 2 :for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.YELLOW_CARPET);
							} break;
							case 3 :for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.GRAY_CARPET);
							} break;
							case 4 :for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.PURPLE_CARPET);
							} break;
							case 5 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.GREEN_CARPET);
							} break;
							case 6 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.CYAN_CARPET);
							} break;
							case 7 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.BLACK_CARPET);
							} break;
							case 8 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.WHITE_CARPET);
							} break;
							case 9 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.ORANGE_CARPET);
							} break;
							case 10 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.BROWN_CARPET);
							} break;
							case 11 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.LIGHT_GRAY_CARPET);
							} break;
							case 12 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.LIME_CARPET);
							} break;
							case 13 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.LIGHT_BLUE_CARPET);
							} break; 
							case 14 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.MAGENTA_CARPET);
							} break;
							case 15 : for(int b = 0; b < containsThePath.length; b++){
								containsThePath[b].getBlock().setType(Material.PINK_CARPET);
							} break;
							}
						} else {
							sender.sendMessage("No path found!");
						}
						//If wanted, this will move the player through the path, by slow teleportation
						if(walk) {
						for(int b = 0; b < containsThePath.length; b++)
						{					
							pl.teleport(containsThePath[b]);
							try {
								Thread.sleep(250);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						}
					} else {
						sender.sendMessage("No waypoint with the name " + args[i] + " found!\n try using /listwaypoints for a complete list of waypoints!");
					}
				}
				return true;
				
				
				//Code for going to a waypoint
			}else if(command.getName().equalsIgnoreCase("goto")) {
				if(sender instanceof Player) {
				if(args.length > 1) {
					sender.sendMessage("Too many arguments, please format the command as: /goto <name>");
					return true;
				} else if(args.length < 1) {
					sender.sendMessage("Too few arguments, please format the command as: /goto <name>");
					return true;
				}	
				//enter code for going to waypoints in here
				String name = args[0];
				if(stuff.waypointExists(name, pl)) {
					Location newLoc = stuff.getWaypointLocation(name, sender);
					pl.teleport(newLoc);
					pl.sendMessage("Teleporting to "+ name +"...");
					pl.playEffect(pl.getLocation(), Effect.ENDER_SIGNAL, 0);
				} else {
					pl.sendMessage("No waypoint with that name was found, use /listwaypoints for a complete list of current waypoints!");
				}
			}
		return true;
			
			//Code for listing waypoints command
		}else if(command.getName().equalsIgnoreCase("listwaypoints")) {
			if(args.length > 0) {
				sender.sendMessage("Too many arguments, please format the command as: /listwaypoints");
				return true;
			} else if(args.length < 0) {
				sender.sendMessage("Too few arguments, please format the command as: /listwaypoints");
				return true;
			}
			//enter code for listing waypoints in here
			stuff.getWaypoints(sender);
			//Waypoint.getWayPoints(sender);
			return true;
			
			
			//Code for setting waypoints command
		}else if(command.getName().equalsIgnoreCase("setwaypoint")) {
			if(args.length == 1 ) {
				//Code based on where the player is standing
				String name = args[0].toString();
				if(sender instanceof Player) {
				Location curLocation = pl.getLocation();
				pl.sendMessage("X: " + curLocation.getX() + ", Y: " + curLocation.getY() + ", Z: " + curLocation.getZ());
				Waypoint w = new Waypoint(name, curLocation);
				Waypoint.waypoints.add(w);
				stuff.addWaypoint(name, curLocation);
				return true;
				}
			} else {
				sender.sendMessage("Too many or too few arguments, please format the command as: /setwaypoint <name>");
			return true;
			}
			
			//Code for deleting waypoints command
		}else if(command.getName().equalsIgnoreCase("delwaypoint")) {
			if(args.length == 1) {
				String name = args[0];
				//enter code for deleting waypoints in here
				pl.sendMessage("Deleting Waypoint " + name);
				stuff.deleteWaypoint(name);
				Waypoint.removeWaypoint(name);
				return true;
			} else {
				sender.sendMessage("Too many or too few arguments, please format the command as: /delwaypoint <name>");
				return true;
			}
			
		}else if(command.getName().equalsIgnoreCase("connected")) {
			if(args.length == 0) {
				SQL.isConnected(pl);
				return true;
			} else {
				sender.sendMessage("Too many arguments, please format the command as: /connected");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("createholo")) {
			if(args.length == 1) {
				ArmorStand holo = (ArmorStand) pl.getWorld().spawnEntity(pl.getLocation(), EntityType.ARMOR_STAND);
				String deptInfo = stuff.getDepartmentInfo(pl, args[0]);
				String edited = deptInfo.replace(':',' ');
				String dept = args[0];
				String capital = dept.toUpperCase();
				String combined = capital + " Department: " + edited;
				holo.setVisible(false);
				holo.setCustomNameVisible(true);
				holo.setCustomName(ChatColor.GREEN + combined);
				holo.setGravity(false);
				return true;
			} else {
			sender.sendMessage("Too few arguments or too many arguments, please format the command as: /createholo <department name>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("adddeptinfo")) {
			if(args.length == 3) {
				String name = args[0];
				String information = args[1];
				String video = args[2];
				stuff.addDepartmentInfo(name, information, video);
				sender.sendMessage("Department information added!");
				return true;
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /adddeptinfo <name> <information> <video link>, with each word in the information field separated by a colon (:). \n For example: /adddeptinfo CS hi:this:is:the:CS:dept Http://www.google.com");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("listdepartments")) {
			if(args.length == 0) {
				stuff.getDepartments(sender);
				return true;
			} else {
				sender.sendMessage("Too many arguments, please format the command as: /listdepartments");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("deptinfo")) {
			if(args.length == 1) {
				String str = stuff.getDepartmentInfo(pl, args[0]);
				String[] strArr = str.split(":");
				for(int i = 0; i < strArr.length; i++) {
					sender.sendMessage(strArr[i]);
				}
				return true;
			} else {
				sender.sendMessage("Too many or too few arguments, please format the command as: /deptinfo <department name>, use /listdepartments to get a list of departments");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("deptvideo")) {
			if(args.length == 1) {
				String arg = args[0];
				String video = stuff.getDepartmentVideo(pl, arg);
				pl.sendMessage(video);
				return true;
			} else {
				sender.sendMessage("Too many or too few arguments, please format the command as: /deptvideo <department name>, use /listdepartments to get a list of departments");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("addclassroominfo")) {
			if(args.length == 3) {
				String number = args[0];
				String building = args[1];
				String zoom = args[2];
				stuff.addClassroom(number, building, zoom);
				sender.sendMessage("Classroom information added!");
				return true;
			} else {
			sender.sendMessage("Too few arguments or too many arguments, please format the command as: /addclassroominfo <Room Number> <Building Name> <Zoom Link>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("editclasszoom")) {
			if(args.length == 3) {
				String number = args[0];
				String building = args[1];
				String newZoom = args[2];
				stuff.editClassroomZoom(sender, number, building, newZoom);
				sender.sendMessage("Classroom Zoom link Updated!");
				return true;
			} else {
			sender.sendMessage("Too few arguments or too many arguments, please format the command as: /editclasszoom <Room Number> <Building Name> <New Zoom Link>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("getzoom")) {
			if(args.length == 2) {
				String number = args[0];
				String building = args[1];
				stuff.getClassroomZoom(sender, number, building);
				return true;
			} else {
			sender.sendMessage("Too few arguments or too many arguments, please format the command as: /getZoom <Building Name> <Room Number>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("getclassrooms")) {
			if(args.length == 0) {
				stuff.getClassrooms(sender);
				return true;
			} else {
			sender.sendMessage("Too many arguments, please format the command as: /getClassrooms");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("addprofinfo")) {
			if(args.length == 5) {
				String firstName = args[0];
				String lastName = args[1];
				String email = args[2];
				String department = args[3];
				String room = args[4].toString();
				stuff.addProfessorInfo(firstName, lastName, email, department, room);
				sender.sendMessage("Professor information added!");
				return true;
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /addprofinfo <first name> <last name> <email> <department> <room number>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("getprofemail")) {
			if(args.length == 2) {
				String firstName = args[0];
				String lastName = args[1];
				String email = stuff.getProfessorEmail(sender, firstName, lastName);
				if(email != null) {
				sender.sendMessage(firstName + " " + lastName + "'s Email is: " + email);
				return true;
				} else {
					sender.sendMessage("No Professor with that name found! try using /listprofessors to find your professor");
				}
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /getprofemail <first name> <last name>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("getprofroom")) {
			if(args.length == 2) {
				String firstName = args[0];
				String lastName = args[1];
				String room = stuff.getProfessorRoom(firstName, lastName);
				if(room != null) {
				sender.sendMessage(firstName + " " + lastName + "'s Room is: " + room);
				return true;
				} else {
					sender.sendMessage("No Professor with that name found! try using /listprofessors to find your professor");
				}
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /getprofroom, <first name> <last name>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("getprofdept")) {
			if(args.length == 2) {
				String firstName = args[0];
				String lastName = args[1];
				String dept = stuff.getProfessorDepartment(firstName, lastName);
				if(dept != null) {
				sender.sendMessage(firstName + " " + lastName + "'s Department is: " + dept);
				return true;
				} else {
					sender.sendMessage("No Professor with that name found! try using /listprofessors to find your professor");
				}
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /getprofdept <first name> <last name>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("listprofessors")) {
			if(args.length == 0) {
				stuff.getProfessors(sender);
				return true;
				}else {
				sender.sendMessage("Too many arguments, please format the command as: /listprofessors");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("listdeptprofessors")) {
			if(args.length == 1) {
				String department = args[0];
				stuff.getDeptProfessors(department, sender);
				return true;
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /listdeptprofessors <department>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("email")) {
			if(args.length == 1) {
				String email = args[0];
				String format = "https://mail.google.com/mail/?view=cm&fs=1&to=";
                  //https://mail.google.com/mail/?view=cm&fs=1&to=someone@example.com
				String sendLink = format + email;
				sender.sendMessage(sendLink);
				return true;
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /email <email address>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("profemail")) {
			if(args.length == 2) {
				String firstName = args[0];
				String lastName = args[1];
				String email = stuff.getProfessorEmail(sender, firstName, lastName);
				String format = "https://mail.google.com/mail/?view=cm&fs=1&to=";
				if(email != null) {
					String sendLink = format + email;
					sender.sendMessage(sendLink);
				return true;
				}
			} else {
				sender.sendMessage("Too few arguments or too many arguments, please format the command as: /getprofemail <first name> <last name>");
				return true;
			}
		}else if(command.getName().equalsIgnoreCase("suutwitter")) {
			if(args.length == 0) {
				pl.sendMessage("https://twitter.com/SUUtbirds");
				return true;
			} else {
				sender.sendMessage("Too many arguments, please format the command as: /suutwitter");
				return true;
			}
		}
		}
		return true;
	}
	
//	public void moveThroughPath(Player pl, ArrayList<Location> path) {
//		for(int x = 0; x < path.size(); x++) {
//			pl.teleport(path.get(x));
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
