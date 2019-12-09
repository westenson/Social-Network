package application;

import java.io.File;
import java.util.List;
import java.util.Set;

public class SocialNetwork implements SocialNetworkADT {


    private Graph graph;

    public SocialNetwork(){
        graph = new Graph();
    }

    
	@Override
	public boolean addFriends(String friend1, String friend2) {
		Person person1 = new Person(friend1);
		Person person2 = new Person(friend2);
		
		
		return graph.addEdge(person1, person2);
	}

	@Override
	public boolean removeFriends(String friend1, String friend2) {
		Person person1 = new Person(friend1);
		Person person2 = new Person(friend2);
		
		return graph.removeEdge(person1, person2);
	}

	@Override
	public boolean addUser(String user) {
		Person user1 = new Person(user);
		return graph.addNode(user1);
	}

	@Override
	public boolean removeUser(String user) {
		Person user1 = new Person(user);
		return graph.removeNode(user1);
	}

	@Override
	public Set<Person> getFriend(String user) {
		Person user1 = new Person(user);
		return graph.getNeighbors(user1);
	}

	@Override
	public Set<Person> getMutualFriends(String friend1, String friend2) {
		// TODO Auto-generated method stub
		Person user1 = new Person(friend1);
		Person user2 = new Person(friend2);
		
		Set user1Neighbors = graph.getNeighbors(user1);
		Set user2Neighbors = graph.getNeighbors(user1);
		
		//now compare the two set and return mutual friends
		return null;
	}

	@Override
	public List<Person> getShortestPath(String user1, String user2) {
		//Person user1 = new Person(user1);
		//Person user1 = new Person(user1);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Graph> getConnectedComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromFile(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveToFile(File file) {
		// TODO Auto-generated method stub
		
	}

	  //main method for testing purposes 
    public static void main (String [] args) {
    	SocialNetwork socialNetwork = new SocialNetwork();
    	Person wally = new Person("wally");
    	Person jack = new Person("jack");
    	
    	
    	
    	socialNetwork.graph.addNode(wally);
    	socialNetwork.graph.addNode(jack);

    	socialNetwork.graph.addEdge(wally, jack);
    	
    	System.out.println(socialNetwork.graph);
    	
    	socialNetwork.graph.removeEdge(jack, wally);
    	
    	//System.out.println(socialNetwork.graph.order());
       	//System.out.println(socialNetwork.graph.size());
    	System.out.print(socialNetwork.graph);
    	
    }
    
}
