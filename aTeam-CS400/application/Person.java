package application;

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

    public Person(String name, String[] friends) {
        this.name = name;
        this.friends = friends;
    }

    public String getName() {
        return this.name;
    }

    public String[] getFriends() {
        return this.friends;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }
}
