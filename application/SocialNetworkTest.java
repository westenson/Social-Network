/**
 * 
 */
package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/***************************************************************************************************
*
* @author Andrew Irvine, Wally Estenson
*
* @version 1.5
*          <p>
*
*          File: SocialNetorkTest.java
*          <p>
*
*          Date: December 10, 2019
*          <p>
*
*          Purpose: aTeam p3 - data structure implementation testing
*
*          Description: Testing of the socialNetwork class 
*
*          Comment:
*
***************************************************************************************************/

class SocialNetworkTest {

	public static SocialNetwork socialNetworkInstance;

	/*** Setup ***/

	@BeforeAll
	public static void beforeClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		socialNetworkInstance = new SocialNetwork();
	}

	@AfterEach
	public void tearDown() throws Exception {
		socialNetworkInstance = null;
	}

	/*** Tests ***/

	@Test
	public void test00_NewSocialNetworkIsEmpty() {
		assertEquals(0, socialNetworkInstance.order());
		assertEquals(0, socialNetworkInstance.size());
	}

	@Test
	public void test01_add01User_OrderEquals01() {
		socialNetworkInstance.addUser("user");
		assertEquals(1, socialNetworkInstance.order());
	}

	@Test
	public void test02_add10Users_OrderEquals10() {
		for (int i = 0; i < 10; i++) {
			socialNetworkInstance.addUser("user" + i);
		}
		assertEquals(10, socialNetworkInstance.order());
	}

	@Test
	public void test03_add01User_remove01User_OrderEquals00() {
		String user = "testUser";

		socialNetworkInstance.addUser(user);
		assertEquals(1, socialNetworkInstance.order());
		socialNetworkInstance.removeUser(user);

		assertEquals(0, socialNetworkInstance.order());
	}

	@Test
	public void test04_add10Users_remove10Users_OrderEquals00() {
		String user = "testUser";

		for (int i = 0; i < 10; i++) {
			socialNetworkInstance.addUser(user + i);
		}
		assertEquals(10, socialNetworkInstance.order());

		for (int i = 0; i < 10; i++) {
			socialNetworkInstance.removeUser(user + i);
		}
		assertEquals(0, socialNetworkInstance.order());
	}

	@Test
	public void test05_add01Friendship_SizeEquals01_OrderEquals02() {
		String user1 = "testUser1";
		String user2 = "testUser2";

		socialNetworkInstance.addFriends(user1, user2);
		assertEquals(2, socialNetworkInstance.order());
		assertEquals(1, socialNetworkInstance.size());
	}

	@Test
	public void test06_add10Users_remove10Users_OrderEquals00() {
		String user = "testUser";

		for (int i = 0; i < 10; i++) {
			socialNetworkInstance.addUser(user + i);
		}
		assertEquals(10, socialNetworkInstance.order());

		for (int i = 0; i < 10; i++) {
			socialNetworkInstance.removeUser(user + i);
		}
		assertEquals(0, socialNetworkInstance.order());
	}

	@Test
	public void test07_AddFriends_RemoveUser() {
		socialNetworkInstance.addUser("testUser3");
		socialNetworkInstance.addFriends("testUser1", "testUser2");

		socialNetworkInstance.removeUser("testUser1");

		if (socialNetworkInstance.getUser("testUser1") != null) {
			fail("Failed to remove user.");
		}
	}

	@Test
	public void test08_AddUser_RemoveUser() {
		socialNetworkInstance.addUser("testUser3");

		socialNetworkInstance.addUser("testUser1");

		socialNetworkInstance.removeUser("testUser1");

		if (socialNetworkInstance.getUser("testUser1") != null) {
			fail("Failed to remove user.");
		}
	}

	@Test
	public void test09_AddUsers_RemoveFriends() {
		socialNetworkInstance.addUser("testUser3");

		socialNetworkInstance.addUser("testUser1");

		socialNetworkInstance.addFriends("testUser3", "testUser1");

		socialNetworkInstance.removeFriends("testUser3", "testUser1");

		if (!socialNetworkInstance.getFriends("testUser1").isEmpty()) {
			fail("Failed to remove edge.");
		}
		if (!socialNetworkInstance.getFriends("testUser3").isEmpty()) {
			fail("Failed to remove edge.");
		}
	}

	@Test
	public void test010_GetMutualFriends() {
		socialNetworkInstance.addUser("1");
		socialNetworkInstance.addUser("2");
		socialNetworkInstance.addUser("3");
		socialNetworkInstance.addUser("4");

		socialNetworkInstance.addFriends("1", "2");
		socialNetworkInstance.addFriends("3", "2");
		socialNetworkInstance.addFriends("3", "1");

		if (!socialNetworkInstance.getMutualFriends("1", "2")
				.contains(socialNetworkInstance.getUser("3"))) {
			fail("User 3 should be a mutual friend of 1 and 2");
		}
		if (socialNetworkInstance.getMutualFriends("1", "2")
				.contains(socialNetworkInstance.getUser("4"))) {
			fail("User 4 should not be a mutual friend of 1 and 2");
		}
	}

	@Test
	public void test011_GetShortestPath() {
		socialNetworkInstance.addUser("1");
		socialNetworkInstance.addUser("2");
		socialNetworkInstance.addUser("3");
		socialNetworkInstance.addUser("4");

		socialNetworkInstance.addFriends("1", "2");
		socialNetworkInstance.addFriends("3", "2");
		socialNetworkInstance.addFriends("3", "1");
		socialNetworkInstance.addFriends("3", "4");

		// Shortest path should contain 3,1,4 and not 2
		if (!socialNetworkInstance.getShortestPath("1", "4")
				.contains(socialNetworkInstance.getUser("3"))) {
			fail("Incorrect Shortest Path (1)");
		}
		if (!socialNetworkInstance.getShortestPath("1", "4")
				.contains(socialNetworkInstance.getUser("1"))) {
			fail("Incorrect Shortest Path (2)");
		}
		if (!socialNetworkInstance.getShortestPath("1", "4")
				.contains(socialNetworkInstance.getUser("4"))) {
			fail("Incorrect Shortest Path (3)");
		}
		if (socialNetworkInstance.getShortestPath("1", "4")
				.contains(socialNetworkInstance.getUser("2"))) {
			fail("Incorrect Shortest Path (4)");
		}
	}

	@Test
	public void test012_connectedComponentsNumber() {
		socialNetworkInstance.addUser("1");
		socialNetworkInstance.addUser("2");
		socialNetworkInstance.addUser("3");
		socialNetworkInstance.addUser("4");
		socialNetworkInstance.addUser("5");
		socialNetworkInstance.addUser("6");

		socialNetworkInstance.addFriends("1", "2");
		socialNetworkInstance.addFriends("3", "2");
		socialNetworkInstance.addFriends("3", "1");
		socialNetworkInstance.addFriends("3", "4");

		if (socialNetworkInstance.getNumberOfConnectedComponents() != 3) {
			fail("Number of connected components should be 3");
		}
	}
}
