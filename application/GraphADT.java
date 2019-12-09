package application;

import java.util.Set;

/**
 * This interface is for the graph.
 * 
 * @author Drew Zimmerman
 *
 */
public interface GraphADT {
  /**
   * Adds an edge between two users.
   * 
   * @param user1 The first friend.
   * @param user2 The second friend used to create the edge.
   * @return true if edge creation was successful, else false.
   */
  public boolean addEdge(Person user1, Person user2);

  /**
   * Removes an edge between two friends.
   * 
   * @param user1 The first user.
   * @param user2 The second user.
   * @return true is successfully deleted, else false.
   */
  public boolean removeEdge(Person user1, Person user2);

  /**
   * Adds a user to the graph.
   * 
   * @param user the user to be added.
   * @return true if user was added.
   */
  public boolean addNode(Person user);

  /**
   * Removes a user from the graph.
   * 
   * @param user the user to be removed.
   * @return true if the user was removed.
   */
  public boolean removeNode(Person user);

  /**
   * Gets the neighbors of a user in the graph.
   * 
   * @param user the user to retrieve the neighbors from.
   * @return the neighbors of the user.
   */
  public Set<Person> getNeighbors(Person user);

  /**
   * Get a user from the graph.
   * 
   * @param user the user to get from.
   * @return the user person from the graph.
   */
  public Person getNode(String user);

  /**
   * Gets all the users from the graph.
   * 
   * @return every user from the graph.
   */
  public Set<Person> getAllNodes();

}
