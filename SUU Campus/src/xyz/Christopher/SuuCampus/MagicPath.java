//SUU CAMPUS MINECRAFT PLUGIN PROJECT
//DEVELOPED BY: Christopher Newton
//VERSION 1.42
//LAST UPDATED 4/22/2021
//CREATED FOR CS4800 TAUGHT BY DR. CANTRELL IN THE CS DEPARTMENT AT SOUTHERN UTAH UNIVERSITY
package xyz.Christopher.SuuCampus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class MagicPath
{
	
	private Location startL;
	private Location endL;
	
	private Node begin;
	private Node finish;
	
	private boolean located = false;
	private ArrayList<Node> lookedAt = new ArrayList<Node>();
	private ArrayList<Node> needToBeChecked = new ArrayList<Node>();
	
	private int checkLimit;
	private double fallLimit;
	
	public MagicPath(Location start, Location end, int checkLimit, double fallLimit)
	{
		//setting the private variable locations to the ones given by the user
		this.startL = start;
		this.endL = end;
		
		//making the start and goal locations into nodes, with no cost
		begin = new Node(startL, 0, null);
		finish = new Node(endL, 0, null);
		
		this.checkLimit = checkLimit;
		this.fallLimit = fallLimit;
	}

	public Location[] findThePath(CommandSender sender){
		//check if start and goal locations are valid, if they arent, return nothing
		if(!(walkOn(startL) && walkOn(endL))) {
			return new Location[0];
		}
		//If we get here, the locations are valid.
		//Using Dr. Barkers method of measuring time
		long startTime = System.nanoTime();
		//Add the beginning node to the list to be checked
		needToBeChecked.add(begin);
		//keep looking while the path isnt found, the list that needs to be checked isnt empty, and the amount that has been looked at is below 10,000 
		//(arbitrary number, could be any. Too large will cause the program to time out, too little wont give enough nodes to find a path)
		while(lookedAt.size() < checkLimit && located == false && needToBeChecked.size() > 0){
			Node n = needToBeChecked.get(0);
			for(Node nt : needToBeChecked) {
				if(nt.getCost() < n.getCost()) {
					n = nt;
				}
			}
			//The node we are checking is at the goal, finish pathfinding and break out of the while loop
			if(n.costLeft < 1){
				located = true;
				finish = n;
				break;
			}
			
			n.getValidLocations();
			needToBeChecked.remove(n);
			lookedAt.add(n);
		}
		
		//finish since no path was found
		if(!located)
		{
			long endTime = System.nanoTime();
			long totalTime = (endTime - startTime) / 1000000;
			sender.sendMessage("No path found after " + totalTime + "ms");
			//returning empty array
			return new Location[0];
		}
		
		//finding the length of the path to initialize the array of locations that represents the path
		//we start at 1 because of the start block
		int size = 1;
		Node n = finish;
		//keep adding 1 while there is another node with a main(parent) node
		while(n.main != null){
			n = n.main;
			size++;
		}
		
		Location[] path = new Location[size];
		
		//Now to add each node that is part of the path to the path array
		//we are going from the end point to the node right before the start, sort of like a linked list.
		n = finish;
		for(int i = size - 1; i > 0; i --){
			path[i] = n.getLocation();
			//going to the previous node in the path
			n = n.main;
		}
		//adding the start block to the path, not completely necessary tbh, will just place the path under your feet as well
		path[0] = begin.getLocation();
		
		//finished pathfinding! end time
		long endTime = System.nanoTime();
		long totalTime = (endTime - startTime) / 1000000;
		sender.sendMessage("Path found after " + totalTime + "ms");
		
		return path;
	}
	
	private Node getNode(Location loc){
		Node check = new Node(loc, 0, null);
		for(Node n : lookedAt) {
			//checking to see if the node in a location is already in the looked at nodes, saves time over rechecking already checked nodes
			if(n.num == check.num) {
				return n;
			}
		}
		return check;
	}
	//Node class for locating blocks and getting the path nodes in order
	public class Node{
		private Location location;
		public double num;
		
		//Parent node, almost works like a linked list to track from the bottom node to the beginning node
		public Node main;
		
		//For efficiency in checking nodes, use cost to weigh which nodes are closer to the goal
		public double cost;
		//cost left for the node, set to -1, will always be set using distance later
		private double costLeft = -1;
		
		public Node(Location l, double cost, Node main){
			location = l;
			//giving each Node a unique number to identify it
			num = l.getBlockX() + 30000000d * l.getBlockY() + 30000000d * 30000000d * l.getBlockZ();
			
			this.main = main;
			
			this.cost = cost;
		}

		public Location getLocation(){
			return location;
		}
		
		public double getCost(){
			if(costLeft == -1) {
				costLeft = distanceBetween(location, endL);
			}
			//A* function is f(n) = g(n) + h(n), http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html says that multiplying h(n) by 1.5 can improve speed
			return cost + 1.5 * costLeft;
		}
		//Looking for all valid blocks in the area
		public void getValidLocations(){ 
			//using x for the x axis, east and west
			for(int x = -1; x <= 1; x++)
				//using z for the z axis, north and south
				for(int z = -1; z <= 1; z++)
					//we only want locations that both x and z axis are not 0
					if(!(x == 0 && z == 0) && x * z == 0){
						//making a location with these new coordinates
						Location l = new Location(Bukkit.getWorlds().get(0), location.getBlockX() + x, location.getBlockY(), location.getBlockZ() + z);
						
						//adding 1 cost to a location at the same y coordinate
						if(walkOn(l)) {
							grabNode(l, cost + 1);
						}
						//adding a cost of sqrt(2) to a location with the y coordinate one above
						//block above current, subtract x and z
						if(!stopped(l.clone().add(-x, 2, -z))){
							Location nL = l.clone().add(0, 1, 0);
							if(walkOn(nL)) {
								//1.4142 = sqrt(2) = cost using pythagorean theorem sqrt(1 block + 1 block)
								grabNode(nL, cost + 1.4142);
							}
						}
						
						//adding a cost of sqrt(2) to a location with the y coordinate one below
						//block above new possible location check if its solid
						if(!stopped(l.clone().add(0, 1, 0))){
							//Block below
							Location nLoc = l.clone().add(0, -1, 0);
							if(walkOn(nLoc)) {
								grabNode(nLoc, cost + 1.4142);
							}
							//Player will fall up to the amount allowed in fallLimit variable
							else if(!stopped(nLoc) && !stopped(nLoc.clone().add(0, 1, 0))){
								int fall = 1;
								while(fall <= fallLimit && !stopped(l.clone().add(0, -fall, 0))){
									Location locF = l.clone().add(0, -fall, 0);
									if(walkOn(locF)){
										Node fallNode = addFallNode(l,  cost + 1);
										fallNode.grabNode(locF, cost + fall * 2);
									}
									
									fall ++;
								}
							}
						}
					}
		}
		
		public void grabNode(Location newLoc, double costToPlace){
			Node n = getNode(newLoc);
			//add to the need to be checked list if the node is new
			if(n.main == null && n != begin){
				n.cost = costToPlace;
				//setting main node for this node to the node being referenced
				n.main = this;
				needToBeChecked.add(n);
				return;
			}
			
			//dont add
			if(n.cost > costToPlace){
				n.cost = costToPlace;
				//setting main node for this node to the node being referenced
				n.main = this;
			}
		}
		//adding additional nodes in case of going down or up a block
		public Node addFallNode(Location l, double cost){
			Node n = new Node(l, cost, this);
			return n;
		}
	}
	
	//Checking if the location being checked is solid or not
	public boolean stopped(Location l){
		if(l.getBlock().getType().isSolid()) {
			return true;
		}
		return false;
	}
	
	public boolean walkOn(Location l){
		//checks for blocks below for the player to step on, and for the lack of solid blocks above the block needing to be stepped on
		return !(stopped(l) || stopped(l.clone().add(0, 1, 0)) || !stopped(l.clone().add(0, -1, 0)));
	}
	
	public double distanceBetween(Location a, Location b){
		if(a.getWorld() != b.getWorld())
			return Double.MAX_VALUE;
		
		double X = Math.abs(a.getX() - b.getX());
		double Y = Math.abs(a.getY() - b.getY());
		double Z = Math.abs(a.getZ() - b.getZ());
		
		//Finding literal distance between the two dimensional coordinates X and Z
		double twodimension = Math.sqrt(X * X + Z * Z);
		//Finding literal distance between the two dimensional coordinates from above, and adding in the vertical axis Y
		double threedimension = Math.sqrt(twodimension * twodimension + Y * Y);
		return threedimension;
	}
}