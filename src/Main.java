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
 * given a new random position on the screen everytime the snake eats it. Whenever the snake 
 * eats the food, it grows in length and the score increases by 1 point. If the snake touches 
 * its body, a "Game Over!" message is displayed in a pop up window with the player's score. 
 * After the "OK" button is pressed on pop up window the game starts over with the snake being 
 * reset to its initial length of 5 blocks and the score being reset to 0 points.
 * 
 */

public class Main extends Application {
	
	static int blockSize = 20;
	
	//width and height of the field 
	int width = 30; //30 blocks
	int height = 20; //20 blocks
	
	//initial length of the Snake
	int initialLength = 5;
	
	//equals current time
	long then = System.nanoTime();
	
	//so set direction in Main doesn't happen every time
	boolean changed = false;
	int nextUpdate;
	boolean hasNext = false;
	
	Field f;

	@Override
	public void start(Stage primaryStage) {
		//Creates a vertical box with 10 pixels of spacing
		VBox root = new VBox(10);
	
		//Gives the vertical box, root, 10 pixels of padding
		root.setPadding(new Insets(10));
		
		//Creates a Field
		f = new Field (width, height);
		
		//Adds Snake to the Field
		f.addSnake(new Snake(initialLength,f));
		
		//Creates a score label
		Label score = new Label("Score: 0");
		score.setFont(Font.font("tahoma",32));
		
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				//Updates one time every one second
				if((now - then) > (1000000000/7)) {
					f.update();
					then = now;
					score.setText("Score: " + f.score);
					changed = false;
					if(hasNext) {
						setDirection(f.snake,nextUpdate);
						hasNext = false;
					}
					
					if(f.isDead()) {
						stop();
						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Game Over!");
						alert.setContentText("Your score is " + f.score);
						Platform.runLater(alert::showAndWait);
						
						alert.setOnHidden(e -> {
							root.getChildren().clear();
							f = new Field(width, height);
							f.addSnake(new Snake(initialLength, f));
							score.setText("Score: 0");
							root.getChildren().addAll(f, score);
							start();
						});
					}
				}
			}
		};
		
		timer.start();
		
		//Adds field f and Label score to root
		root.getChildren().addAll(f,score);
		
		//Creates a Scene using root
		Scene scene = new Scene(root);
		
		scene.setOnKeyPressed(e ->{
			if(e.getCode().equals(KeyCode.UP) && f.snake.getDirection() != Block.DOWN) {
				setDirection(f.snake, Block.UP);
			}
			if(e.getCode().equals(KeyCode.DOWN) && f.snake.getDirection() != Block.UP) {
				setDirection(f.snake, Block.DOWN);
			}
			if(e.getCode().equals(KeyCode.RIGHT) && f.snake.getDirection() != Block.LEFT) {
				setDirection(f.snake, Block.RIGHT);
			}
			if(e.getCode().equals(KeyCode.LEFT) && f.snake.getDirection() != Block.RIGHT) {
				setDirection(f.snake, Block.LEFT);
			}
		});
		
		//makes primaryStage not resizable
		primaryStage.setResizable(false);
		
		//sets scene to primaryStage
		primaryStage.setScene(scene);
		
		/*sets title of primaryStage*/
		primaryStage.setTitle("Snake Game");
		
		primaryStage.show();
	}
	
	/*Allows for only one direction update every position update */
	public void setDirection(Snake s, int d) {
		if(!changed) {
			s.setDirection(d);
			changed = true;
		} else {
			hasNext = true;
			nextUpdate = d;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}