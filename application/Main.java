package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import application.SocialNetwork;
import application.Person;

/***************************************************************************************************
 *
 * @author  Dan Gerstl, Cecelia Peterson, Drew Zimmerman, Andrew Irvine
 *
 * @version 1.0
 *          <p>
 *
 *          File: Main.java
 *          <p>
 *
 *          Date: December 2, 2019
 *          <p>
 *
 *          Purpose: aTeam p2 - GUI
 *
 *          Description: Creates a GUI as a mockup for our aTeam design and fills it with hardcoded
 *          data to provide an example for what it will look like when the data structure is
 *          implemented.
 *
 *          Comment:
 *
 ***************************************************************************************************/

public class Main extends Application {

  /*** Class Constants ***/

  final double CIRCLE_HEIGHT = 50.0;
  final double CIRCLE_WIDTH = 50.0;
  final double CIRCLE_MID = CIRCLE_HEIGHT / 2;
  final double START_X = -CIRCLE_WIDTH / 2;
  final double START_Y = -CIRCLE_HEIGHT / 2;
  final double FRIEND_X_OFFSET = 125.0;
  final double FRIEND_Y_OFFSET = 125.0;
  final double CENTERING_OFFSET_X = -10.0;
  final double CENTERING_OFFSET_Y = 4.0;
  final double CANVAS_X_SIZE = 350;
  final double CANVAS_Y_SIZE = 350;
  
  final ToggleGroup tGroup = new ToggleGroup();

  /*** Class Variables ***/

  SocialNetwork sn = new SocialNetwork();

  ListView<String> lvFriends;

  Canvas canvas = new Canvas(CANVAS_X_SIZE, CANVAS_Y_SIZE);

  GraphicsContext gc = canvas.getGraphicsContext2D();
  
  ComboBox<String> c1;
  ComboBox<String> c2;
  
  RadioButton rb1;
  RadioButton rb2;
  RadioButton rb3;
  
  Label lblRadioChoice;

  @Override
  public void start(Stage primaryStage) {
    try {

      /*** Local Variables ***/

      BorderPane root = new BorderPane();

      VBox centerBox = new VBox();

      HBox topPanel = new HBox();
      HBox bottomPanel = new HBox();
      
      /*** Create top and bottom panels ***/

      topPanel = createTopPanel();
      bottomPanel = createBottomPanel();

      /*** Add components to centerBox ***/

      centerBox.getChildren().add(createCanvasPane());
      centerBox.getChildren().add(createButtonPane());

      /*** Add components to root ***/

      root.setTop(topPanel);
      root.setCenter(centerBox);
      root.setBottom(bottomPanel);

      /*** Set scene ***/

      Scene scene = new Scene(root, 725, 550);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      /*** Set Stage and show ***/

      primaryStage.setTitle("Network Visualizer");
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*** Panel creation methods ***/

  private HBox createCanvasPane() {

    /*** Local Variables ***/

    HBox mainBox = new HBox();

    lblRadioChoice = new Label("All friends (default)");

    VBox rightBox = new VBox();

    lvFriends = new ListView<String>();
    
    /*** Add EventHandler ***/
    
    lvFriends.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    		clickFriendListBox();
        }
    });
    
    lvFriends.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	public void handle (MouseEvent e) {

    	}
    });

    /*** HARDCODED DATA FOR EXAMPLE ***/

    ObservableList<String> friendsList =
        FXCollections.observableArrayList("Friend 1", "Friend 2", "Friend 3", "Friend 4");

    lvFriends.setItems(friendsList);

    /*** Add components to VBox ***/

    rightBox.getChildren().add(lblRadioChoice);
    rightBox.getChildren().add(lvFriends);

    /*** Set padding ***/

    mainBox.setPadding(new Insets(15, 15, 15, 15));
    mainBox.setSpacing(130);
    
    /*** Draw canvas border ***/
    
    drawCanvasBorder();

    /*** Add EXAMPLE FRIENDS ***/

    // drawExampleFriends(CANVAS_X_SIZE, CANVAS_Y_SIZE);

     //drawFriends("USER");

    //drawMutualFriends("USER1", "USER2");

    /*** Add components to main pane ***/

    mainBox.getChildren().add(canvas);
    mainBox.getChildren().add(rightBox);

    return mainBox;
  }

  private HBox createButtonPane() {

    /*** Local Constants ***/

    final double BUTTON_HEIGHT = 20.0;
    final double BUTTON_WIDTH = 90.0;
    final double BUTTON_SPACING = 10.0;

    /*** Local Variables ***/

    HBox buttonPane = new HBox();

    ArrayList<Button> buttonList = new ArrayList<Button>();

    Button btnClear   = new Button("Clear"   );
    Button btnNewUser = new Button("New User");
    Button btnUndo    = new Button("Undo"    );
    Button btnRedo    = new Button("Redo"    );
    Button btnLoad    = new Button("Load"    );
    Button btnExport  = new Button("Export"  );
    Button btnExit    = new Button("Exit"    );

    /*** Add buttons to Array ***/

    buttonList.add(btnClear  );
    buttonList.add(btnNewUser);
    buttonList.add(btnUndo   );
    buttonList.add(btnRedo   );
    buttonList.add(btnLoad   );
    buttonList.add(btnExport );
    buttonList.add(btnExit   );


    /*** Set spacing for buttons ***/

    buttonPane.setPadding(new Insets(0, 15, 0, 15));
    buttonPane.setSpacing(BUTTON_SPACING);

    /*** Set button size and add to pane ***/

    for (Button b : buttonList) {

      b.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

      buttonPane.getChildren().add(b);
    }

    clickClear(btnClear);
    clickNewUser(btnNewUser);
    clickLoad(btnLoad);
    clickExport(btnExport);
    clickExit(btnExit);


    return buttonPane;
  }

  private HBox createTopPanel() {

    HBox topPanel = new HBox();

    // instantiate radio buttons for left-most feature
    rb1 = new RadioButton("Show all friends");
    rb2 = new RadioButton("Show mutual friends");
    rb3 = new RadioButton("Show shortest path");

    VBox tGroupContainer = new VBox(rb1, rb2, rb3);

    rb1.setSelected(true);
    rb1.setToggleGroup(tGroup);
    rb2.setToggleGroup(tGroup);
    rb3.setToggleGroup(tGroup);

    // create combo box for main profile
    VBox v1 = new VBox();
    Label l1 = new Label("Main Profile");
    ObservableList<String> users = FXCollections.observableArrayList("User 1", "User 2", "User 3");
    c1 = new ComboBox<String>(users);
    v1.getChildren().addAll(l1, c1);

    // create combo box for friend
    VBox v2 = new VBox();
    Label l2 = new Label("Friend");
    ObservableList<String> friends =
        FXCollections.observableArrayList("Friend 1", "Friend 2", "Friend 3");
    c2 = new ComboBox<String>(friends);
    v2.getChildren().addAll(l2, c2);
    c2.setDisable(true);

    // create button for remove user and remove friendship
    VBox v3 = new VBox();
    Button btnRmUser = new Button("Remove User");
    v3.getChildren().add(btnRmUser);
    v3.snappedBottomInset();
    VBox v4 = new VBox();
    Button btnRmFriend = new Button("Remove Friendship");
    v4.getChildren().add(btnRmFriend);
    v4.snappedBottomInset();

    btnRmUser.setPrefSize(150.0, 40.0);
    btnRmFriend.setPrefSize(150.0, 40.0);

    // add all nodes to top panel
    topPanel.getChildren().addAll(tGroupContainer, v1, v2, btnRmUser, btnRmFriend);
    topPanel.setSpacing(20);

    topPanel.setPadding(new Insets(15, 15, 0, 15));
    
    /*** Add EventHandlers ***/

    c1.setOnAction(new EventHandler<ActionEvent>() {
    	public void handle (ActionEvent e) {
    		clickMainComboBox();
    	}
    });
    
    c2.setOnAction(new EventHandler<ActionEvent>() {
    	public void handle (ActionEvent e) {
    		clickFriendComboBox();
    	}
    });
    
    tGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        public void changed(ObservableValue<? extends Toggle> ov,
            Toggle old_toggle, Toggle new_toggle) {
          if (tGroup.getSelectedToggle() != null) {
        	  clickRadioButton();
          }
        }
      });

    clickRemoveUser(btnRmUser);
    clickRemoveFriendship(btnRmFriend);

    return topPanel;

  }

  private HBox createBottomPanel() {

    HBox bottomPanel = new HBox();

    // create variable for previous action
    String prevAction = "Last Action Taken";
    Label lastAction = new Label(prevAction);

    // create variable for number of current users
    int currentUsers = 4;
    Label userCount = new Label("Total Current Users: " + currentUsers);

    bottomPanel.getChildren().addAll(lastAction, userCount);
    bottomPanel.setSpacing(440);

    bottomPanel.setPadding(new Insets(15, 15, 15, 15));

    return bottomPanel;

  }

  /*** Canvas friend drawing method ***/

  private void drawExampleFriends(double x, double y) {

    /*** Local Constants ***/

    final double CIRCLE_HEIGHT = 50.0;
    final double CIRCLE_WIDTH = 50.0;
    final double CIRCLE_MID = CIRCLE_HEIGHT / 2;
    final double START_X = 0.0;
    final double START_Y = 0.0;
    final double FRIEND_X_OFFSET = 75.0;
    final double FRIEND_Y_OFFSET = 75.0;
    final double CENTERING_OFFSET = 4.0;

    /*** Local Variables ***/

    double centerX = x / 2.0;
    double centerY = y / 2.0;

    /*** Set drawing properties ***/

    gc.setFill(Color.CRIMSON);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1);

    /*** Move to center of canvas ***/

    gc.translate(centerX, centerY);

    /*** Draw main user ***/

    gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

    /*** Label main user ***/

    gc.strokeText("Current", CENTERING_OFFSET, CIRCLE_MID + CENTERING_OFFSET);

    /*** Set color for friends ***/

    gc.setFill(Color.INDIANRED);

    /*** Draw vertical friends ***/

    gc.fillOval(START_X, START_Y - FRIEND_Y_OFFSET, CIRCLE_WIDTH, CIRCLE_HEIGHT);
    gc.fillOval(START_X, START_Y + FRIEND_Y_OFFSET, CIRCLE_WIDTH, CIRCLE_HEIGHT);

    /*** Label vertical friends ***/

    gc.strokeText("Friend 1", CENTERING_OFFSET, CIRCLE_MID - FRIEND_Y_OFFSET + CENTERING_OFFSET);
    gc.strokeText("Friend 3", CENTERING_OFFSET, CIRCLE_MID + FRIEND_Y_OFFSET + CENTERING_OFFSET);

    /*** Draw lines to vertical friends ***/

    gc.strokeLine(CIRCLE_MID, 0, CIRCLE_MID, CIRCLE_HEIGHT - FRIEND_Y_OFFSET);
    gc.strokeLine(CIRCLE_MID, CIRCLE_HEIGHT, CIRCLE_MID, FRIEND_Y_OFFSET);

    /*** Draw horizontal friends ***/

    gc.fillOval(START_X - FRIEND_X_OFFSET, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);
    gc.fillOval(START_X + FRIEND_X_OFFSET, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

    /*** Label horizontal friends ***/

    gc.strokeText("Friend 2", -FRIEND_X_OFFSET + CENTERING_OFFSET, CIRCLE_MID + CENTERING_OFFSET);
    gc.strokeText("Friend 4", FRIEND_X_OFFSET + CENTERING_OFFSET, CIRCLE_MID + CENTERING_OFFSET);

    /*** Draw lines to horizontal friends ***/

    gc.strokeLine(0, CIRCLE_HEIGHT / 2, CIRCLE_WIDTH - FRIEND_X_OFFSET, CIRCLE_HEIGHT / 2);
    gc.strokeLine(CIRCLE_WIDTH, CIRCLE_HEIGHT / 2, FRIEND_X_OFFSET, CIRCLE_HEIGHT / 2);
  }

  private void drawFriends(String user) {

    /*** Local Variables ***/

    double centerX = CANVAS_X_SIZE / 2.0;
    double centerY = CANVAS_Y_SIZE / 2.0;

    Set<Person> friendSet;

    double rotation;

    /*** Clear any existing data from canvas ***/

    gc.clearRect(0, 0, CANVAS_X_SIZE, CANVAS_Y_SIZE);

    /*** Set drawing properties ***/

    gc.setFill(Color.CRIMSON);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1);

    /*** Draw border ***/

    drawCanvasBorder();

    // /*** Draw lines to find center of canvas ***/
    //
    // gc.strokeLine(x / 2, 0, x/2, y);
    // gc.strokeLine(0, y/2, x, y/2);

    /*** Move to center of canvas ***/

    gc.translate(centerX, centerY);

    /*** Draw main user ***/

    gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

    /*** Label main user ***/

    gc.strokeText(user, CENTERING_OFFSET_X, CENTERING_OFFSET_Y);

    /*** Set color for friends ***/

    gc.setFill(Color.INDIANRED);

    /*** Get friend list ***/ //-----------------------------------------------------------------------------

     friendSet = sn.getFriends(user);
     
    /*** Convert set to list for displaying the names ***/
     
     List<Person> friendList = new ArrayList<Person>(friendSet); 

    /*** Determine rotation based on friend list size ***/
     
     int listSize = friendList.size();

     if (listSize > 12) {    
    	 
    	 rotation = Math.toDegrees((2 * Math.PI) / 12);
    
     //TODO: update user stating that there are too many to display on canvas
     } else {
    	 rotation = Math.toDegrees((2 * Math.PI) / listSize);
     }

     for (int i = 0; i < listSize; i++) {

      /*** Draw friend circle ***/

      gc.setFill(Color.INDIANRED);
      gc.fillOval(START_X, START_Y - FRIEND_Y_OFFSET, CIRCLE_WIDTH, CIRCLE_HEIGHT);
      gc.strokeLine(START_X + CIRCLE_MID, START_Y, 0, -FRIEND_Y_OFFSET + CIRCLE_MID);

      /*** Draw friend name ***/

      gc.setFill(Color.BLACK);
      gc.strokeText(friendList.get(i).getName(), CENTERING_OFFSET_X, -FRIEND_Y_OFFSET + CENTERING_OFFSET_Y); 

      /*** Rotate transform ***/

      gc.rotate(rotation);

    }
    
    if (listSize == 0) {
        gc.strokeText("No friends", CENTERING_OFFSET_X * 2 - 4, -FRIEND_Y_OFFSET + CENTERING_OFFSET_Y);
    }
  }

  private void drawMutualFriends(String user1, String user2) {

    /*** Local Variables ***/

    double leftMidX  = CANVAS_X_SIZE / 4.0;
    double rightMidX = (3 * CANVAS_X_SIZE) / 4.0;
    double centerY   = CANVAS_Y_SIZE / 2.0;
    double centerX   = CANVAS_X_SIZE / 2.0;
    double spacing   = 0.0;
    
    Set<Person> friendSet;

    /*** Clear any existing data from canvas ***/

    gc.clearRect(0, 0, CANVAS_X_SIZE, CANVAS_Y_SIZE);

    /*** Set drawing properties ***/

    gc.setFill(Color.CRIMSON);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1);

    /*** Draw border ***/

    drawCanvasBorder();

//    /*** Draw lines to find midpoints ***/
//
//    gc.strokeLine(leftMidX, 0, leftMidX, CANVAS_Y_SIZE);
//    gc.strokeLine(rightMidX, 0, rightMidX, CANVAS_Y_SIZE);
//    gc.strokeLine(0, CANVAS_Y_SIZE / 2, CANVAS_X_SIZE, CANVAS_Y_SIZE / 2);
//    gc.strokeLine(CANVAS_X_SIZE / 2, 0, CANVAS_X_SIZE / 2, CANVAS_Y_SIZE);

    /*** Move to left half center ***/

    gc.translate(leftMidX, centerY);

    /*** Draw left user ***/

    gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);
    
    c1.getSelectionModel().selectFirst();
    c2.getSelectionModel().selectFirst();
    
    /*** Label left user ***/
    
    gc.strokeText(c1.getValue().toString(), -CIRCLE_MID / 2, 4);

    /*** Move to right half center and draw right user ***/

    gc.translate(rightMidX - leftMidX, 0);
    gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);
    
    /*** Label right user ***/
    
    gc.strokeText(c2.getValue().toString(), -CIRCLE_MID / 2, 4);
    
    /*** Change color for friends ***/
    
    gc.setFill(Color.PURPLE);
    
    /*** Move to middle ***/

    gc.translate(-leftMidX, 0);
    
    /*** Get mutual friends ***/
    
    friendSet = sn.getMutualFriends(user1, user2);
    
    /*** Convert set to list for displaying the names ***/
    
    List<Person> friendList = new ArrayList<Person>(friendSet);    
    
    /*** Determine spacing based on friend list size ***/

     if (friendSet.size() > 12) {
    
     spacing = CANVAS_Y_SIZE / 12;
    
     //TODO: update user stating that there are too many to display on canvas
     } else {
     spacing = CANVAS_Y_SIZE / friendSet.size();
     }

    int listSize = friendList.size();
    
    spacing = CANVAS_Y_SIZE / listSize;
    
    if (listSize > 1 && listSize % 2 == 0) { //even number of friends
    	
    	/*** Translate up for spacing on non-centered friends ***/
    	
    	double shift = -spacing / 2;
    	
    	gc.translate(0, shift);    	
    	
        for (int i = 0; i < listSize; i++) {
        	
        	spacing = spacing * - 1;
        	
        	gc.translate(0, spacing * i);
        	gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);
        	
        	/*** Draw connecting lines ***/
        	
        	if (i % 2 == 1) { // Bottom friends
            	gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, 
            				 (-spacing * i) / 2.0); //left lines
            	
            	gc.strokeLine(CIRCLE_MID , 0,  leftMidX - CIRCLE_MID, 
            			     (-spacing * i) / 2.0); //right lines
        	} else { // Top friends
        		gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, (-spacing * i) / 2.0 - shift); //left
        		gc.strokeLine( CIRCLE_MID, 0,  leftMidX - CIRCLE_MID, (-spacing * i) / 2.0 - shift); //right
        	}
        	
        	/*** Draw name ***/
        	
        	gc.strokeText(friendList.get(i).getName(), START_X + 4 + CIRCLE_MID / 2, START_Y + CIRCLE_MID + 4);
        	
        }
        
    } else if (listSize > 1 && listSize % 2 == 1) { //odd number of friends
    	
        for (int i = 0; i < listSize; i++) {
        	
        	spacing = spacing * - 1.0;        	
        	
        	/*** Draw circles for friends ***/
        	
        	gc.translate(0, spacing * i);
        	
        	gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT); 
        	      	
        	/*** Draw connecting lines ***/
        	
        	if (i % 2 == 1) {
            	gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, 
            				((-spacing * i) / 2.0) - CIRCLE_MID); //left lines
            	
            	gc.strokeLine(CIRCLE_MID , 0,  leftMidX - CIRCLE_MID, 
            			    ((-spacing * i) / 2.0) - CIRCLE_MID); //right lines
        	} else {
        		gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, (-spacing * i) / 2.0); //left
        		gc.strokeLine( CIRCLE_MID, 0,  leftMidX - CIRCLE_MID, (-spacing * i) / 2.0); //right
        	}
        	
        	/*** Draw name ***/
        	
        	gc.strokeText(friendList.get(i).getName(), START_X + 4 + CIRCLE_MID / 2, START_Y + CIRCLE_MID + 4); 
        }
        
    } else if (listSize == 1) {
    	
    	/*** Add single friend ***/
    	
    	gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT); 
    	
    	/*** Draw connecting lines ***/
    	
		gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, 0); //left
		gc.strokeLine( CIRCLE_MID, 0,  leftMidX - CIRCLE_MID, 0); //right
    	
    	/*** Draw name ***/
    	
    	gc.strokeText(friendList.get(0).getName() , CENTERING_OFFSET_X, CENTERING_OFFSET_Y);

    } else {
    	gc.strokeText("No mutual friends", -CIRCLE_WIDTH + 4, CENTERING_OFFSET_Y);
    }
  }

  private void drawCanvasBorder() {

    /*** Draw border ***/

    gc.strokeLine(0, 0, CANVAS_X_SIZE, 0);
    gc.strokeLine(CANVAS_X_SIZE, 0, CANVAS_X_SIZE, CANVAS_Y_SIZE);
    gc.strokeLine(CANVAS_X_SIZE, CANVAS_Y_SIZE, 0, CANVAS_Y_SIZE);
    gc.strokeLine(0, CANVAS_Y_SIZE, 0, 0);
  }

  private void displayFriendsOfOneUser(String user) {

    /*** Local Variables ***/

    ObservableList<String> friendsList = FXCollections.observableArrayList();

    Set<Person> friendSet;

    /*** Clear any existing data ***/

    lvFriends.getItems().clear();

    /*** Get friends of user ***/

    friendSet = sn.getFriends(user);

    /*** Add friends from set to list ***/

    for (Person p : friendSet) {
      friendsList.add(p.getName());
    }

    /*** Add friendsList to listView ****/

    lvFriends.setItems(friendsList);
  }
  
  private void displayFriendsTwoUsers(String mainUser, String secondUser) {

	    /*** Local Variables ***/

	    ObservableList<String> friendsList = FXCollections.observableArrayList();

	    Set<Person> mutualFriends;

	    /*** Clear any existing data ***/

	    lvFriends.getItems().clear();

	    /*** Get friends of user ***/

	    mutualFriends = sn.getMutualFriends(mainUser, secondUser);

	    /*** Add friends from set to list ***/

	    for (Person p : mutualFriends) {
	      friendsList.add(p.getName());
	    }

	    /*** Add friendsList to listView ****/

	    lvFriends.setItems(friendsList);
	  }
  
  private void updateMainComboBox() {
	  
	  /*** Local Variables ***/	  
	  
	  List<String> userArray = sn.getAllUsers();
	  ObservableList<String> userList = FXCollections.observableArrayList();
	  
	  /*** Add all users to ObservableList ***/
	  
	  for (String user : userArray) {
		  userList.add(user);
	  }
	  
	  /*** Clear current data ***/
	  
	  c1.getItems().clear();
	  
	  /*** Update comboBox with new user data ***/
	  
	  c1.setItems(userList);
  }
  
  private void updateFriendComboBox(String user) {
	  
	  /*** Local Variables ***/	  
	  
	  Set<Person> userSet = sn.getFriends(user);
	  ObservableList<String> userList = FXCollections.observableArrayList();
	  
	  /*** Convert set to list ***/
	  
	  List<Person> userArray = new ArrayList<Person>(userSet);
	  
	  /*** Add all users to ObservableList ***/
	  
	  for (Person p : userArray) {
		  userList.add(p.getName());
	  }
	  
	  /*** Clear current data ***/
	  
	  c2.getItems().clear();
	  
	  /*** Update comboBox with new user data ***/
	  
	  c2.setItems(userList);
  }


  private void clickClear(Button Clear) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        lvFriends.getItems().clear();
      }
    };
    Clear.setOnAction(event);

  }

  private void clickNewUser(Button newUser) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Stage stage = (Stage) newUser.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        Label label = new Label("");
        TextField field = new TextField("Enter new user");


        VBox box = new VBox();
        box.getChildren().addAll(label, field);
        box.setSpacing(10);

        Button btnSubmit = new Button("Submit");
        box.getChildren().add(btnSubmit);
        Scene dialogScene = new Scene(box, 200, 100);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {

            sn.addUser(field.getText());
            // updateFriendListBox(field.getText());
            dialog.close();
          }
        };
        btnSubmit.setOnAction(event);

        dialog.setScene(dialogScene);
        dialog.show();
      }
    };
    newUser.setOnAction(event);
  }

  private void clickUndo() {

  }

  private void clickRedo() {

  }


  private void clickLoad(Button load) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");

        Stage stage = (Stage) load.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null)
          sn.loadFromFile(file);
      }
    };

    load.setOnAction(event);
  }

  private void clickExport(Button export) {

  }

  private void clickExit(Button exit) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
      }
    };

    exit.setOnAction(event);
  }

  private void clickRemoveUser(Button removeUser) {
   
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Stage stage = (Stage) removeUser.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        Label label = new Label("Click a user to remove:");
        // users will have to be a list of all the users in the network.
        ObservableList<String> users1 =
            FXCollections.observableArrayList("User 1", "User 2", "User 3");
        ComboBox comboBox1 = new ComboBox(users1);

        BorderPane root = new BorderPane();
        HBox box = new HBox();
        HBox box2 = new HBox();

        box.getChildren().add(label);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 20, 20, 20));

        Button btnSubmit = new Button("Submit");
        box2.getChildren().addAll(comboBox1, btnSubmit);
        box2.setSpacing(10);
        box2.setPadding(new Insets(0, 0, 0, 20));

        root.setTop(box);
        root.setCenter(box2);


        Scene dialogScene = new Scene(root, 200, 100);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            
            sn.removeUser((String)comboBox1.getValue());
            dialog.close();
          }
        };
        btnSubmit.setOnAction(event);

        dialog.setScene(dialogScene);
        dialog.show();
      }
    };
    removeUser.setOnAction(event);
  }

  private void clickRemoveFriendship(Button rmFriendship) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Stage stage = (Stage) rmFriendship.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        Label label = new Label("Enter two users to remove friendship:");
        // users will have to be a list of all the users in the network.
        ObservableList<String> users1 =
            FXCollections.observableArrayList("User 1", "User 2", "User 3");
        ComboBox comboBox1 = new ComboBox(users1);
       
        // users will have to be a list of all the users in the network.
        ObservableList<String> users2 =
            FXCollections.observableArrayList("User 1", "User 2", "User 3");
        ComboBox comboBox2 = new ComboBox(users2);

        BorderPane root = new BorderPane();
        HBox box = new HBox();
        HBox box2 = new HBox();

        box.getChildren().add(label);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 20, 20, 20));

        Button btnSubmit = new Button("Submit");
        box2.getChildren().addAll(comboBox1, comboBox2, btnSubmit);
        box2.setSpacing(10);
        box2.setPadding(new Insets(0, 0, 0, 20));

        root.setTop(box);
        root.setCenter(box2);


        Scene dialogScene = new Scene(root, 290, 100);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            
            sn.removeFriends((String)comboBox1.getValue(), (String)comboBox2.getValue());
            dialog.close();
          }
        };
        btnSubmit.setOnAction(event);

        dialog.setScene(dialogScene);
        dialog.show();
      }
    };
    rmFriendship.setOnAction(event);
  }
  
  private void clickMainComboBox() {
	  
	  /*** Local Variables ***/
	  
	  String selection = c1.getValue();
	  
	  if (rb1.isSelected()) {
		  
		  /*** Disable Friend comboBox ***/
		  
		  c2.setDisable(true);
		  
		  /*** Clear current data ***/
		  
		  clearGUI();
		  
		  /*** Update listBox with all friends ***/
		  
		  displayFriendsOfOneUser(selection);
		  
		  /*** Update canvas with all friends ***/
		  
		  drawFriends(selection);
		  
	  } else if (rb2.isSelected()) {
		  
		  /*** Enable Friend comboBox ***/
		  
		  c2.setDisable(false);
		  
	  } else if (rb3.isSelected()) {
		  
		  /*** Enable Friend comboBox ***/
		  
		  c2.setDisable(false);
	  }
  }
  
  private void clickFriendComboBox() {
	  
	  /*** Local Variables ***/
	  
	  String mainSelection   = "";
	  String friendSelection = "";
	  
	  boolean validSelections = false;
	  
	  /*** Check that valid selections are made ***/
	  
	  if (c1.getValue() != null) {
		  mainSelection = c1.getValue();
		  
		  if (c2.getValue() != null) {
			  friendSelection = c2.getValue();
			  
			  validSelections = true;
		  }
	  }
	  
	  if (validSelections) {	  
		  if (rb2.isSelected()) {		  
			  
			  /*** Clear current data ***/
			  
			  clearGUI();
			  
			  /*** Update listBox with all friends ***/
			  
			  displayFriendsTwoUsers(mainSelection, friendSelection);
			  
			  /*** Update canvas with all friends ***/
			  
			  drawMutualFriends(mainSelection, friendSelection);
			  
		  } else if (rb3.isSelected()) {
			  displayShortestPath(mainSelection, friendSelection);
		  }
	  }	  
  }

  private void clickFriendListBox() {
	  
	  /*** Local Variables ***/
	  
	  String selection = lvFriends.getSelectionModel().getSelectedItem();
	  
	  /*** Disable Friend comboBox ***/
	  
	  c2.setDisable(true);
	  
	  /*** Set radioButton selection to all friends ***/
	  
	  rb1.setSelected(true);
	  
	  /*** Clear current data ***/
	  
	  clearGUI();
	  
	  /*** Update listBox with all friends ***/
	  
	  displayFriendsOfOneUser(selection);
	  
	  /*** Update canvas with all friends ***/
	  
	  drawFriends(selection);
  }
  
  private void clickRadioButton() {
	  
	  /*** Local Variables ***/
	  
	  RadioButton selected = (RadioButton) tGroup.getSelectedToggle();
	  
	  /*** Enable/disable second comboBox based on selected radioButton and update listbox label***/
	  
	  if (selected.equals(rb1)) {
		  
		  c2.setDisable(true);
		  
		  lblRadioChoice.setText("All friends");
		  
	  } else if (selected.equals(rb2)) {
		  
		  c2.setDisable(false);
		  
		  lblRadioChoice.setText("Mutual friends");
		  
	  }	else if (selected.equals(rb3)) {
		  
		  c2.setDisable(false);
		  
		  lblRadioChoice.setText("Shortest path");		  
	  }
  }
  
  private void clearGUI() {
	  
  }
  
  private void displayShortestPath(String main, String friend) {
	  //TODO: need to implement this yet.
  }
	
	/*** Application ***/
	
	public static void main(String[] args) {
		launch(args);
	}
}
