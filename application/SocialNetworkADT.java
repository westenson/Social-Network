package application;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * This interface is for the social network visualization.
 * 
 * @author Drew Zimmerman
 *
 */
public interface SocialNetworkADT {

  /**
   * Adds a friend to the social network.
   * 
   * @param friend1 One of the friends.
   * @param friend2 The second friend to be added to the friendship.
   * @return true if friends were added.
   */
  public boolean addFriends(String friend1, String friend2);

  /**
   * Removes a friend from the network.
   * 
   * @param friend1 The first friend.
   * @param friend2 The second friend to remove the friendship.
   * @return true if friends were removed.
   */
  public boolean removeFriends(String friend1, String friend2);

  /**
   * Adds a new user to the network.
   * 
   * @param user The user to be added.
   * @return true if user was added.
   */
  public boolean addUser(String user);

  /**
   * Removes a user from the network.
   * 
   * @param user The user to be removed.
   * @return true is friend was removed.
   */
  public boolean removeUser(String user);

  /**
   * Gets the friends of a user.
   * 
   * @param user the user to figure out the friends of.
   * @return the users friends.
   */
  public Set<Person> getFriends(String user);

  /**
   * Gets the shared friendships between two users.
   * 
   * @param friend1 one of the friends.
   * @param friend2 the other friend to be compared.
   * @return the mutual friends between the two users.
   */
  public Set<Person> getMutualFriends(String friend1, String friend2);

  /**
   * Gets the shortest path between two users
   * 
   * @param user1 The first user
   * @param user2 The user to be compared.
   * @return the shortest path between the two users.
   */
  public List<Person> getShortestPath(String user1, String user2);

  /**
   * Gets the connected components.
   * 
   * @return the connected components.
   */
  public Set<Graph> getConnectedComponents();
  
  /**
   * Gets thes the number connected components.
   * 
   * @return the number of connected components.
   */
  public int getNumberOfConnectedComponents();

  public boolean loadFromFile(File file);

  public void saveToFile(File file);

}
