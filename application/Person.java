package application;

import java.util.ArrayList;
import java.util.Arrays;

/***************************************************************************************************
 *
 * @author Wally Estenson
 *
 * @version 1.0
 *          <p>
 *
 *          File: Person.java
 *          <p>
 *
 *          Date: December 4, 2019
 *          <p>
 *
 *          Purpose: aTeam p3 - data structure implementation
 *
 *          Description: A person object represents an individual in the social
 *          network. They have a name an array of the names of other persons who
 *          have been specified as their friends.
 *
 *          Comment:
 *
 ***************************************************************************************************/
public class Person {
    private String name;
    private String[] friends;
    private boolean visited;

  
    // prints out friends name for testing purposes 
    @Override
    public String toString() {
      return this.getName();
    }
    
    public Person(String name) {
        this.name = name;
        this.visited = false;
    }
    
    public Person(String name, String[] friends) {
        this.name = name;
        this.friends = friends;
        this.visited = false;
    }

    public Person getPerson() {
        return this;
    }
    
    public String getName() {
        return this.name;
    }

    public String[] getFriends() {
        return this.friends;
    }
    
    public boolean getVisited() {
        return this.visited;
    }
    
    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }
}
