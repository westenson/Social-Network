/**
 * 
 */
package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Andrew Irvine
 *
 */
class SocialNetworkTest {

	public static SocialNetwork graphInstance;

	/*** Setup ***/

	@BeforeAll
	public static void beforeClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		graphInstance = new SocialNetwork();
	}

	@AfterEach
	public void tearDown() throws Exception {
		graphInstance = null;
	}

	/*** Tests ***/

	@Test
	public void test00_NewSocialNetworkIsEmpty() {
		assertEquals(0, graphInstance.order());
		assertEquals(0, graphInstance.size());
	}

	@Test
	public void test01_add01User_OrderEquals01() {
		graphInstance.addUser("user");
		assertEquals(1, graphInstance.order());
	}

	@Test
	public void test02_add10Users_OrderEquals10() {
		for (int i = 0; i < 10; i++) {
			graphInstance.addUser("user" + i);
		}
		assertEquals(10, graphInstance.order());
	}

	@Test
	public void test03_add01User_remove01User_OrderEquals00() {
		String user = "testUser";

		graphInstance.addUser(user);
		assertEquals(1, graphInstance.order());
		graphInstance.removeUser(user);

		assertEquals(0, graphInstance.order());
	}

	@Test
	public void test04_add10Users_remove10Users_OrderEquals00() {
		String user = "testUser";

		for (int i = 0; i < 10; i++) {
			graphInstance.addUser(user + i);
		}
		assertEquals(10, graphInstance.order());

		for (int i = 0; i < 10; i++) {
			graphInstance.removeUser(user + i);
		}
		assertEquals(0, graphInstance.order());
	}

	@Test
	public void test06_add10Users_remove10Users_OrderEquals00() {
		String user = "testUser";

		for (int i = 0; i < 10; i++) {
			graphInstance.addUser(user + i);
		}
		assertEquals(10, graphInstance.order());

		for (int i = 0; i < 10; i++) {
			graphInstance.removeUser(user + i);
		}
		assertEquals(0, graphInstance.order());
	}

	@Test
	public void test05_add01Friendship_SizeEquals01_OrderEquals02() {
		String user1 = "testUser1";
		String user2 = "testUser2";

		graphInstance.addFriends(user1, user2);
		assertEquals(2, graphInstance.order());
		assertEquals(1, graphInstance.size());
	}

	@Test
	public void test010_AddFriends_RemoveUser() {
		graphInstance.addUser("testUser3");
		graphInstance.addFriends("testUser1", "testUser2");

		graphInstance.removeUser("testUser1");

		if (graphInstance.getUser("testUser1") != null) {
			fail("Failed to remove user.");
		}
	}

	@Test
	public void test011_AddUser_RemoveUser() {
		graphInstance.addUser("testUser3");

		graphInstance.addUser("testUser1");

		graphInstance.removeUser("testUser1");

		if (graphInstance.getUser("testUser1") != null) {
			fail("Failed to remove user.");
		}
	}

	@Test
	public void test012_AddUsers_RemoveFriends() {
		graphInstance.addUser("testUser3");

		graphInstance.addUser("testUser1");

		graphInstance.addFriends("testUser3", "testUser1");

		graphInstance.removeFriends("testUser3", "testUser1");

		if (!graphInstance.getFriends("testUser1").isEmpty()) {
			fail("Failed to remove edge.");
		}
		if (!graphInstance.getFriends("testUser3").isEmpty()) {
			fail("Failed to remove edge.");
		}
	}

}
