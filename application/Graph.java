package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/***************************************************************************************************
 *
 * @author Wally Estenson
 *
 * @version 1.0
 *          <p>
 *
 *          File: Graph.java
 *          <p>
 *
 *          Date: December 4, 2019
 *          <p>
 *
 *          Purpose: aTeam p3 - data structure implementation
 *
 *          Description: Undirected and unweighted graph implementation for the
 *          social network. Graph nodes are person objects.
 *
 *          Comment:
 *
 ***************************************************************************************************/

public class Graph implements GraphADT {

	private int size; // number of edges
	private int order; // number of users

	// adjacency list mapping each person to its connected edges
	private HashMap<Person, List<Person>> personsMap = new HashMap<Person, List<Person>>();

	// prints out personsMap for testing purposes
	@Override
	public String toString() {
		if (personsMap == null)
			return "";

		StringBuffer s = new StringBuffer();
		for (Person v : personsMap.keySet())
			s.append("\n    " + v.getName() + " -> "
					+ (personsMap.get(v)).toString());

		return s.toString();
	}

	/**
	 * Add the edge between user1 to user2 to this graph. (edge is undirected
	 * and unweighted) If either user does not exist, add vertex, and add edge,
	 * no exception is thrown. If the edge exists in the graph, no edge is added
	 * and no exception is thrown.
	 * 
	 * Valid argument conditions: 1. neither vertex is null 2. both vertices are
	 * in the graph 3. the edge is not in the graph
	 * 
	 * @param user1
	 */
	@Override
	public boolean addEdge(Person user1, Person user2) {
		// if the inputs are null, cannot add edge
		if (user1 == (null) || user2 == (null))
			return false;

		// if either of the users do not already exist, add them as new persons
		if (!personsMap.containsKey(user1))
			this.addNode(user1);

		if (!personsMap.containsKey(user2))
			this.addNode(user2);

		// add new edge to user1
		personsMap.get(user1).add(user2);
		personsMap.get(user2).add(user1);
		size++;
		return true;
	}

	/**
	 * Remove the edge between user1 and user2. (edge is undirected and
	 * unweighted so we remove from both users) If either vertex does not exist,
	 * or if an edge from vertex1 to vertex2 does not exist, no edge is removed
	 * and no exception is thrown.
	 * 
	 * Valid argument conditions: 1. neither vertex is null 2. both vertices are
	 * in the graph 3. the edge from vertex1 to vertex2 is in the graph
	 */
	@Override
	public boolean removeEdge(Person user1, Person user2) {
		// if either input is null, cannot remove edge
		if (user1 == (null) || user2 ==(null))
			return false;

		// if either input is not in the network, cannot remove edge
		if (!personsMap.containsKey(user1) || !personsMap.containsKey(user2))
			return false;

		// remove the edges
		personsMap.get(user1).remove(user2);
		personsMap.get(user2).remove(user1);
		size--;
		return true;
	}

	/**
	 * Add new user to the graph.
	 *
	 * If user is null or already exists, method ends without adding a user or
	 * throwing an exception.
	 * 
	 * Valid argument conditions: 1. user is non-null 2. user is not already in
	 * the graph
	 * 
	 * @param Person to be added
	 * @return true if user was added
	 */
	@Override
	public boolean addNode(Person user) {

		// parameter is invalid/null
		if (user == (null))
			return false;

		// network already contains the user
		if (personsMap.containsKey(user))
			return false;

		// add new person respective edge list to the map
		else {
			personsMap.put(user, new ArrayList<Person>());
			order++;
			return true;
		}
	}

	/**
	 * Remove a user and all associated edges from the graph.
	 * 
	 * If user is null or does not exist, method ends without removing a user,
	 * edges, or throwing an exception.
	 * 
	 * Valid argument conditions: 1. vertex is non-null 2. vertex is not already
	 * in the graph
	 * 
	 * @param Person user to be removed
	 * @return true if user was removed
	 */
	@Override
	public boolean removeNode(Person user) {

		// if parameter is an existing person, begin removal process
		if (personsMap.containsKey(user)) {

			// create set of neighbors and iterator so we can remove the edges
			Set<Person> userNeighbors = getNeighbors(user);
			Iterator<Person> iterator = userNeighbors.iterator();

			// removes edges of the node to be removed
			while (iterator.hasNext()) {
				Person friend = iterator.next();
				removeEdge(user, friend);
			}

			// remove the user from the social network
			personsMap.remove(user);
			order--;
			return true;
		}

		else
			return false;
	}

	/**
	 * Get all the neighbor (adjacent) vertices of a user Graph is undirected so
	 * no need to worry about inbound and outbound
	 * 
	 * @Param person that we'll be getting the neighbors of
	 * @Return list of neighbors
	 */
	@Override
	public Set<Person> getNeighbors(Person user) {
		Set<Person> neighbors = new HashSet<Person>();

		// iterate through the users neighbors and add them to the neighbors
		// list
		for (Person friend : personsMap.get(user))
			neighbors.add(friend);

		return neighbors;
	}

	/**
	 * get the person object of a specified name
	 * 
	 * @param string of persons name
	 * @return the person if they exist, otherwise null
	 */
	@Override
	public Person getNode(String user) {
		Set<Person> allNodes = getAllNodes();
		Iterator<Person> allNodesIterator = allNodes.iterator();

		while (allNodesIterator.hasNext()) {
			Person user1 = allNodesIterator.next();

			if (user1.getName().equals(user)) {
				return user1;
			}
		}

		return null;
	}

	/**
	 * get all the users of the social network
	 *
	 * @return all users in social network
	 */
	@Override
	public Set<Person> getAllNodes() {
		Set<Person> allUsers = new HashSet<Person>();

		// iterate through all the keys of the social network and add them to
		// the allUsers list
		for (Person user : personsMap.keySet())
			allUsers.add(user);

		return allUsers;
	}

	/**
	 * Return the number of users in the social network
	 *
	 * @return int number of users
	 */
	public int size() {
		return size;
	}

	/**
	 * Return the number of edges in the social network
	 *
	 * @return int number of friendships/edges
	 */
	public int order() {
		return order;
	}
}
