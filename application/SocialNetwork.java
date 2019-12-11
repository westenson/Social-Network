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
 * @version 1.3
 *          <p>
 *
 *          File: SocialNetwork.java
 *          <p>
 *
 *          Date: December 10, 2019
 *          <p>
 *
 *          Purpose: aTeam p3 - data structure implementation
 *
 *          Description: Social network ADT that utilizes the graph.java class.
 *          Class enables user to call all methods within the graph class to
 *          create the social network (adding and removing users, adding and
 *          removing friends, and getting a list of a user's friends). Further
 *          this class, has methods with new functionality that allow the user
 *          to get mutual friends between two users, get the shortest path
 *          between two users, get the connected components within the network
 *          (friend groups), use an external file to create the social network,
 *          and export a file with the social network.
 *
 *          Comment:
 *
 ***************************************************************************************************/

public class SocialNetwork implements SocialNetworkADT {

	private Graph graph;
	private int numberOfConnectedComponents;

	/**
	 * Social network constructor. Creates an instance of graph.
	 */
	public SocialNetwork() {
		graph = new Graph();
		numberOfConnectedComponents = 0;
	}

	/**
	 * Creates a new user and calls the graph class to add that user as a new
	 * node in the graph
	 * 
	 * @param String user to be added
	 * @return true is user is added
	 */
	@Override
	public boolean addUser(String user) {
		Person user1 = new Person(user);
		return graph.addNode(user1);
	}

	/**
	 * Removes a user from the social network by calling the graph class method
	 * to remove a node
	 * 
	 * @param String user to be removed
	 * @return true is user is removed
	 */
	@Override
	public boolean removeUser(String user) {
		return graph.removeNode(graph.getNode(user));
	}

	/**
	 * Adds a friendship/edge between two users. If either of the input users do
	 * not yet exist, add them first by calling the method in the graph class to
	 * add a new node.
	 * 
	 * @param String friend1 and String friend2
	 * @return true if friendship is added
	 */
	@Override
	public boolean addFriends(String friend1, String friend2) {
		Person user1 = new Person(friend1);
		Person user2 = new Person(friend2);

		// if either of the input friends do not yet exist, add them as new
		// nodes
		if (graph.getNode(friend1) == null)
			graph.addNode(user1);
		if (graph.getNode(friend2) == null)
			graph.addNode(user2);

		return graph.addEdge(graph.getNode(friend1), graph.getNode(friend2));
	}

	/**
	 * Call the graph method to remove a friendship/edge between two users
	 * 
	 * @param String friend1 and String friend2
	 * @return true if friendship is removed
	 */
	@Override
	public boolean removeFriends(String friend1, String friend2) {
		return graph.removeEdge(graph.getNode(friend1), graph.getNode(friend2));
	}

	/**
	 * Call the graph method to get the friends of the specified user
	 * 
	 * @param String user
	 * @return Set of friends of the user
	 */
	@Override
	public Set<Person> getFriends(String user) {
		return graph.getNeighbors(graph.getNode(user));
	}

	/**
	 * Method to get the mutual friends between two users. Utilizes the
	 * getNeighbors method in the graph class for both users and compares these
	 * sets to find mutual friends.
	 * 
	 * @param String friend1 and String friend2
	 * @return Set of mutual friends
	 */
	@Override
	public Set<Person> getMutualFriends(String friend1, String friend2) {

		// create two sets of the specified users' friends
		Set<Person> user1Neighbors = graph.getNeighbors(graph.getNode(friend1));
		Set<Person> user2Neighbors = graph.getNeighbors(graph.getNode(friend2));

		// create a third set that we will add mutual friends to
		Set<Person> mutualFriends = new HashSet<Person>();

		Iterator<Person> iterator = user1Neighbors.iterator();

		// iterate through the first user's friends
		while (iterator.hasNext()) {
			// create a new person object of the first user's friend
			Person compare1 = iterator.next();

			Iterator<Person> iterator2 = user2Neighbors.iterator();

			// iterate through the second user's friends
			while (iterator2.hasNext()) {
				// create a new person object of the second user's friend
				Person compare2 = iterator2.next();

				// compare the friends, if its a match, add to the mutual
				// friends set
				if (compare1.getName().equals(compare2.getName())) {
					mutualFriends.add(compare2);
				}
			}
		}
		return mutualFriends;
	}

	/**
	 * Method to get the shortest path between two users
	 * 
	 * @param String user1 and String user2
	 * @return List of the person/s that are the shortest path between the users
	 */
	@Override
	public List<Person> getShortestPath(String user1, String user2) {

		Person start = graph.getNode(user1);
		Person end = graph.getNode(user2);

		class Pair implements Comparable {
			int weight;
			Person vertex;

			Pair(int weight, Person vertex) {
				this.weight = weight;
				this.vertex = vertex;
			}

			@Override
			public int compareTo(Object o) {
				return 0;
			}
		}

		HashMap<Person, Integer> visited = new HashMap<Person, Integer>();
		HashMap<Person, Integer> weight = new HashMap<Person, Integer>();
		HashMap<Person, Person> pred = new HashMap<Person, Person>();

		Set<Person> people = graph.getAllNodes();

		// initialize visited to false (0), weight to infinity (1000), pred to
		// null
		for (Person p : people) {
			visited.put(p, 0);
			weight.put(p, 1000);
			pred.put(p, null);
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
			for (Person S : graph.getNeighbors(min.vertex)) {
				if ((Integer) visited.get(S) == 0) {
					// if S's total weight can be reduced
					if ((Integer) weight
							.get(S) > (Integer) weight.get(min.vertex) + 1) {
						// S's total weight = C's total weight + edge weight
						// from C to S
						// S's predecessor = C
						weight.put(S, (Integer) weight.get(min.vertex) + 1);
						pred.put(S, min.vertex);
						// pq.insert( [S's total weight, S] )
						pq.remove(S);
						pq.add(new Pair((Integer) weight.get(min.vertex) + 1,
								S));
					}
				}
			}
		}

		ArrayList<Person> shortestPath = new ArrayList<Person>();
		shortestPath.add(end);
		while (!end.equals(start)) {
			Person predesessor = (Person) pred.get(end);
			shortestPath.add(predesessor);
			end = predesessor;
		}

		return shortestPath;
	}

	/**
	 * Method that returns a set of graphs representing the connected components
	 * of the social network.
	 * 
	 * @return Set of graphs of the connected components
	 */
	@Override
	public Set<Graph> getConnectedComponents() {
		// must rest the number of connected components each time its called
		numberOfConnectedComponents = 0;

		// set that we will be adding the connected components to
		Set<Graph> connectedComponents = new HashSet<Graph>();

		// set containing all the users in the network
		Set<Person> allUsers = graph.getAllNodes();

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

	/**
	 * Breadth First Search method that returns a graph representing a users
	 * network, including all paths ie. the returned graph will not include
	 * users that the specififed user does not have a path to
	 * 
	 * @return Graph of users network
	 */
	private Graph BFSearch(Person user) {

		Graph usersNetwork = new Graph();

		// Create a queue for BFS
		LinkedList<Person> queue = new LinkedList<Person>();

		// mark the current user as visited and add them to the queue
		user.setVisited(true);
		queue.add(user);

		// the queue represents the unvisited nodes
		while (queue.size() != 0) {

			// Dequeue the user at the front and add them to the new graph
			user = queue.poll();
			usersNetwork.addNode(user);

			Iterator<Person> friendsIterator = graph.getNeighbors(user)
					.iterator();

			// Get all the users friends, add edges between them and the
			// user,and add them to the queue
			while (friendsIterator.hasNext()) {
				Person friend = friendsIterator.next();
				if (!friend.getVisited()) {
					friend.setVisited(true);
					usersNetwork.addEdge(user, friend);
					queue.add(friend);
				}
			}
		}
		return usersNetwork;
	}

	/**
	 * Gets the number of connected components in the social network. Must call
	 * the getConnectedComponents method first which does the calculation.
	 * 
	 * @return int number of connected components 
	 */
	@Override
	public int getNumberOfConnectedComponents() {
		getConnectedComponents();
		return numberOfConnectedComponents;
	}

	/**
	 * Takes file and builds social network 
	 * 
	 * @param file 
	 */
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

	/**
	 * Exports social network to a specified file 
	 * 
	 * @param file 
	 */
	@Override
	public void saveToFile(File file) {

		Writer outFile;

		try {

			outFile = new FileWriter(file);

			Set<Graph> graphs = getConnectedComponents();

			for (Graph g : graphs) {

				Set<Person> people = g.getAllNodes();
				HashSet<String> visited = new HashSet<String>();

				int i = 0;
				// visit each person in each graph
				for (Person p : people) {

					Set<Person> friends = g.getNeighbors(p);

					for (Person f : friends) {
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

	/**
	 * Returns number of edges in the social network
	 * 
	 * @param int size
	 */
	public int size() {
		return graph.size();
	}

	/**
	 * Returns number of users in the social network
	 * 
	 * @param int order
	 */
	public int order() {
		return graph.order();
	}

	/**
	 * Prints social network
	 * For testing purposes 
	 * 
	 * @return String of social network
	 */
	@Override
	public String toString() {
		return graph.toString();

	}

	/**
	 * Gets person object 
	 * For testing purposes 
	 * 
	 * @param String user
	 * @return Person user 
	 */
	public Person getUser(String user) {
		return graph.getNode(user);

	}
	
	public ArrayList<String> getAllUsers() {
		Set<Person> people = graph.getAllNodes();
		Iterator<Person> iterator = people.iterator();
		
		ArrayList<String> users = new ArrayList<String>();
		while (iterator.hasNext()) {
			users.add(iterator.next().getName());
		}
		return users;
		
	}
}
