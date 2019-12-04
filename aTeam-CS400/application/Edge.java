package application;

/***************************************************************************************************
 *
 * @author Wally Estenson
 *
 * @version 1.0
 *          <p>
 *
 *          File: Edge.java
 *          <p>
 *
 *          Date: December 4, 2019
 *          <p>
 *
 *          Purpose: aTeam p3 - data structure implementation
 *
 *          Description: Represents edge connecting two person objects 
 *
 *          Comment:
 *
 ***************************************************************************************************/
public class Edge {

    //Represents the person that the new edge is connected to
    private Person person;
    
    //Represents second person object of the edge
  //  private Person person2;

    public Edge(Person person_) {
        person = person_;
      //  person2 = person_2;
    }

    public Person getPerson() {
        return person;
    }

   // public Person getPerson2() {
     //   return person2;
    //}

}