import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/***
 * 
 * @authors Yuliya Smilyanova and Elizabeth Volg
 * 
 * This is a simple Snake game using JavaFX. The player maneuvers a line, the snake, using
 * the arrow keys. The player controls the snake to get the food, the rectangle. The food is 
 * given a new random position on the screen every time the snake eats it and a new random 
 * color. Whenever the snake eats the food, it grows in length and the score increases by 1 
 * point. If the snake touches its body, a "Game Over!" message is displayed in a pop up window
 * with the player's score. After the "OK" button is pressed on pop up window the game starts 
 * over with the snake being reset to its initial length of 5 blocks and the score being reset 
 * to 0 points.
 * 
 */

public class Main extends Application {
	
	/*blocksize in pixels -> how big each block is going to be*/
	static int blockSize = 20;
	
	//width and height of the field -> width and height of the game in blocks
	int width = 30; //30 blocks
	int height = 20; //20 blocks
	
	//initial length of the Snake -> 5 blocks
	int initialLength = 5; 
	
	//equals current time
	long then = System.nanoTime();
	
	//direction change is set to false
	boolean changed = false;
	
	/*If the Snake object has a second direction update -> if 
	  two arrows keys were pressed, the direction of the second
	  arrow key is saved and done in the next position update*/
	int nextUpdate;
	boolean hasNext = false;
	
	Field f;

	@Override
	public void start(Stage primaryStage) {
		//Creates a vertical box with 10 pixels of spacing
		VBox root = new VBox(10);
	
		//Gives the vertical box, root, 10 pixels of padding
		root.setPadding(new Insets(10));
		
		//Creates a Field with a width of 30 blocks and a height of 20 blocks
		f = new Field (width, height);
		
		/*Adds Snake to the Field 
		  Creates a Snake object by calling the Snake class constructor and then
		  adding the Snake to the Field*/
		f.addSnake(new Snake(initialLength,f));
		
		//Creates a score label
		Label score = new Label("Score: 0");
		score.setFont(Font.font("tahoma",32));
		
		//A basic timer whose handle method is called in every frame
		AnimationTimer timer = new AnimationTimer() {
			//parameter now is the timestamp of the current frame given in nanoseconds
			public void handle(long now) {
				//Updates one time every one second
				if((now - then) > (1000000000/7)) {
					//the Field updates
					f.update();
					then = now;
					score.setText("Score: " + f.score);
					changed = false;
					
					/*Checks if there is a second direction update, for
					  example if two direction buttons were clicked */
					if(hasNext) {
						setDirection(f.snake,nextUpdate);
						hasNext = false;
					}
					
					//Checks if the Snake is dead
					if(f.isDead()) {
						
						//Stops the AnimationTimer
						stop();
						
						/*Creates an Alert, a predefined dialog that displays some kind of 
						  information to the user, of type INFORMATION, which configures the 
						  Alert dialog to appear in a way that suggests the content of the 
						  dialog is informing the user of a piece of information */
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Game Over!");
						alert.setContentText("Your score is " + f.score);
						
						/*Displays the alert message to the user in a pop-up window and waits
						  for the user to close it by clicking the button "OK" */
						Platform.runLater(alert::showAndWait);
						
						/*After the user closes the Alert message, the event handler is invoked 
						  allowing for the game to start over again*/
						alert.setOnHidden(e -> {
							//removes the Field and user's score from root
							root.getChildren().clear();
							
							//creates a new Field
							f = new Field(width, height);
							
							//Adds a new Snake object to the Field
							f.addSnake(new Snake(initialLength, f));
							
							//resets the user's score to 0 
							score.setText("Score: 0");
							
							//Adds the Field and reset score to root
							root.getChildren().addAll(f, score);
							
							//Starts the AnimationTimer
							start();
						});
					}
				}
			}
		};
		
		//Starts the AnimationTimer
		timer.start();
		
		//Adds Field f and Label score to root
		root.getChildren().addAll(f,score);
		
		//Creates a Scene using root
		Scene scene = new Scene(root);
		
		//Changes the direction if any of the arrow keys have been pressed
		scene.setOnKeyPressed(e ->{
			
			/*If the arrow button pointing up has been pressed, the Snake object's direction is 
			  set so that it will move in the upwards direction*/
			if(e.getCode().equals(KeyCode.UP) && f.snake.getDirection() != Block.DOWN) {
				setDirection(f.snake, Block.UP);
			}
			
			/*If the arrow button pointing down has been pressed, the Snake object's direction is 
			  set so that it will move in the downwards direction*/
			if(e.getCode().equals(KeyCode.DOWN) && f.snake.getDirection() != Block.UP) {
				setDirection(f.snake, Block.DOWN);
			}
			
			/*If the arrow button pointing left has been pressed, the Snake object's direction is 
			  set so that it will move in the left direction*/
			if(e.getCode().equals(KeyCode.RIGHT) && f.snake.getDirection() != Block.LEFT) {
				setDirection(f.snake, Block.RIGHT);
			}
			
			/*If the arrow button pointing right has been pressed, the Snake object's direction is 
			  set so that it will move in the right direction*/
			if(e.getCode().equals(KeyCode.LEFT) && f.snake.getDirection() != Block.RIGHT) {
				setDirection(f.snake, Block.LEFT);
			}
		});
		
		//makes primaryStage not resizable
		primaryStage.setResizable(false);
		
		/*sets scene to primaryStage in order to display content on the Stage*/
		primaryStage.setScene(scene);
		
		/*sets title of primaryStage*/
		primaryStage.setTitle("Snake Game");
		
		//Makes the Stage visible -> allows you to portray the Stage
		primaryStage.show();
	}
	
	/*Allows for only one direction update every position update (The
	  Field updates the position the only for the 1st arrow key pressed)
	 *If the Snake object has another directions updates, the 
	  direction is saved and executed in the following position
	  update*/
	public void setDirection(Snake s, int d) {
		//If the direction has not been changed
		if(!changed) {
			
			//changes the direction of the Snake
			s.setDirection(d);
			
			//Sets changed direction to true
			changed = true;
			
		} else {
			/*If the Snake has a second direction update, hasNext direction 
			  is changed to true and the position update is saved as the second
			  direction*/
			hasNext = true;
			nextUpdate = d;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}