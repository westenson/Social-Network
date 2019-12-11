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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import application.SocialNetwork;
import application.Person;

/***************************************************************************************************
 *
 * @author Dan Gerstl, Cecelia Peterson, Drew Zimmerman, Andrew Irvine
 *
 * @version 2.0
 *          <p>
 *
 *          File: Main.java
 *          <p>
 *
 *          Date: December 11, 2019
 *          <p>
 *
 *          Purpose: aTeam p3 - Final Project
 *
 *          Description: Creates a GUI for a user to interact with the underlying data structure
 *          that models a Social Network using a GraphADT.
 *
 *          Comment: Redo and Undo buttons are not functional
 *
 ***************************************************************************************************/

/**
 * @author Dan
 *
 */
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

  final int OVERFLOW_LIMIT = 12;

  final ToggleGroup tGroup = new ToggleGroup();

  /*** Class Variables ***/

  SocialNetwork sn = new SocialNetwork();

  ListView<String> lvFriends;

  Canvas canvas = new Canvas(CANVAS_X_SIZE, CANVAS_Y_SIZE);
  Canvas border = new Canvas(CANVAS_X_SIZE, CANVAS_Y_SIZE);

  GraphicsContext gc = canvas.getGraphicsContext2D();
  GraphicsContext gcBorder = border.getGraphicsContext2D();

  ComboBox<String> c1;
  ComboBox<String> c2;

  RadioButton rb1;
  RadioButton rb2;
  RadioButton rb3;

  Label lblRadioChoice;
  Label lblUserOverflow;
  Label lblLastAction;
  Label lblGroupCount;
  Label lblUsersCount;

  @Override
  public void start(Stage primaryStage) {
    try {

      /*** Local Variables ***/

      BorderPane root = new BorderPane();

      VBox centerBox = new VBox();

      HBox topPanel = new HBox();

      BorderPane bottomPanel = new BorderPane();

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

  /***** PANEL CREATION METHODS *****/

  /**
   * Creates an HBox containing the canvas and listView that displays the users and friendships
   * contained in the Social Network and the associated labels
   * 
   * @return mainBox - an HBox containing a Canvas and ListView
   */
  private HBox createCanvasPane() {

    /*** Local Variables ***/

    HBox mainBox = new HBox();

    lblRadioChoice = new Label("All friends");
    lblUserOverflow =
        new Label("Unable to display all relationships. Displaying first " + OVERFLOW_LIMIT);

    VBox rightBox = new VBox();
    VBox leftBox = new VBox();

    lvFriends = new ListView<String>();

    Pane canvasPane = new Pane();

    /*** Add EventHandler ***/

    lvFriends.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        clickFriendListBox();
      }
    });

    /*** Hide overflow label ***/

    lblUserOverflow.setVisible(false);

    // /*** HARDCODED DATA FOR TESTING ***/
    //
    // ObservableList<String> friendsList =
    // FXCollections.observableArrayList("Friend 1", "Friend 2", "Friend 3", "Friend 4");
    //
    // lvFriends.setItems(friendsList);

    /*** Add components to right VBox ***/

    rightBox.getChildren().add(lblRadioChoice);
    rightBox.getChildren().add(lvFriends);

    /*** Set padding ***/

    mainBox.setPadding(new Insets(15, 15, 15, 15));
    mainBox.setSpacing(130);

    /*** Draw canvas border ***/

    drawCanvasBorder();

    /*** Add EXAMPLE FRIENDS ***/

    // drawExampleFriends(CANVAS_X_SIZE, CANVAS_Y_SIZE);

    // drawFriends("USER");

    // drawMutualFriends("USER1", "USER2");

    /*** Add canvases to pane ***/

    canvasPane.getChildren().add(border);
    canvasPane.getChildren().add(canvas);

    /*** Add components to left VBox ***/

    leftBox.getChildren().addAll(canvasPane, lblUserOverflow);

    /*** Add components to main pane ***/

    mainBox.getChildren().add(leftBox);
    mainBox.getChildren().add(rightBox);

    return mainBox;
  }

  /**
   * Creates an HBox that contains buttons that allows the user to interact with the modeled Social
   * Network.
   * 
   * @return buttonPane - an HBox containing a row of buttons for user interaction
   */
  private HBox createButtonPane() {

    /*** Local Constants ***/

    final double BUTTON_HEIGHT = 20.0;
    final double BUTTON_WIDTH = 100.0;
    final double BUTTON_SPACING = 10.0;

    /*** Local Variables ***/

    HBox buttonPane = new HBox();

    ArrayList<Button> buttonList = new ArrayList<Button>();

    Button btnClear = new Button("Clear");
    Button btnNewUser = new Button("New User");
    Button btnAddFrnd = new Button("Add Friendship");
    Button btnRedo = new Button("Redo");
    Button btnLoad = new Button("Load");
    Button btnExport = new Button("Export");
    Button btnExit = new Button("Exit");

    /*** Add buttons to Array ***/

    buttonList.add(btnClear);
    buttonList.add(btnNewUser);
    buttonList.add(btnAddFrnd);
   // buttonList.add(btnRedo);
    buttonList.add(btnLoad);
    buttonList.add(btnExport);
    buttonList.add(btnExit);


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
    clickAddFriendship(btnAddFrnd);


    return buttonPane;
  }

  /**
   * Creates an HBox that contains RadioButtons, ComboBoxes, and Buttons that allows the user to
   * interact with the modeled Social Network.
   * 
   * @return topPanel - an HBox multiple Objects for user interaction
   */

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
    // ObservableList<String> users = FXCollections.observableArrayList(sn.getAllUsers());
    ObservableList<String> users = FXCollections.observableArrayList("          ");
    c1 = new ComboBox<String>(users);
    v1.getChildren().addAll(l1, c1);

    // create combo box for friend
    VBox v2 = new VBox();
    Label l2 = new Label("Friend");
    ObservableList<String> friends = FXCollections.observableArrayList("          ");
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
      public void handle(ActionEvent e) {
        clickMainComboBox();
      }
    });

    c2.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        clickFriendComboBox();
      }
    });

    tGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle,
          Toggle new_toggle) {
        if (tGroup.getSelectedToggle() != null) {
          clickRadioButton();
        }
      }
    });

    clickRemoveUser(btnRmUser);
    clickRemoveFriendship(btnRmFriend);

    return topPanel;

  }

  /**
   * Creates a BorderPane that contains labels that track the last action taken and the current
   * number of users in the Social Network
   * 
   * @return bottomPanel - BorderPane containing informational labels
   */

  private BorderPane createBottomPanel() {

    BorderPane bottomPanel = new BorderPane();

    // create variable for previous action
    String prevAction = "Program loaded";
    lblLastAction = new Label(prevAction);

    // create variable for number of current users
    int currentGroups = 0;
    int currentUsers = 0;
    lblGroupCount = new Label("Group count: " + currentGroups);
    lblUsersCount = new Label("User count: " + currentUsers);

    bottomPanel.setLeft(lblLastAction);
    VBox vbox = new VBox();

    vbox.getChildren().addAll(lblGroupCount, lblUsersCount);
    bottomPanel.setRight(vbox);
    // bottomPanel.setRight(lblGroupCount);
    // bottomPanel.setRight(lblUsersCount);

    bottomPanel.setPadding(new Insets(15, 15, 15, 15));

    return bottomPanel;
  }

  /***** CANVAS DRAWING METHODS *****/

  private void drawExampleFriends(double x, double y) { // For testing purposes only

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
    
    double currentX = 0.0;
    double currentY = 0.0;

    int listSize = 0;

    Set<Person> friendSet;

    double rotation;

    /*** Clear Canvas ***/

    clearCanvas();

    /*** Reset overflow label ***/

    lblUserOverflow.setVisible(false);

    /*** Set drawing properties ***/

    gc.setFill(Color.CRIMSON);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1);

    // /*** Draw lines to find center of canvas ***/ //Used for orientation/testing
    //
    // gc.strokeLine(x / 2, 0, x/2, y);
    // gc.strokeLine(0, y/2, x, y/2);

    /*** Move to center of canvas ***/

    gc.translate(centerX, centerY);
    currentX += centerX;
    currentY += centerY;

    /*** Draw main user ***/

    gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

    /*** Label main user ***/

    gc.strokeText(user, CENTERING_OFFSET_X, CENTERING_OFFSET_Y);

    /*** Set color for friends ***/

    gc.setFill(Color.PURPLE);

    /*** Get friend list ***/

    friendSet = sn.getFriends(user);

    /*** Convert set to list for displaying the names ***/
    if (friendSet != null) {

      List<Person> friendList = new ArrayList<Person>(friendSet);

      /*** Determine rotation based on friend list size ***/

      listSize = friendList.size();

      if (listSize > OVERFLOW_LIMIT) {

        rotation = Math.toDegrees((2 * Math.PI) / OVERFLOW_LIMIT);

        lblUserOverflow.setVisible(true);

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
        gc.strokeText(friendList.get(i).getName(), CENTERING_OFFSET_X,
            -FRIEND_Y_OFFSET + CENTERING_OFFSET_Y);

        /*** Rotate transform ***/

        gc.rotate(rotation);
      }
    }

    if (listSize == 0) {
      gc.strokeText("No friends", CENTERING_OFFSET_X * 2 - 4,
          -FRIEND_Y_OFFSET + CENTERING_OFFSET_Y);
    }
    
    /*** Move back to origin ***/
    
    gc.translate(-currentX, -currentY);
  }

  private void drawMutualFriends(String user1, String user2) {

    /*** Local Variables ***/

    double leftMidX = CANVAS_X_SIZE / 4.0;
    double rightMidX = (3 * CANVAS_X_SIZE) / 4.0;
    double centerY = CANVAS_Y_SIZE / 2.0;
    double spacing = 0.0;
    double currentX = 0.0;
    double currentY = 0.0;

    int listSize;

    Set<Person> friendSet;

    /*** Clear any existing data from canvas ***/

    clearCanvas();
    
    /*** Reset overflow label ***/

    lblUserOverflow.setVisible(false);

    /*** Set drawing properties ***/

    gc.setFill(Color.CRIMSON);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1);

     /*** Draw lines to find midpoints ***/ //Used for orientation/testing
    
     gc.strokeLine(leftMidX, 0, leftMidX, CANVAS_Y_SIZE);
     gc.strokeLine(rightMidX, 0, rightMidX, CANVAS_Y_SIZE);
     gc.strokeLine(0, CANVAS_Y_SIZE / 2, CANVAS_X_SIZE, CANVAS_Y_SIZE / 2);
     gc.strokeLine(CANVAS_X_SIZE / 2, 0, CANVAS_X_SIZE / 2, CANVAS_Y_SIZE);

    /*** Move to left half center ***/

    gc.translate(leftMidX, centerY);
    
    currentX += leftMidX;
    currentY += centerY;

    /*** Draw left user ***/

    gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

    c1.getSelectionModel().selectFirst();
    c2.getSelectionModel().selectFirst();

    /*** Label left user ***/

    gc.strokeText(c1.getValue().toString(), -CIRCLE_MID / 2, 4);

    /*** Move to right half center and draw right user ***/

    gc.translate(rightMidX - leftMidX, 0);
    gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);
    
    currentX += rightMidX - leftMidX;

    /*** Label right user ***/

    gc.strokeText(c2.getValue().toString(), -CIRCLE_MID / 2, 4);

    /*** Change color for friends ***/

    gc.setFill(Color.PURPLE);

    /*** Move to middle ***/

    gc.translate(-leftMidX, 0);    
    currentX += -leftMidX;
    
    /*** Get mutual friends ***/

    friendSet = sn.getMutualFriends(user1, user2);

    /*** Convert set to list for displaying the names ***/

    List<Person> friendList = new ArrayList<Person>(friendSet);

    /*** Determine spacing based on friend list size ***/

    if (friendSet.size() > OVERFLOW_LIMIT) {

      spacing = CANVAS_Y_SIZE / OVERFLOW_LIMIT;

      lblUserOverflow.setVisible(false);

    } else {
      spacing = CANVAS_Y_SIZE / friendSet.size();
    }

    listSize = friendList.size();

    spacing = CANVAS_Y_SIZE / listSize;
    
    System.out.println(listSize);

    if (listSize >= 1 && listSize % 2 == 0) { // even number of friends
    	
    	System.out.println("even friends");


      /*** Translate up for spacing on non-centered friends ***/

      double shift = -spacing / 2;

      gc.translate(0, shift);
      currentY += shift;

      for (int i = 0; i < listSize; i++) {

        spacing = spacing * -1;

        gc.translate(0, spacing * i);
        gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

        currentY += (spacing * i);

        /*** Draw connecting lines ***/

        if (i % 2 == 1) { // Bottom friends
          gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, (-spacing * i) / 2.0); // left lines

          gc.strokeLine(CIRCLE_MID, 0, leftMidX - CIRCLE_MID, (-spacing * i) / 2.0); // right lines
        } else { // Top friends
          gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, (-spacing * i) / 2.0 - shift); // left
          gc.strokeLine(CIRCLE_MID, 0, leftMidX - CIRCLE_MID, (-spacing * i) / 2.0 - shift); // right
        }

        /*** Draw name ***/

        gc.strokeText(friendList.get(i).getName(), START_X + 4 + CIRCLE_MID / 2,
            START_Y + CIRCLE_MID + 4);

      }

    } else if (listSize >= 1 && listSize % 2 == 1) { // odd number of friends
    	
    	System.out.println("odd friends");


      for (int i = 0; i < listSize; i++) {

        spacing = spacing * -1.0;

        /*** Draw circles for friends ***/

        gc.translate(0, spacing * i);        
        currentY += (spacing * i);

        gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

        /*** Draw connecting lines ***/

        if (i % 2 == 1) {
          gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID,
              ((-spacing * i) / 2.0) - CIRCLE_MID); // left lines

          gc.strokeLine(CIRCLE_MID, 0, leftMidX - CIRCLE_MID, ((-spacing * i) / 2.0) - CIRCLE_MID); // right
                                                                                                    // lines
        } else {
          gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, (-spacing * i) / 2.0); // left
          gc.strokeLine(CIRCLE_MID, 0, leftMidX - CIRCLE_MID, (-spacing * i) / 2.0); // right
        }

        /*** Draw name ***/

        gc.strokeText(friendList.get(i).getName(), START_X + 4 + CIRCLE_MID / 2,
            START_Y + CIRCLE_MID + 4);
      }

//    } else if (listSize == 1 ) {
//    	
//    	System.out.println("one friend");
//
//
//      /*** Add single friend ***/
//
//      gc.fillOval(START_X, START_Y, CIRCLE_WIDTH, CIRCLE_HEIGHT);
//
//      /*** Draw connecting lines ***/
//
//      gc.strokeLine(-CIRCLE_MID, 0, -leftMidX + CIRCLE_MID, 0); // left
//      gc.strokeLine(CIRCLE_MID, 0, leftMidX - CIRCLE_MID, 0); // right
//
//      /*** Draw name ***/
//
//      gc.strokeText(friendList.get(0).getName(), CENTERING_OFFSET_X, CENTERING_OFFSET_Y);

    } else if (listSize == 0){
    	
    	System.out.println("no friends " + listSize);

      gc.strokeText("No mutual friends", -CIRCLE_WIDTH + 4, CENTERING_OFFSET_Y);
    }
    
    /*** Move back to origin ***/
    
   gc.translate(-currentX, -currentY);
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

  private void displayShortestPath(String main, String friend) {

    /*** Local Variables ***/

    List<Person> path;
    ObservableList<String> observablePath = FXCollections.observableArrayList();

    /*** Clear canvas ***/

    clearCanvas();

    /*** Check for invalid input ***/

    if (main.isEmpty() || friend.isEmpty()) {
      return;
    }

    /*** Clear friend listView ***/

    lvFriends.getItems().clear();

    /*** Get list of shortest path ***/

    path = sn.getShortestPath(main, friend);

    /*** Add list of users to observableList ***/

    for (Person p : path) {
      observablePath.add(p.getName());
    }

    /*** Update listBox label with user names ***/

    lblRadioChoice.setText("Shortest path between " + main + " and " + friend + ".");

    /*** Update listView with shortest path between two users ***/

    lvFriends.setItems(observablePath);
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
  
  public void setCentralUser(String user) {
	  updateMainComboBox();
	  
	  displayFriendsOfOneUser(user);
	  drawFriends(user);
	  
	  c1.setValue(user);
  }

  private void updateFriendComboBoxAllUsers() {

    /*** Local Variables ***/

    List<String> userArray = sn.getAllUsers();
    ObservableList<String> userList = FXCollections.observableArrayList();

    /*** Add all users to ObservableList ***/

    for (String user : userArray) {
      userList.add(user);
    }

    /*** Clear current data ***/

    c2.getItems().clear();

    /*** Update comboBox with new user data ***/

    c2.setItems(userList);
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

  /***** GUI HELPER METHODS ****/

  private void drawCanvasBorder() {

    /*** Draw border ***/

    gcBorder.strokeLine(0, 0, CANVAS_X_SIZE, 0);
    gcBorder.strokeLine(CANVAS_X_SIZE, 0, CANVAS_X_SIZE, CANVAS_Y_SIZE);
    gcBorder.strokeLine(CANVAS_X_SIZE, CANVAS_Y_SIZE, 0, CANVAS_Y_SIZE);
    gcBorder.strokeLine(0, CANVAS_Y_SIZE, 0, 0);
  }

  private void clearCanvas() {

    /*** Clear canvas contents by brute force ***/

    gc.clearRect(-CANVAS_X_SIZE, -CANVAS_Y_SIZE, CANVAS_X_SIZE * 2, CANVAS_Y_SIZE * 2);
  }
  
  private void clearAllData() {
	  sn = new SocialNetwork();
	  c1.getItems().clear();
	  c2.getItems().clear();
	  resetGUI();
  }

  private void resetGUI() {

    /*** Set radio button group to first selection ***/

    rb1.setSelected(true);

    /*** Clear comboBoxes and disable friend comboBox ***/

    c1.getSelectionModel().clearSelection();
    c2.getSelectionModel().clearSelection();
    c2.setDisable(true);

    /*** Clear canvas ***/

    clearCanvas();

    /*** Clear friends listBox ***/

    lvFriends.getItems().clear();

    /*** Reset overflow label ***/

    lblUserOverflow.setVisible(false);
  }

  /***** ACTION EVENT/CLICK METHODS *****/
  /**
   * Handles what happens when the user clicks clear.
   * 
   * @param Clear the reference to the button.
   */
  private void clickClear(Button Clear) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
    	clearAllData();

        updateLastActionAndGroupAndUserCount("Cleared all data");
      }
    };
    Clear.setOnAction(event);
  }

  /**
   * Handles what happens when the user clicks New User.
   * 
   * @param newUser reference to the new user button.
   */
  private void clickNewUser(Button newUser) {
    // When the user clicks the new user button, a pop-up will appear.
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Stage stage = (Stage) newUser.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        /*** Set title on pop-up ***/

        dialog.setTitle("Add new user");

        Label label = new Label("Enter name of user:");
        TextField field = new TextField();

        VBox vbox = new VBox();
        HBox hbox = new HBox();

        Button btnSubmit = new Button("Submit");
        Button btnCancel = new Button("Cancel");

        vbox.getChildren().addAll(label, field);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.setSpacing(10);

        btnSubmit.setPrefSize(90, 20);
        btnCancel.setPrefSize(90, 20);

        hbox.getChildren().add(btnSubmit);
        hbox.getChildren().add(btnCancel);
        hbox.setPadding(new Insets(0, 10, 0, 10));
        hbox.setSpacing(50);

        vbox.getChildren().add(hbox);

        Scene dialogScene = new Scene(vbox, 240, 100);

        EventHandler<ActionEvent> submit = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            // If the user types in valid input, add them to the network.
            if (!(field.getText().equals(""))) {
              sn.addUser(field.getText());
              updateLastActionAndGroupAndUserCount("Added new user: " + field.getText());
              updateMainComboBox();


            } else {
              // informs the user that adding user failed if input is invalid.
              dialog.close();
              Stage stage2 = (Stage) newUser.getScene().getWindow();
              final Stage dialog2 = new Stage();
              dialog2.initModality(Modality.APPLICATION_MODAL);
              dialog2.initOwner(stage2);

              dialog2.setTitle("Failed");

              VBox box3 = new VBox();
              Label label3 = new Label("Adding user failed.");
              box3.setPadding(new Insets(10, 10, 10, 50));
              box3.setSpacing(10);

              Button btnCancel2 = new Button("Cancel");
              btnCancel2.setPrefSize(90, 20);
              box3.getChildren().addAll(label3, btnCancel2);

              Scene dialogScene2 = new Scene(box3, 200, 100);

              dialog2.setScene(dialogScene2);
              dialog2.show();
              // The cancel button for the invalid input pop-up
              EventHandler<ActionEvent> cancel2 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                  dialog2.close();
                }
              };
              btnCancel2.setOnAction(cancel2);
            }
            dialog.close();
          }
        };
        // The cancel button for the add new user pop-up
        EventHandler<ActionEvent> cancel = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            dialog.close();
          }
        };
        btnSubmit.setOnAction(submit);
        btnCancel.setOnAction(cancel);

        dialog.setScene(dialogScene);
        dialog.show();
      }
    };

    newUser.setOnAction(event);

  }

  /**
   * Handles when a user clicks add friendship
   * 
   * @param btnAddFrnd reference to the add friend button.
   */
  private void clickAddFriendship(Button btnAddFrnd) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Stage stage = (Stage) btnAddFrnd.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        // Set title
        dialog.setTitle("Add Friendship");

        Label label = new Label("Enter two users to add friendship:");
        // users will have to be a list of all the users in the network.
        ObservableList<String> users1 = FXCollections.observableArrayList(sn.getAllUsers()); // TODO:
                                                                                             // UPDATE
                                                                                             // THIS
                                                                                             // WITH
                                                                                             // USERS
                                                                                             // FROM
                                                                                             // SOCIAL
                                                                                             // NETWORK
        ComboBox<String> comboBox1 = new ComboBox<String>(users1);

        // users will have to be a list of all the users in the network.
        ObservableList<String> users2 = FXCollections.observableArrayList(sn.getAllUsers()); // TODO:
                                                                                             // UPDATE
                                                                                             // THIS
                                                                                             // WITH
                                                                                             // USERS
                                                                                             // FROM
                                                                                             // SOCIAL
                                                                                             // NETWORK
        ComboBox<String> comboBox2 = new ComboBox<String>(users2);

        comboBox1.setPrefSize(100, 20);
        comboBox2.setPrefSize(100, 20);

        BorderPane root = new BorderPane();
        HBox box = new HBox();
        HBox box2 = new HBox();
        HBox box3 = new HBox();
        VBox vbox = new VBox();

        box.getChildren().add(label);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 20, 20, 20));

        Button btnSubmit = new Button("Submit");
        Button btnCancel = new Button("Cancel");
        btnSubmit.setPrefSize(75, 20);
        btnCancel.setPrefSize(75, 20);

        box2.getChildren().addAll(comboBox1, comboBox2);
        box2.setSpacing(30);
        box2.setPadding(new Insets(0, 0, 0, 20));

        box3.getChildren().addAll(btnSubmit, btnCancel);
        box3.setSpacing(55);
        box3.setPadding(new Insets(10, 30, 10, 30));

        vbox.getChildren().addAll(box, box2, box3);

        root.setCenter(vbox);


        Scene dialogScene = new Scene(root, 270, 120);


        EventHandler<ActionEvent> submit = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {

            sn.addFriends((String) comboBox1.getValue(), (String) comboBox2.getValue());
            dialog.close();

            updateLastActionAndGroupAndUserCount("Added friendship between: " + comboBox1.getValue()
                + " and " + comboBox2.getValue());
          }
        };

        EventHandler<ActionEvent> cancel = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            dialog.close();
          }
        };
        btnSubmit.setOnAction(submit);
        btnCancel.setOnAction(cancel);

        dialog.setScene(dialogScene);
        dialog.show();
        
        displayFriendsOfOneUser(comboBox1.getValue());
      }
    };
    btnAddFrnd.setOnAction(event);
  }

  private void clickRedo() {
    // TODO: implement if there is time
  }

  /**
   * Handles when a user selects load.
   * 
   * @param load reference to the button.
   */
  private void clickLoad(Button load) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

    	String centralUser = null;
    	  
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");

        Stage stage = (Stage) load.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null)
          sn.loadFromFile(file);

        updateMainComboBox();

        updateLastActionAndGroupAndUserCount("Loaded data from file");
        
        centralUser = sn.getCentralUser();
        
        if (centralUser != null) {
        	setCentralUser(centralUser);
        }        
      }
    };

    load.setOnAction(event);
  }

  /**
   * Handles what happens when the user chooses export
   * 
   * @param export reference to the button.
   */
  private void clickExport(Button export) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extension =
            new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(extension);

        File destination = fileChooser.showSaveDialog((Stage) export.getScene().getWindow());

        if (destination != null)
          sn.saveToFile(destination);
      }
    };
    export.setOnAction(event);

  }

  /**
   * Handles what happens when the user selects exit.
   * 
   * @param exit reference to the exit button.
   */
  private void clickExit(Button exit) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        // ask if the user wants to save before exiting with a pop-up.
        Stage stage = (Stage) exit.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        // Set title
        dialog.setTitle("Exit");

        Label label = new Label("Do you want to save your work?");
        Button btnSave = new Button("Save");
        Button btnDontSave = new Button("Dont Save");

        VBox box = new VBox();
        HBox box2 = new HBox();

        box.getChildren().add(label);
        box.setSpacing(10);
        box2.getChildren().addAll(btnSave, btnDontSave);
        box2.setSpacing(10);
        box.setPadding(new Insets(10, 0, 0, 20));
        box2.setPadding(new Insets(0, 0, 0, 20));

        box.getChildren().add(box2);

        Scene scene = new Scene(box, 210, 80);

        dialog.setScene(scene);
        dialog.show();
        // if the user chooses save it'll prompt them to save their file.
        EventHandler<ActionEvent> eventSave = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extension =
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");

            fileChooser.getExtensionFilters().add(extension);

            File destination = fileChooser.showSaveDialog((Stage) btnSave.getScene().getWindow());

            if (destination != null)
              sn.saveToFile(destination);
            stage.close();
            
          }
        };
        btnSave.setOnAction(eventSave);
        
        // If the user selected don't save the program will close.
        EventHandler<ActionEvent> eventDontSave = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            stage.close();
          }
        };
        btnDontSave.setOnAction(eventDontSave);
      }
    };

    exit.setOnAction(event);
  }

  /**
   * Handles what happens when the user selects remove user.
   * 
   * @param removeUser reference to the button.
   */
  private void clickRemoveUser(Button removeUser) {

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Stage stage = (Stage) removeUser.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        // Set title
        dialog.setTitle("Remove user");

        Label label = new Label("Select a user to remove:");
        // users will have to be a list of all the users in the network.
        ObservableList<String> users1 = FXCollections.observableArrayList(sn.getAllUsers()); // TODO:
                                                                                             // UPDATE
                                                                                             // THIS
                                                                                             // WITH
                                                                                             // USERS
                                                                                             // FROM
                                                                                             // SOCIAL
                                                                                             // NETWORK
        ComboBox<String> comboBox1 = new ComboBox<String>(users1);
        comboBox1.setPrefSize(75, 20);

        BorderPane root = new BorderPane();
        HBox box = new HBox();
        HBox box2 = new HBox();

        box.getChildren().add(label);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 20, 20, 20));

        Button btnSubmit = new Button("Submit");
        Button btnCancel = new Button("Cancel");
        btnSubmit.setPrefSize(75, 20);
        btnCancel.setPrefSize(75, 20);

        box2.getChildren().addAll(comboBox1, btnSubmit, btnCancel);
        box2.setSpacing(10);
        box2.setPadding(new Insets(0, 0, 0, 20));

        root.setTop(box);
        root.setCenter(box2);

        Scene dialogScene = new Scene(root, 275, 100);

        EventHandler<ActionEvent> submit = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {

            sn.removeUser((String) comboBox1.getValue());
            dialog.close();

            updateLastActionAndGroupAndUserCount("Removed user: " + comboBox1.getValue());

            updateMainComboBox();
          }
        };

        EventHandler<ActionEvent> cancel = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            dialog.close();
          }
        };
        btnSubmit.setOnAction(submit);
        btnCancel.setOnAction(cancel);

        dialog.setScene(dialogScene);
        dialog.show();
      }
    };
    removeUser.setOnAction(event);

  }

  /**
   * Handles what happens when the user selects remove friendship.
   * 
   * @param rmFriendship reference to the button.
   */
  private void clickRemoveFriendship(Button rmFriendship) {
    // when the user clicks remove friendship, a pop-up appears.
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Stage stage = (Stage) rmFriendship.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        // Set title
        dialog.setTitle("Remove friendship");

        Label label = new Label("Enter two users to remove friendship:");
        // users will have to be a list of all the users in the network.
        ObservableList<String> users1 = FXCollections.observableArrayList(sn.getAllUsers()); // TODO:
                                                                                             // UPDATE
                                                                                             // THIS
                                                                                             // WITH
                                                                                             // USERS
                                                                                             // FROM
                                                                                             // SOCIAL
                                                                                             // NETWORK
        ComboBox<String> comboBox1 = new ComboBox<String>(users1);

        // users will have to be a list of all the users in the network.
        ObservableList<String> users2 = FXCollections.observableArrayList(sn.getAllUsers()); // TODO:
                                                                                             // UPDATE
                                                                                             // THIS
                                                                                             // WITH
                                                                                             // USERS
                                                                                             // FROM
                                                                                             // SOCIAL
                                                                                             // NETWORK
        ComboBox<String> comboBox2 = new ComboBox<String>(users2);

        comboBox1.setPrefSize(100, 20);
        comboBox2.setPrefSize(100, 20);

        BorderPane root = new BorderPane();
        HBox box = new HBox();
        HBox box2 = new HBox();
        HBox box3 = new HBox();
        VBox vbox = new VBox();

        box.getChildren().add(label);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 20, 20, 20));

        Button btnSubmit = new Button("Submit");
        Button btnCancel = new Button("Cancel");
        btnSubmit.setPrefSize(75, 20);
        btnCancel.setPrefSize(75, 20);

        box2.getChildren().addAll(comboBox1, comboBox2);
        box2.setSpacing(30);
        box2.setPadding(new Insets(0, 0, 0, 20));

        box3.getChildren().addAll(btnSubmit, btnCancel);
        box3.setSpacing(55);
        box3.setPadding(new Insets(10, 30, 10, 30));

        vbox.getChildren().addAll(box, box2, box3);

        root.setCenter(vbox);


        Scene dialogScene = new Scene(root, 270, 120);

        // the submit button action.
        EventHandler<ActionEvent> submit = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {

            sn.removeFriends((String) comboBox1.getValue(), (String) comboBox2.getValue());
            dialog.close();

            updateLastActionAndGroupAndUserCount("Removed friendship between: "
                + comboBox1.getValue() + " and " + comboBox2.getValue());
          }
        };
        // the cancel button action.
        EventHandler<ActionEvent> cancel = new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            dialog.close();
          }
        };
        btnSubmit.setOnAction(submit);
        btnCancel.setOnAction(cancel);

        dialog.setScene(dialogScene);
        dialog.show();
      }
    };
    rmFriendship.setOnAction(event);
  }

  private void clickMainComboBox() {

    /*** Local Variables ***/

    String selection = c1.getValue();

    if (rb1.isSelected()) { // All friends

      /*** Disable Friend comboBox ***/

      c2.setDisable(true);

      /*** Clear current data ***/

      clearCanvas();

      /*** Update listBox with all friends ***/

      displayFriendsOfOneUser(selection);

      /*** Update canvas with all friends ***/

      drawFriends(selection);

      /*** Update last action ***/

      updateLastActionAndGroupAndUserCount("Displayed all friends of " + selection);

    } else if (rb2.isSelected()) { // Mutual friendships

      /*** Enable Friend comboBox ***/

      c2.setDisable(false);

      /*** Update friend comboBox with friends of main user ***/

      updateFriendComboBox(c1.getValue());

    } else if (rb3.isSelected()) { // Shortest path

      /*** Enable Friend comboBox ***/

      c2.setDisable(false);

      /*** Add all users to friend comboBox ***/

      updateFriendComboBoxAllUsers();
    }
  }

  /**
   * Updates last action label with the string that is passed in.
   * 
   * @param action - last action taken to update label with
   */
  private void updateLastActionAndGroupAndUserCount(String action) {
    lblLastAction.setText(action);
    lblGroupCount.setText("Group count: " + sn.getNumberOfConnectedComponents());
    lblUsersCount.setText("User count: " + sn.getAllUsers().size());
  }

private void clickFriendComboBox() {

    /*** Local Variables ***/

    String mainSelection = "";
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
      if (rb2.isSelected()) { // Mutual friends

        /*** Clear current data ***/

        clearCanvas();

        /*** Update listBox with all friends ***/

        displayFriendsTwoUsers(mainSelection, friendSelection);

        /*** Update canvas with all friends ***/

        drawMutualFriends(mainSelection, friendSelection);

        /*** Update last action ***/

        updateLastActionAndGroupAndUserCount(
            "Displayed mutual friends of: " + mainSelection + " and " + friendSelection);

      } else if (rb3.isSelected()) { // Shortest path
        displayShortestPath(mainSelection, friendSelection);

        /*** Update last action ***/

        updateLastActionAndGroupAndUserCount(
            "Displayed shortest path between " + mainSelection + " and " + friendSelection);
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

    clearCanvas();

    /*** Update listBox with all friends ***/

    displayFriendsOfOneUser(selection);

    /*** Update canvas with all friends ***/

    drawFriends(selection);
    
    /*** Update comboBox with selection ***/
    
    c1.setValue(selection);

    /*** Update last action ***/

    updateLastActionAndGroupAndUserCount("Displayed all friends of " + selection);
  }

  private void clickRadioButton() {

    /*** Local Variables ***/

    RadioButton selected = (RadioButton) tGroup.getSelectedToggle();
    List<String> userArray = sn.getAllUsers();
    ObservableList<String> userList = FXCollections.observableArrayList();

    /*** Enable/disable second comboBox based on selected radioButton and update listbox label ***/

    if (selected.equals(rb1)) {

      c2.setDisable(true);
      c2.getItems().clear();

      lblRadioChoice.setText("All friends");

    } else if (selected.equals(rb2)) {
    	
      c2.setDisable(false); 
      c2.getItems().clear();

      lblRadioChoice.setText("Mutual friends");

    } else if (selected.equals(rb3)) {

      c2.setDisable(false);

      lblRadioChoice.setText("Shortest path");

      /*** Add all users to ObservableList ***/

      for (String user : userArray) {
        userList.add(user);
      }

      /*** Clear current data ***/

      c2.getItems().clear();

      /*** Update comboBox with new user data ***/

      c2.setItems(userList);      
    }
  }

  /*** Application ***/

  public static void main(String[] args) {
    launch(args);
  }
}
