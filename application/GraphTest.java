/**
 * 
 */
package application;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

/***************************************************************************************************
*
* @author Andrew Irvine, Wally Estenson
*
* @version 1.3
*          <p>
*
*          File: GraphTest.java
*          <p>
*
*          Date: December 10, 2019
*          <p>
*
*          Purpose: aTeam p3 - data structure implementation testing
*
*          Description: Testing of the graph class 
*
*          Comment:
*
***************************************************************************************************/

class GraphTest {
	
	public static Graph graphInstance;

	
/**Setup**/
	@BeforeAll
	public static void beforeClass() throws Exception{
	}
	
	
	@BeforeEach
	public void setUp() throws Exception{
		graphInstance = new Graph();
	}
	
	@AfterEach
	public void tearDown()throws Exception{
		graphInstance = null;
	}

	
/**Tests for Graph**/			
	
	@Test
	public void test00_EmptyGraph() {
		assertEquals(0,graphInstance.order());
		assertEquals(0,graphInstance.size());
	}
	
	@Test
	public void test01_InsertNode1_OrderEquals1() {
		Person Person = new Person("user1");
		graphInstance.addNode(Person);
		assertEquals(1, graphInstance.order());
	}
	
	@Test
	public void test02_AddNode1_RemoveNode1_OrderEquals00() {
		Person Person1 = new Person ("testUser");
		
		graphInstance.addNode(Person1);
		assertEquals(1, graphInstance.order());
		graphInstance.removeNode(Person1);
		assertEquals(0, graphInstance.order());
	}
	
	@Test
	public void test03_AddEdge_MissingNodesCreated() {
		Person Person = new Person("testUser1");
		Person Person1 = new Person ("testUser2");
		
		graphInstance.addEdge(Person, Person1);
		assertEquals(2,graphInstance.order());
		assertEquals(1,graphInstance.size());
	}
	
	@Test
	public void test04_AddEdge_SingleMissingNodeCreated() {
		Person Person1 = new Person("testUser1");
		Person Person2 = new Person("testUser2");
		
		assertEquals(0,graphInstance.order());
		
		graphInstance.addNode(Person1);
		assertEquals(1, graphInstance.order());
		graphInstance.addEdge(Person1, Person2);
		assertEquals(2, graphInstance.order());
		assertEquals(1, graphInstance.size());	
	}
	

	@Test
	public void test05_Add05Edges_GetNeighbors() {
		Person Person1 =new Person ("testUser1");
		Person neighbor1 = new Person ("testEdge");
		Person neighbor2 = new Person ("testEdge");


		graphInstance.addEdge(Person1, neighbor1);
		graphInstance.addEdge(Person1, neighbor2);
		
		
		Set<Person> neighbors = graphInstance.getNeighbors(Person1);
		
		if (!neighbors.contains(neighbor1)) {
			fail("Improper set returned: " + graphInstance.getNeighbors(Person1) + "\n" +  "expected set: " + neighbors);	
		}
		if (!neighbors.contains(neighbor2)) {
			fail("Improper set returned: " + graphInstance.getNeighbors(Person1) + "\n" +  "expected set: " + neighbors);	
		}
	}

	
	//adds person1 node with neighbors. Then removes person one and checks that neighbors still dont have person1 as a friend
	@Test
	public void test06_RemoveNode_RemovesEdgeBetweenNodes() {
		Person Person1 =new Person ("testUser1");
		Person neighbor1 = new Person ("testEdge");
		Person neighbor2 = new Person ("testEdge");
		

		graphInstance.addEdge(Person1, neighbor1);
		graphInstance.addEdge(Person1, neighbor2);
		
		graphInstance.removeNode(Person1);
		
		Set<Person> neighbors1 = graphInstance.getNeighbors(neighbor1);
		Set<Person> neighbors2 = graphInstance.getNeighbors(neighbor2);

		if (neighbors1.contains(Person1)) {
			fail("Failed to remove edge between users when user was removed.");	
		}
		if (neighbors2.contains(Person1)) {
			fail("Failed to remove edge between users when user was removed.");	
		}
	}
	
	@Test
	public void test07_Add05Edges_Remove05Edges_SizeEquals05_OrderEquals00() {
		Person Person1 = new Person("testUser");
		Person Person2 = new Person("testUser");
		Person Person3 = new Person("testUser");
		Person Person4 = new Person("testUser");
		Person Person5 = new Person("testUser");
		Person Person6 = new Person("testUser");

		graphInstance.addEdge(Person1, Person2);
		graphInstance.addEdge(Person1, Person3);
		graphInstance.addEdge(Person1, Person4);
		graphInstance.addEdge(Person1, Person5);
		graphInstance.addEdge(Person1, Person6);
		
		assertEquals(6, graphInstance.order());
		assertEquals(5, graphInstance.size());

		graphInstance.removeEdge(Person1, Person2);
		graphInstance.removeEdge(Person1, Person3);
		graphInstance.removeEdge(Person1, Person4);
		
		assertEquals(6,graphInstance.order());
		assertEquals(2, graphInstance.size());
	}

	@Test
	public void test08_AddNullNode_OrderEquals00() {
		graphInstance.addNode(null);
		
		assertEquals(0,graphInstance.order());
	}
	
	@Test
	public void test09_AddNullEdge_SizeEquals00() {
		Person Person = new Person("testUser1");
		graphInstance.addEdge(Person, null);
		
		assertEquals(0,graphInstance.order());
		assertEquals(0,graphInstance.size());
	}	
	
	//Figure out how to test - but this works correctly
	@Test
	public void test010_AddUser_RemoveUser() {
		Person Person = new Person("testUser1");
		Person Person1 = new Person("testUser2");
		graphInstance.addEdge(Person, Person1);
		graphInstance.addNode(Person);
		graphInstance.addNode(Person1);
		graphInstance.removeNode(Person);
	}	
}
