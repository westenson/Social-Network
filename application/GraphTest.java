/**
 * 
 */
package application;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

/**
 * @author airvi
 *
 */
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
	public void test02_AddNode1_RemoveNode1_OrderEquals1() {
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
	
/**
 * Must fix test still
	@Test
	public void test05_Add05Edges_GetNeighbors() {
		Person Person1 =new Person ("testUser1");
		Person target = new Person ("testEdge");
		List<String>neighbors = new ArrayList<String>();
		for (int i=0; i<5; i++) {
			graphInstance.addEdge(Person1, target);
			neighbors.add(edges.getTarget());
		}
		if (!neighbors.equals(graphInstance.getNeighbors(Person1))) {
			fail("Improper set returned: " + graphInstance.getNeighbors(Person1) + "\n" +  "expected set: " + neighbors);	
		}
	}
**/
	
	@Test
	public void test06_RemoveNode_RemovesEdgeBetweenNodes() {
		
	}

}
