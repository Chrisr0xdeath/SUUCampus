//SUU CAMPUS MINECRAFT PLUGIN PROJECT
//DEVELOPED BY: Christopher Newton
//VERSION 1.42
//LAST UPDATED 4/22/2021
//CREATED FOR CS4800 TAUGHT BY DR. CANTRELL IN THE CS DEPARTMENT AT SOUTHERN UTAH UNIVERSITY
package xyz.Christopher.SuuCampus;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pathfinder {
	
	public ArrayList<Location> findPath(Player pl, Location start, Location goal) {
		ArrayList<Location> path = new ArrayList<Location>();
		path.add(start);
		
		
		//look for path
		if(start.equals(goal)) {
			pl.sendMessage("You are already at your destination!");
			return null;
			//Start pathfinding since the goal block is not the start block
		} else {
			//Keep looking for a path while the path does not contain the goal
			while(!containsSameLocation(path, goal)) {
				//Gets the last location in the path arraylist
				Location newest = path.get(path.size() - 1);
				//Get the neighbors of the last location
				ArrayList<Location> newNeighbors = getNeighbors(newest);
				
				//If the amount of valid neighbors is 0, then stop pathfinding.
//				if((newNeighbors.size()) == 0) {
//					pl.sendMessage("No valid neighbors found, no path found!");
//					return null;
//				} 
				//else {
					Location l1 = newNeighbors.get(0);
					Location l2 = newNeighbors.get(1);
					Location l3 = newNeighbors.get(2);
					Location l4 = newNeighbors.get(3);
					//have to check all neighbors against the goal to see if any are closer.
					//If so, add the closest neighbor to the path, otherwise, if the current block is the closest
					//Return null since there are no closer neighbors, and the path will stray away from the goal.
//					for(int i = 0; i < newNeighbors.size() - 1; i++) {
//						if(i == 0) {
//							l1 = newNeighbors.get(0);
//						} else if(i == 1) {
//							l2 = newNeighbors.get(1);
//						} else if(i == 2) {
//							l3 = newNeighbors.get(2);
//						} else if(i == 3) {
//							l4 = newNeighbors.get(3);
//						} else {
//							pl.sendMessage("Good job, you somehow have more than 4 directions in your reality");
//						}
//					}
				//}
			//returns the closest neighbor out of the new neighbors arraylist. add this location to the path if not null
			Location closestNeighbor = closestNeighbor(newest, newNeighbors, goal);	
			if(closestNeighbor != null) {
				path.add(closestNeighbor);
			} else {
				pl.sendMessage("The only valid neighbors were farther than the furthest path block... ending pathfinding...");
				break;
			}
		}
			//if the path contains the goal, return the path
			if(containsSameLocation(path, goal)) {
				pl.sendMessage("Path found!!!");
				return path;
			}
			}
		pl.sendMessage("Path not found!");
		return null;
		}
	
	public ArrayList<Location> getNeighbors(Location main){
		ArrayList<Location> neighbors = new ArrayList<Location>();
		
		Location N = main;
		Location S = main;
		Location E = main;
		Location W = main;
		
		N.setZ(N.getZ() - 1);
		N.setY(N.getY() + 1);
		S.setZ(S.getZ() + 1);
		S.setY(S.getY() + 1);
		E.setX(E.getX() + 1);
		E.setY(E.getY() + 1);
		W.setX(W.getX() - 1);
		W.setY(W.getY() + 1);
		
		Block nB = N.getBlock();
		Block sB = S.getBlock();
		Block eB = E.getBlock();
		Block wB = W.getBlock();
		
		nB.setType(Material.ORANGE_CARPET);
		sB.setType(Material.ORANGE_CARPET);
		eB.setType(Material.ORANGE_CARPET);
		wB.setType(Material.ORANGE_CARPET);
		
		neighbors.add(N);
		neighbors.add(S);
		neighbors.add(E);
		neighbors.add(W);
		
//		///////////////////////////////////
//		//  _   _            _   _       //
//		//  | \ | |          | | | |     //
//		//  |  \| | ___  _ __| |_| |__   //
//		//  | . ` |/ _ \| '__| __| '_ \  //
//		//  | |\  | (_) | |  | |_| | | | //
//		//  |_| \_|\___/|_|   \__|_| |_| //
//		///////////////////////////////////
//		                              
//		
//		//make a neighboring location 1 block north of the current location (Negative Z direction)
//		Location neighborN = main;
//		neighborN.setZ(main.getZ() - 1);
//		//get the block of the north neighbor location
//		Block blockNorth = neighborN.getBlock();
//		//get the block above the north neighbor
//		Block blockNorth1Above = neighborN.add(0, 1, 0).getBlock();
//		//get the block 2 blocks above the north neighbor
//		Block blockNorth2Above = neighborN.add(0, 2, 0).getBlock();
//		//get the block below the north neighbor
//		Block blockNorth1Below = neighborN.subtract(0, 1, 0).getBlock();
//		//get the block 2 blocks below the north neighbor
//		Block blockNorth2Below = neighborN.subtract(0, 2, 0).getBlock();
//		
//		//SCENARIO 1: The north neighbor is NOT air, and the block above the north neighbor is air, VALID
//		//add the north neighbor location to the valid neighbors list to be returned
//		//Set the block above the north neighbor to Material.ORANGE_CARPET
//		if(!blockNorth.getType().equals(Material.AIR) && blockNorth1Above.getType().equals(Material.AIR)) {
//			blockNorth1Above.setType(Material.ORANGE_CARPET);
//			neighbors.add(neighborN);
//		}
//		
//		//SCENARIO 2: The north neighbor is NOT air, and the block one above isn't air, but the block 2 blocks above IS air, VALID
//		//add the location of the block one above the north neighbor to the list of valid neighbors to be returned
//		//Set the block two higher than the north neighbor to Material.ORANGE_CARPET
//		if(!blockNorth.getType().equals(Material.AIR) && !blockNorth1Above.getType().equals(Material.AIR) && blockNorth2Above.getType().equals(Material.AIR)) {
//			blockNorth2Above.setType(Material.ORANGE_CARPET);
//			Location northScenario2 = blockNorth1Above.getLocation();
//			neighbors.add(northScenario2);
//		}
//		
//		//SCENARIO 3: The north neighbor is NOT air, and both the block above and two blocks above are NOT air, NOT VALID
//		//do NOT add anything to the valid list, since you cannot jump two blocks
//		//Set the block three higher than the north neighbor to Material.RED_CARPET
//		if(!blockNorth.getType().equals(Material.AIR) && !blockNorth1Above.getType().equals(Material.AIR) && !blockNorth2Above.getType().equals(Material.AIR)) {
//			Block blockNorth3Above = neighborN.add(0, 3, 0).getBlock();
//			blockNorth3Above.setType(Material.RED_CARPET);
//		}
//		
//		//SCENARIO 4: The north neighbor IS air, and the block one below is NOT air, VALID
//		//add the location of the block one below the north neighbor to the list of valid neighbors to be returned
//		//Set the block of the north neighbor to Material.ORANGE_CARPET
//		if(blockNorth.getType().equals(Material.AIR) && !blockNorth1Below.getType().equals(Material.AIR)) {
//			blockNorth.setType(Material.ORANGE_CARPET);
//			Location northScenario4 = blockNorth1Below.getLocation();
//			neighbors.add(northScenario4);
//		}
//		
//		//SCENARIO 5: The north neighbor IS air, and both the block below and two blocks below are air, NOT VALID
//		//do NOT add anything to the valid list, you do not want to drop to a location you cannot get back to!
//		//Set the block one below the north neighbor to Material.RED_Carpet
//		if(blockNorth.getType().equals(Material.AIR) && blockNorth1Below.getType().equals(Material.AIR) && blockNorth2Below.getType().equals(Material.AIR)) {
//			blockNorth1Below.setType(Material.RED_CARPET);
//		}
//		
//		
//		
//		/////////////////////////////////////
//		//   _____             _   _       //
//		//   / ____|           | | | |     //
//		//  | (___   ___  _   _| |_| |__   //
//		//   \___ \ / _ \| | | | __| '_ \  //
//		//   ____) | (_) | |_| | |_| | | | //
//		//  |_____/ \___/ \__,_|\__|_| |_| //
//		/////////////////////////////////////
//		
//		//make a neighboring location 1 block south of the current location (Positive Z Direction)
//		Location neighborS = main;
//		neighborS.setZ(main.getZ() + 1);
//		//get the block of the south neighbor location
//		Block blockSouth = neighborS.getBlock();
//		//get the block above the south neighbor
//		Block blockSouth1Above = neighborS.add(0, 1, 0).getBlock();
//		//get the block 2 blocks above the south neighbor
//		Block blockSouth2Above = neighborS.add(0, 2, 0).getBlock();
//		//get the block below the south neighbor
//		Block blockSouth1Below = neighborS.subtract(0, 1, 0).getBlock();
//		//get the block 2 blocks below the south neighbor
//		Block blockSouth2Below = neighborS.subtract(0, 2, 0).getBlock();
//		
//		//SCENARIO 1: The south neighbor is NOT air, and the block above the south neighbor is air, VALID
//		//add the south neighbor location to the valid neighbors list to be returned
//		//Set the block above the south neighbor to Material.ORANGE_CARPET
//		if(!blockSouth.getType().equals(Material.AIR) && blockSouth1Above.getType().equals(Material.AIR)) {
//			blockSouth1Above.setType(Material.ORANGE_CARPET);
//			neighbors.add(neighborN);
//		}
//		
//		//SCENARIO 2: The south neighbor is NOT air, and the block one above isn't air, but the block 2 blocks above IS air, VALID
//		//add the location of the block one above the south neighbor to the list of valid neighbors to be returned
//		//Set the block two higher than the south neighbor to Material.ORANGE_CARPET
//		if(!blockSouth.getType().equals(Material.AIR) && !blockSouth1Above.getType().equals(Material.AIR) && blockSouth2Above.getType().equals(Material.AIR)) {
//			blockSouth2Above.setType(Material.ORANGE_CARPET);
//			Location SouthScenario2 = blockSouth1Above.getLocation();
//			neighbors.add(SouthScenario2);
//		}
//		
//		//SCENARIO 3: The south neighbor is NOT air, and both the block above and two blocks above are NOT air, NOT VALID
//		//do NOT add anything to the valid list, since you cannot jump two blocks
//		//Set the block three higher than the south neighbor to Material.RED_CARPET
//		if(!blockSouth.getType().equals(Material.AIR) && !blockSouth1Above.getType().equals(Material.AIR) && !blockSouth2Above.getType().equals(Material.AIR)) {
//			Block blockSouth3Above = neighborN.add(0, 3, 0).getBlock();
//			blockSouth3Above.setType(Material.RED_CARPET);
//		}
//		
//		//SCENARIO 4: The south neighbor IS air, and the block one below is NOT air, VALID
//		//add the location of the block one below the south neighbor to the list of valid neighbors to be returned
//		//Set the block of the south neighbor to Material.ORANGE_CARPET
//		if(blockSouth.getType().equals(Material.AIR) && !blockSouth1Below.getType().equals(Material.AIR)) {
//			blockSouth.setType(Material.ORANGE_CARPET);
//			Location SouthScenario4 = blockSouth1Below.getLocation();
//			neighbors.add(SouthScenario4);
//		}
//		
//		//SCENARIO 5: The south neighbor IS air, and both the block below and two blocks below are air, NOT VALID
//		//do NOT add anything to the valid list, you do not want to drop to a location you cannot get back to!
//		//Set the block one below the south neighbor to Material.RED_Carpet
//		if(blockSouth.getType().equals(Material.AIR) && blockSouth1Below.getType().equals(Material.AIR) && blockSouth2Below.getType().equals(Material.AIR)) {
//			blockSouth1Below.setType(Material.RED_CARPET);
//		}
//		
//		
//		
//		////////////////////////////
//		//   ______          _    //
//		//  |  ____|        | |   //
//		//  | |__   __ _ ___| |_  //
//		//  |  __| / _` / __| __| //
//		//  | |___| (_| \__ \ |_  //
//		//  |______\__,_|___/\__| //
//		////////////////////////////                       
//		
//		//make a neighboring location 1 block east of the current location (Positive X Direction)
//		Location neighborE = main;
//		neighborE.setX(main.getX() + 1);
//		//get the block of the east neighbor location
//		Block blockEast = neighborE.getBlock();
//		//get the block above the east neighbor
//		Block blockEast1Above = neighborE.add(0, 1, 0).getBlock();
//		//get the block 2 blocks above the east neighbor
//		Block blockEast2Above = neighborE.add(0, 2, 0).getBlock();
//		//get the block below the east neighbor
//		Block blockEast1Below = neighborE.subtract(0, 1, 0).getBlock();
//		//get the block 2 blocks below the east neighbor
//		Block blockEast2Below = neighborE.subtract(0, 2, 0).getBlock();
//		
//		//SCENARIO 1: The east neighbor is NOT air, and the block above the east neighbor is air, VALID
//		//add the east neighbor location to the valid neighbors list to be returned
//		//Set the block above the east neighbor to Material.ORANGE_CARPET
//		if(!blockEast.getType().equals(Material.AIR) && blockEast1Above.getType().equals(Material.AIR)) {
//			blockEast1Above.setType(Material.ORANGE_CARPET);
//			neighbors.add(neighborN);
//		}
//		
//		//SCENARIO 2: The east neighbor is NOT air, and the block one above isn't air, but the block 2 blocks above IS air, VALID
//		//add the location of the block one above the east neighbor to the list of valid neighbors to be returned
//		//Set the block two higher than the east neighbor to Material.ORANGE_CARPET
//		if(!blockEast.getType().equals(Material.AIR) && !blockEast1Above.getType().equals(Material.AIR) && blockEast2Above.getType().equals(Material.AIR)) {
//			blockEast2Above.setType(Material.ORANGE_CARPET);
//			Location EastScenario2 = blockEast1Above.getLocation();
//			neighbors.add(EastScenario2);
//		}
//		
//		//SCENARIO 3: The east neighbor is NOT air, and both the block above and two blocks above are NOT air, NOT VALID
//		//do NOT add anything to the valid list, since you cannot jump two blocks
//		//Set the block three higher than the east neighbor to Material.RED_CARPET
//		if(!blockEast.getType().equals(Material.AIR) && !blockEast1Above.getType().equals(Material.AIR) && !blockEast2Above.getType().equals(Material.AIR)) {
//			Block blockEast3Above = neighborN.add(0, 3, 0).getBlock();
//			blockEast3Above.setType(Material.RED_CARPET);
//		}
//		
//		//SCENARIO 4: The east neighbor IS air, and the block one below is NOT air, VALID
//		//add the location of the block one below the east neighbor to the list of valid neighbors to be returned
//		//Set the block of the east neighbor to Material.ORANGE_CARPET
//		if(blockEast.getType().equals(Material.AIR) && !blockEast1Below.getType().equals(Material.AIR)) {
//			blockEast.setType(Material.ORANGE_CARPET);
//			Location EastScenario4 = blockEast1Below.getLocation();
//			neighbors.add(EastScenario4);
//		}
//		
//		//SCENARIO 5: The east neighbor IS air, and both the block below and two blocks below are air, NOT VALID
//		//do NOT add anything to the valid list, you do not want to drop to a location you cannot get back to!
//		//Set the block one below the east neighbor to Material.RED_Carpet
//		if(blockEast.getType().equals(Material.AIR) && blockEast1Below.getType().equals(Material.AIR) && blockEast2Below.getType().equals(Material.AIR)) {
//			blockEast1Below.setType(Material.RED_CARPET);
//		}
//		
//		
//		
//		///////////////////////////////
//		// __          __       _    //
//		// \ \        / /      | |   //
//		//  \ \  /\  / /__  ___| |_  //
//		//   \ \/  \/ / _ \/ __| __| //
//		//    \  /\  /  __/\__ \ |_  //
//		//     \/  \/ \___||___/\__| //
//		///////////////////////////////
//		
//		//make a neighboring location 1 block west of the current location (Negative X Direction)
//		Location neighborW = main;
//		neighborW.setX(main.getX() - 1);
//		//get the block of the west neighbor location
//		Block blockWest = neighborW.getBlock();
//		//get the block above the west neighbor
//		Block blockWest1Above = neighborW.add(0, 1, 0).getBlock();
//		//get the block 2 blocks above the west neighbor
//		Block blockWest2Above = neighborW.add(0, 2, 0).getBlock();
//		//get the block below the west neighbor
//		Block blockWest1Below = neighborW.subtract(0, 1, 0).getBlock();
//		//get the block 2 blocks below the west neighbor
//		Block blockWest2Below = neighborW.subtract(0, 2, 0).getBlock();
//		
//		//SCENARIO 1: The West neighbor is NOT air, and the block above the West neighbor is air, VALID
//		//add the West neighbor location to the valid neighbors list to be returned
//		//Set the block above the West neighbor to Material.ORANGE_CARPET
//		if(!blockWest.getType().equals(Material.AIR) && blockWest1Above.getType().equals(Material.AIR)) {
//			blockWest1Above.setType(Material.ORANGE_CARPET);
//			neighbors.add(neighborN);
//		}
//		
//		//SCENARIO 2: The West neighbor is NOT air, and the block one above isn't air, but the block 2 blocks above IS air, VALID
//		//add the location of the block one above the West neighbor to the list of valid neighbors to be returned
//		//Set the block two higher than the West neighbor to Material.ORANGE_CARPET
//		if(!blockWest.getType().equals(Material.AIR) && !blockWest1Above.getType().equals(Material.AIR) && blockWest2Above.getType().equals(Material.AIR)) {
//			blockWest2Above.setType(Material.ORANGE_CARPET);
//			Location WestScenario2 = blockWest1Above.getLocation();
//			neighbors.add(WestScenario2);
//		}
//		
//		//SCENARIO 3: The West neighbor is NOT air, and both the block above and two blocks above are NOT air, NOT VALID
//		//do NOT add anything to the valid list, since you cannot jump two blocks
//		//Set the block three higher than the West neighbor to Material.RED_CARPET
//		if(!blockWest.getType().equals(Material.AIR) && !blockWest1Above.getType().equals(Material.AIR) && !blockWest2Above.getType().equals(Material.AIR)) {
//			Block blockWest3Above = neighborN.add(0, 3, 0).getBlock();
//			blockWest3Above.setType(Material.RED_CARPET);
//		}
//		
//		//SCENARIO 4: The West neighbor IS air, and the block one below is NOT air, VALID
//		//add the location of the block one below the West neighbor to the list of valid neighbors to be returned
//		//Set the block of the West neighbor to Material.ORANGE_CARPET
//		if(blockWest.getType().equals(Material.AIR) && !blockWest1Below.getType().equals(Material.AIR)) {
//			blockWest.setType(Material.ORANGE_CARPET);
//			Location WestScenario4 = blockWest1Below.getLocation();
//			neighbors.add(WestScenario4);
//		}
//		
//		//SCENARIO 5: The West neighbor IS air, and both the block below and two blocks below are air, NOT VALID
//		//do NOT add anything to the valid list, you do not want to drop to a location you cannot get back to!
//		//Set the block one below the West neighbor to Material.RED_Carpet
//		if(blockWest.getType().equals(Material.AIR) && blockWest1Below.getType().equals(Material.AIR) && blockWest2Below.getType().equals(Material.AIR)) {
//			blockWest1Below.setType(Material.RED_CARPET);
//		}
		
		
		//Will now return the arrayList of VALID neighbors, keep in mind that this could be a number between 0 and 4.
		return neighbors;
	}
	
	//Checks to see if the new neighbors have a closer location than the current block, and return that location
	public Location closestNeighbor(Location previous, ArrayList<Location> neighbors, Location goal) {
		
		//All possible combinations of valid neighbors
		if(neighbors.size() == 1) {
			//get both distances, we are looking for a smaller number meaning its closer to the goal
			double prevDist = previous.distance(goal);
			double neighborDist = neighbors.get(0).distance(goal);
			//previous is farther, return the neighbor location
			if(prevDist > neighborDist) {
				return neighbors.get(0);
			} else {
				return null;
			}
		} else if(neighbors.size() == 2) {
			double prevDist = previous.distance(goal);
			double neighborDist = neighbors.get(0).distance(goal);
			double neighborDist2 = neighbors.get(1).distance(goal);
			//Neighbor 1 was closer
			if(neighborDist < prevDist && neighborDist < neighborDist2) {
				return neighbors.get(0);
			}
			//Neighbor 2 was closer
			else if(neighborDist2 < prevDist && neighborDist2 < neighborDist) {
				return neighbors.get(1);
			} 
			//Neither of the neighbors were closer.
			else {
				return null;
			}
		} else if(neighbors.size() == 3) {
			double prevDist = previous.distance(goal);
			double neighborDist = neighbors.get(0).distance(goal);
			double neighborDist2 = neighbors.get(1).distance(goal);
			double neighborDist3 = neighbors.get(2).distance(goal);
			//Neighbor 1 was closer
			if(neighborDist < prevDist && neighborDist < neighborDist2 && neighborDist < neighborDist3) {
				return neighbors.get(0);
			}
			//Neighbor 2 was closer
			else if(neighborDist2 < prevDist && neighborDist2 < neighborDist && neighborDist2 < neighborDist3) {
				return neighbors.get(1);
			} 
			//Neighbor 3 was closer
			else if(neighborDist3 < prevDist && neighborDist3 < neighborDist && neighborDist3 < neighborDist2) {
				return neighbors.get(2);
			} 
			//None of the neighbors were closer.
			else {
				return null;
			}
		} else if(neighbors.size() == 4) {
			double prevDist = previous.distance(goal);
			double neighborDist = neighbors.get(0).distance(goal);
			double neighborDist2 = neighbors.get(1).distance(goal);
			double neighborDist3 = neighbors.get(2).distance(goal);
			double neighborDist4 = neighbors.get(3).distance(goal);
			//Neighbor 1 was closer
			if(neighborDist < prevDist && neighborDist < neighborDist2 && neighborDist < neighborDist3 && neighborDist < neighborDist4) {
				return neighbors.get(0);
			}
			//Neighbor 2 was closer
			else if(neighborDist2 < prevDist && neighborDist2 < neighborDist && neighborDist2 < neighborDist3 && neighborDist2 < neighborDist4) {
				return neighbors.get(1);
			} 
			//Neighbor 3 was closer
			else if(neighborDist3 < prevDist && neighborDist3 < neighborDist && neighborDist3 < neighborDist2 && neighborDist3 < neighborDist4) {
				return neighbors.get(2);
			} 
			//Neighbor 4 was closer
			else if(neighborDist4 < prevDist && neighborDist4 < neighborDist && neighborDist4 < neighborDist2 && neighborDist4 < neighborDist3) {
				return neighbors.get(3);
			} 
			//None of the neighbors were closer.
			else {
				return null;
			}
		}
		//This should NEVER be returned, something is VERY wrong if this gets here, since the program should have stopped before sending an empty arraylist.
		return null;
	}
	
	public boolean containsSameLocation(ArrayList<Location> locations, Location goal) {
		int goalX = (int) goal.getX();
		int goalY = (int) goal.getY();
		int goalZ = (int) goal.getZ();
		for(int x = 0; x < locations.size(); x++) {
			int newX = (int) locations.get(x).getX();
			int newY = (int) locations.get(x).getY();
			int newZ = (int) locations.get(x).getZ();
			if( (goalX == newX || goalX - newX == 1 || newX - goalX == 1) && (goalY == newY || goalY - newY == 1 || newY - goalY == 1) && (goalZ == newZ || goalZ - newZ == 1 || newZ - goalZ == 1) ) {
				//contains the same location within 1 block radius to account for floor/ceiling math effect
				return true;
			}
		}
		//does not contain the same location
		return false;
	}
}
