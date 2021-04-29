//SUU CAMPUS MINECRAFT PLUGIN PROJECT
//DEVELOPED BY: Christopher Newton
//VERSION 1.42
//LAST UPDATED 4/22/2021
//CREATED FOR CS4800 TAUGHT BY DR. CANTRELL IN THE CS DEPARTMENT AT SOUTHERN UTAH UNIVERSITY
package xyz.Christopher.SuuCampus;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Waypoint {
	private double xcoord;
	private double ycoord;
	private double zcoord;
	private Location loc;
	private String name;
	public static ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
	
	//Might implement later, dont come at me
//	public Waypoint(String name, double xcoord, double ycoord, double zcoord) {
//		this.name = name;
//		this.xcoord = xcoord;
//		this.ycoord = ycoord;
//		this.zcoord = zcoord;
//	}
	
	public Waypoint(String name, Location loc) {
		this.name = name;
		this.loc = loc;
	}
	
	public Waypoint getLocation(String name) {
		for(int x = 0; x < waypoints.size(); x++) {
			if(waypoints.get(x).getName() == name) {
				return waypoints.get(x);
			}
		}
		//TODO: Write to player that they have chosen an invalid name for the waypoint location
			return null;
	}
	
	public static void getWayPoints(CommandSender sender) {
		for(int x = 0; x < waypoints.size(); x++) {
			sender.sendMessage(waypoints.get(x).getName());
			sender.sendMessage(String.valueOf(waypoints.get(x).getLoc().getX()));
			sender.sendMessage(String.valueOf(waypoints.get(x).getLoc().getY()));
			sender.sendMessage(String.valueOf(waypoints.get(x).getLoc().getZ()));
		}
	}
	
//	public static Waypoint getWayPoint(String s, Player pl) {
//		for(int x = 0; x < waypoints.size(); x++) {
//			if(s == waypoints.get(x).getName()) {
//				return waypoints.get(x);
//			}
//		}
//		pl.sendMessage("Waypoint not found");
//		return null;
//	}
	
	public static void sendtoWaypoint(Player pl, Waypoint w) {
		if(pl.teleport(getReqWaypoint(pl,w))) {
			pl.sendMessage("Ok");
		} else {
			pl.sendMessage("Not ok");
		}
	}
	
	public static Location getReqWaypoint(Player pl, Waypoint w) {
		Location l = pl.getLocation();
		return new Location(
				l.getWorld(),
				w.getXVal(),
				w.getYVal(),
				w.getZVal()
				);
	}
	
	public static void setWaypointToArray(Waypoint w) {
		waypoints.add(w);
	}
	
	public static void removeWaypoint(String name) {
		for(Waypoint w : Waypoint.waypoints) {
			if(w.getName().equals(name)) {
				waypoints.remove(w);
			}
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getXVal() {
		return this.xcoord;
	}
	
	public static double [] getLocFromArray(String name){
		for(int x = 0; x < waypoints.size(); x++) {
			if(waypoints.get(x).getName() == name) {
				return new double[] {waypoints.get(x).getXVal(), waypoints.get(x).getYVal(), waypoints.get(x).getZVal()};
			}
		}
		System.out.println("No Waypoint with that name was found");
		return null;
	}
	
	public Location getLoc() {
		return this.loc;
	}
	
	public double getYVal() {
		return this.ycoord;
	}
	
	public double getZVal() {
		return this.zcoord;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setXVal(double xcoord) {
		this.xcoord = xcoord;
	}
	
	public void setYVal(double ycoord) {
		this.ycoord = ycoord;
	}
	
	public void setZVal(double zcoord) {
		this.zcoord = zcoord;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
