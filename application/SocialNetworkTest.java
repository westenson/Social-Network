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
	
/***Setup***/
	
	@BeforeAll
	public static void beforeClass() throws Exception{
	}
	
	@BeforeEach
	public void setUp()throws Exception{
		graphInstance = new SocialNetwork();
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		graphInstance = null;
	}
	
/***Tests***/	

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
		for (int i=0; i<10;i++) {
			graphInstance.addUser("user" + i);
		}
		assertEquals(10,graphInstance.order());
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
		
		for(int i = 0; i < 10; i++) {
			graphInstance.addUser(user + i);
		}
		assertEquals(10, graphInstance.order());
		
		for(int i = 0; i < 10; i++) {
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
	

}
