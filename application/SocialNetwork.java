package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
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
	
	private int size;//number of edges
	private int order;//number of users

	public SocialNetwork() {
		graph = new Graph();
		numberOfConnectedComponents = 0;
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
	public Set<Person> getFriends(String user) {
		Person user1 = new Person(user);
		return graph.getNeighbors(user1);
	}

	@Override
	public Set<Person> getMutualFriends(String friend1, String friend2) {

		Set<Person> user1Neighbors = graph.getNeighbors(graph.getNode(friend1));
		Set<Person> user2Neighbors = graph.getNeighbors(graph.getNode(friend2));

		Set<Person> mutualFriends = new HashSet<Person>();

		Iterator<Person> iterator = user1Neighbors.iterator();

		while (iterator.hasNext()) {
			Person compare1 = iterator.next();

			Iterator<Person> iterator2 = user2Neighbors.iterator();

			while (iterator2.hasNext()) {

				Person compare2 = iterator2.next();

				if (compare1.getName().equals(compare2.getName())) {
					mutualFriends.add(compare2);
				} else {

				}

			}

		}
		return mutualFriends;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getShortestPath(String user1, String user2) {
				
		Person start = graph.getNode(user1);
		Person end = graph.getNode(user2);

		class Pair implements Comparable {
			int weight;
			Person vertex;
			
			Pair(int weight,Person vertex) {
				this.weight = weight;
				this.vertex = vertex;
			}
			
			@Override
			public int compareTo(Object o) {
				return 0;
			}
		}
				
		HashMap<Person,Integer> visited = new HashMap<Person,Integer>();
		HashMap<Person,Integer> weight = new HashMap<Person,Integer>();
		HashMap<Person,Person> pred = new HashMap<Person,Person>();

		Set<Person> people = graph.getAllNodes();

		// initialize visited to false (0), weight to infinity (1000), pred to null
		for (Person p : people) {
			visited.put(p, 0);
			weight.put(p,1000);
			pred.put(p,null);
		}
		
		// set start vertex(user 1) total weight to 0
		weight.put(start, 0);
		
		// create new priority queue
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		pq.add(new Pair(0, start));
		
		while (!pq.isEmpty()) {
			
			// remove minimum of PQ (C) and set to visited
			Pair min = pq.remove();
			visited.put(min.vertex, 1);
			
			// for each unvisited successor (S) of C
			for (Person S: graph.getNeighbors(min.vertex)) {
				if ((Integer)visited.get(S)==0) {
					// if S's total weight can be reduced
					if ((Integer)weight.get(S) > (Integer)weight.get(min.vertex) + 1) {
						// S's total weight = C's total weight + edge weight from C to S
						// S's predecessor = C
						weight.put(S, (Integer)weight.get(min.vertex)+1);
						pred.put(S,min.vertex);
						// pq.insert( [S's total weight, S] )
						pq.remove(S);
						pq.add(new Pair((Integer)weight.get(min.vertex)+1, S));
					}
				}
			}	
		}
		
		ArrayList<Person> shortestPath = new ArrayList<Person>();
		shortestPath.add(end);
		while (!end.equals(start)) {
			Person predesessor = (Person)pred.get(end);
			shortestPath.add(predesessor);
			end = predesessor;
		}
		
		return shortestPath;
	}

	// work through each user in the graph
	// for the first user, add all friends to first new graph
	// then for each of the friends, add their friends to the first graph
	// mark all the user nodes of the initial friends as visited
	// do again on the first graph until no new friends are shown
	// then move on to the next unvisited node in all nodes
	@Override
	public Set<Graph> getConnectedComponents() {

		Set<Graph> connectedComponents = new HashSet<Graph>();// set containing
																// graphs of the
		// connected groups

		Set<Person> allUsers = graph.getAllNodes(); // set containing all users
													// in the network

		Iterator<Person> usersIterator = allUsers.iterator();
		Iterator<Person> visitedReset = allUsers.iterator();

		// make sure each node's visited value starts as false
		while (visitedReset.hasNext())
			visitedReset.next().setVisited(false);

		// work through each users in the social network to build out all the
		// connected components
		while (usersIterator.hasNext()) {
			Person user = usersIterator.next();

			// if the user hasn't been visited yet, conduct BFS and add the
			// resulting graph to the components set
			if (!user.getVisited()) {
				connectedComponents.add(BFSearch(user));
				numberOfConnectedComponents++;
			}


		}
		// return graphs represents the disjoint user groups in the social
		// network
		return connectedComponents;
	}

	// BFS to return a graph representing a users network, including all path
	// ie the returned graph will not include users that the specified user does
	// not have a path to
	private Graph BFSearch(Person user) {

		Graph graph1 = new Graph();

		// Create a queue for BFS
		LinkedList<Person> queue = new LinkedList<Person>();

		// mark the current user as visited and add them to the queue
		user.setVisited(true);
		queue.add(user);

		// the queue represents the unvisited nodes
		while (queue.size() != 0) {

			// Dequeue the user at the front and add them to the new graph
			user = queue.poll();
			graph1.addNode(user);

			// Get all the users friends, add edges between them and the user,
			// and add them to the queue
			Iterator<Person> friendsIterator = graph.getNeighbors(user)
					.iterator();
			while (friendsIterator.hasNext()) {
				Person friend = friendsIterator.next();
				if (!friend.getVisited()) {
					friend.setVisited(true);
					graph1.addEdge(user, friend);
					queue.add(friend);
				}
			}
		}
		return graph1;
	}

	@Override
	public int getNumberOfConnectedComponents() {
		return numberOfConnectedComponents;
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

	// Use getConnectedComponents method
	@Override
	public void saveToFile(File file) {
		
        Writer outFile;
        
        try {
        	
			outFile = new FileWriter(file);
        
	        Set<Graph> graphs = getConnectedComponents();
	        
	        for (Graph g: graphs) {
	        	
	        	Set<Person> people = g.getAllNodes();
	        	HashSet<String> visited = new HashSet<String>();
	        	
	        	int i = 0;
	        	// visit each person in each graph
	        	for (Person p: people) {
	        		
	        		Set<Person> friends = g.getNeighbors(p);
	        		
		        	for (Person f: friends) {
		        		outFile.write(i);
		        		if (!visited.contains(f.getName())) {
				        	outFile.write("a " + p.getName() + f.getName());
		        		}
		        	}
		        	// mark as visited
		        	visited.add(p.getName());
		        	i++;
	        	}
	        	
	        }
        						
	        outFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	// main method for testing purposes
	public static void main(String[] args) {
		SocialNetwork socialNetwork = new SocialNetwork();
		Person wally = new Person("wally");
		Person jack = new Person("jack");
		Person bill = new Person("bill");
		Person jake = new Person("jake");

		socialNetwork.graph.addNode(wally);
		socialNetwork.graph.addNode(jack);
		socialNetwork.graph.addNode(bill);
		socialNetwork.graph.addNode(jake);

		socialNetwork.graph.addEdge(wally, jack);
		socialNetwork.graph.addEdge(bill, jake);
		socialNetwork.graph.addEdge(wally, jake);
		socialNetwork.graph.addEdge(wally, bill);

		System.out.println(socialNetwork.graph);

		socialNetwork.graph.removeEdge(jack, wally);
		System.out.println(socialNetwork.graph);

		// socialNetwork.graph.removeNode(wally);

		// System.out.println(socialNetwork.graph.order());
		// System.out.println(socialNetwork.graph.size());
		System.out.println(
				socialNetwork.getMutualFriends("jake", "bill").toString());
		System.out.println(socialNetwork.getConnectedComponents().toString());
		// System.out.print(socialNetwork.graph);

	}
	
	//size = number of edges/friendships
	public int size() {
		return graph.size();
	}
	
	//order = number of users/nodes
	public int order() {
		return graph.order();
	}
	

}
