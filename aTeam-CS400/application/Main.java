package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
			BorderPane root = new BorderPane();
			
			root.setCenter(createCanvasPane());
			
			
			
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private BorderPane createCanvasPane() {
		
		/*** Local Constants ***/
		
		final double CANVAS_X = 300;
		final double CANVAS_Y = 300;
		
		/*** Local Variables ***/
		
		BorderPane pane = new BorderPane();		
		
		Canvas canvas = new Canvas(CANVAS_X, CANVAS_Y);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Label lblRadioChoice = new Label("All friends (default)");
				
		VBox rightBox = new VBox();
		
		ListView<String> friendList = new ListView<String>();
		
		/*** Add components to HBox ***/
		
		rightBox.getChildren().add(lblRadioChoice);
		rightBox.getChildren().add(friendList);
		
		/*** Add EXAMPLE FRIENDS ***/
		
		drawExampleFriends(gc, CANVAS_X, CANVAS_Y);
		
		/*** Add components to main pane ***/
		
		pane.setLeft(canvas);
		pane.setRight(rightBox);
				
		return pane;
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
	
	private GridPane createButtonPane() {
		
		/*** Local Variables ***/

		GridPane buttonPane = new GridPane();
		
		
		return buttonPane;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
