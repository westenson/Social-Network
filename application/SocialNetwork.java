package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/***************************************************************************************************
 *
 * @author Wally Estenson, Cela Peterson
 *
 * @version 1.0
 *          <p>
 *
 *          File: SocialNetwork.java
 *          <p>
 *
 *          Date: December 9, 2019
 *          <p>
 *
 *          Purpose: aTeam p3 - data structure implementation
 *
 *          Description: Social network adt that utilizes the graph.
 *
 *          Comment:
 *
 ***************************************************************************************************/

public class SocialNetwork implements SocialNetworkADT {

	private Graph graph;
	private int numberOfConnectedComponents;

	public SocialNetwork() {
		graph = new Graph();
	}

	@Override
	public boolean addFriends(String friend1, String friend2) {
		Person person1 = new Person(friend1);
		Person person2 = new Person(friend2);

		return graph.addEdge(person1, person2);
	}

	@Override
	public boolean removeFriends(String friend1, String friend2) {
		Person person1 = new Person(friend1);
		Person person2 = new Person(friend2);

		return graph.removeEdge(person1, person2);
	}

	@Override
	public boolean addUser(String user) {
		Person user1 = new Person(user);
		return graph.addNode(user1);
	}

	@Override
	public boolean removeUser(String user) {
		Person user1 = new Person(user);
		return graph.removeNode(user1);
	}

	@Override
	public Set<Person> getFriend(String user) {
		Person user1 = new Person(user);
		return graph.getNeighbors(user1);
	}

	@Override
	public Set<Person> getMutualFriends(String friend1, String friend2) {
		Person user1 = new Person(friend1);
		Person user2 = new Person(friend2);

		Set<Person> user1Neighbors = graph.getNeighbors(user1);
		Set<Person> user2Neighbors = graph.getNeighbors(user2);
		Set<Person> mutualFriends = new HashSet<Person>();

		Iterator<Person> iterator = user1Neighbors.iterator();
		Iterator<Person> iterator2 = user2Neighbors.iterator();

		while (iterator.hasNext()) {
			Person compare1 = iterator.next();

			while (iterator2.hasNext()) {

				Person compare2 = iterator2.next();

				if (compare1.getName().contentEquals(compare2.getName())) {
					mutualFriends.add(compare2);
				} else {
					iterator2.next();
				}

			}
			iterator.next();
		}
		return mutualFriends;
	}

	@Override
	public List<Person> getShortestPath(String user1, String user2) {
		// Person user1 = new Person(user1);
		// Person user1 = new Person(user1);
		// TODO Auto-generated method stub
		return null;
	}

	// WORK IN PROGRESS
	@Override
	public Set<Graph> getConnectedComponents() {
		
		Set <Person> allNodes = graph.getAllNodes();
		Set<Graph> connectedComponents = null; 
		Iterator<Person> iterator = allNodes.iterator();
		
		boolean[] visit = new boolean[graph.order()];
		
		while (iterator.hasNext()) {
			Person user = iterator.next();
			

			
		}
		
		for (int i = 0; i < graph.order(); i++) {
			//Set <Person> user1Neighbors = graph.getNeighbors(allNodes.)
			if (visit[i] == false)
				return DFSearch(i, visit); 
		}	
		
		
		return connectedComponents;
	}

	@Override
	public int getNumberOfConnectedComponents() {
		return numberOfConnectedComponents;
	}

	
	//WORK IN PROGRESS - to be used for connected component method 
	private Set<Graph> DFSearch(int i, boolean[] visit) {
		visit[i] = true;// TODO Auto-generated method stub
	
		//for ()
	
		Graph graph1 = new Graph();
		return null;
	
	}

	@Override
	public void loadFromFile(File file) {
		Scanner scnr = null;
		try {
			scnr = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scnr.hasNextLine()) {
			String[] commands = scnr.nextLine().trim().split(" ");
			switch (commands[0]) {
			case "r":
				if (commands.length == 2) {
					removeUser(commands[1]);
				} else if (commands.length == 3) {
					removeFriends(commands[1], commands[2]);
				}
			case "a":
				addUser(commands[1]);
			case "s": // setCentralUser(commands[1]);

			}
		}
		scnr.close();
	}

	//Use getConnectedComponents method
	@Override
	public void saveToFile(File file) {
		// TODO Auto-generated method stub

	}

	// main method for testing purposes
	public static void main(String[] args) {
		SocialNetwork socialNetwork = new SocialNetwork();
		Person wally = new Person("wally");
		Person jack = new Person("jack");

		socialNetwork.graph.addNode(wally);
		socialNetwork.graph.addNode(jack);

		socialNetwork.graph.addEdge(wally, jack);

		System.out.println(socialNetwork.graph);

		socialNetwork.graph.removeEdge(jack, wally);

		// System.out.println(socialNetwork.graph.order());
		// System.out.println(socialNetwork.graph.size());
		System.out.print(socialNetwork.graph);

	}

}
