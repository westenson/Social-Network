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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFriends(String friend1, String friend2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUser(String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Person> getFriend(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Person> getMutualFriends(String friend1, String friend2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getShortestPath(String user1, String user2) {
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
