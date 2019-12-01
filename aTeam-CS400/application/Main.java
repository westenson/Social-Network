package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			/*** Local Variables ***/
			
			BorderPane root = new BorderPane();
			
			VBox centerBox = new VBox();
			
			HBox spacer1 = new HBox();  // TEMPORARY FOR SPACING PURPOSES
			HBox spacer2 = new HBox();  // TEMPORARY FOR SPACING PURPOSES			
			
			/*** Add components to centerBox ***/
			
			centerBox.getChildren().add(createCanvasPane());
			centerBox.getChildren().add(createButtonPane());
			
			/*** Create Spacers ***/
			
			spacer1 = createPlaceHolder();  // TEMPORARY FOR SPACING PURPOSES
			spacer2 = createPlaceHolder();  // TEMPORARY FOR SPACING PURPOSES
			
			/*** Add components to root ***/
			
			root.setTop(spacer1);		 // TEMPORARY FOR SPACING PURPOSES	
			root.setCenter(centerBox);	
			root.setBottom(spacer2);     // TEMPORARY FOR SPACING PURPOSES
			
			/*** Set scene ***/
			
			Scene scene = new Scene(root,725,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			/*** Set Stage and show ***/
			
			primaryStage.setTitle("Network Visualizer");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private HBox createCanvasPane() {
		
		/*** Local Constants ***/
		
		final double CANVAS_X = 300;
		final double CANVAS_Y = 300;
		
		/*** Local Variables ***/
		
		HBox mainBox = new HBox();		
		
		Canvas canvas = new Canvas(CANVAS_X, CANVAS_Y);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Label lblRadioChoice = new Label("All friends (default)");
				
		VBox rightBox = new VBox();
		
		ListView<String> lvFriends = new ListView<String>();
		
		/*** HARDCODED DATA FOR EXAMPLE ***/
		
		ObservableList<String> friendsList = FXCollections.observableArrayList ("Friend 1", 
											 "Friend 2", "Friend 3", "Friend 4");
		
		lvFriends.setItems(friendsList);
		
		/*** Add components to VBox ***/
		
		rightBox.getChildren().add(lblRadioChoice);
		rightBox.getChildren().add(lvFriends);
		
		/*** Set padding ***/
		
		mainBox.setPadding(new Insets(15, 15, 15, 15));
		mainBox.setSpacing(130);
		
		/*** Add EXAMPLE FRIENDS ***/
		
		drawExampleFriends(gc, CANVAS_X, CANVAS_Y);
		
		/*** Add components to main pane ***/
		
		mainBox.getChildren().add(canvas);
		mainBox.getChildren().add(rightBox);
				
		return mainBox;
	}
	
	private void drawExampleFriends(GraphicsContext gc, double x, double y) {
		
		/*** Local Constants ***/
		
		final double CIRCLE_HEIGHT    = 50.0;
		final double CIRCLE_WIDTH     = 50.0;
		final double CIRCLE_MID		  = CIRCLE_HEIGHT / 2;
		final double START_X 	      = 0.0;
		final double START_Y	      = 0.0;
		final double FRIEND_X_OFFSET  = 75.0;
		final double FRIEND_Y_OFFSET  = 75.0;
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
		
		gc.strokeText("Friend 2",-FRIEND_X_OFFSET + CENTERING_OFFSET, CIRCLE_MID + CENTERING_OFFSET);
		gc.strokeText("Friend 4", FRIEND_X_OFFSET + CENTERING_OFFSET, CIRCLE_MID + CENTERING_OFFSET);
		
		/*** Draw lines to horizontal friends ***/
		
		gc.strokeLine(0, CIRCLE_HEIGHT / 2, CIRCLE_WIDTH - FRIEND_X_OFFSET, CIRCLE_HEIGHT / 2);
		gc.strokeLine(CIRCLE_WIDTH, CIRCLE_HEIGHT / 2, FRIEND_X_OFFSET, CIRCLE_HEIGHT / 2);		
	}
	
	private HBox createButtonPane() {
		
		/*** Local Constants ***/
		
		final double BUTTON_HEIGHT  = 20.0;
		final double BUTTON_WIDTH   = 90.0;
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
		
		/*** Set button size and add to pane***/
		
		for (Button b : buttonList) {
			
			b.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			
			buttonPane.getChildren().add(b);
		}
		
		return buttonPane;
	}
	
	private HBox createPlaceHolder() {
		
		/*** Local Variables ***/
		
		HBox spacer = new HBox();
		
		spacer.setSpacing(20);
		
		for (int i = 0; i < 7; i++) {
			spacer.getChildren().add(new Button("Placeholder " + i));
		}		
		
		return spacer;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
