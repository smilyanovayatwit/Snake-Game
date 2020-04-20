import java.util.ArrayList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Field extends Pane{
	
	//Declares width and height variables in blocks
	private int w; 
	private int h; 
	
	//Creates an ArrayList of blocks
	ArrayList<Block> blocks = new ArrayList<Block>();
	
	int score = 0;
	Food f;
	Snake snake;
	
	//Constructor for the Field class
	public Field (int width, int height) {
		this.w = width;
		this.h = height;
		
		//Sets minimum size of the Field
		setMinSize(w * Main.blockSize, h * Main.blockSize);
		
		/*Sets background of the Field by creating a new Background which creates a new 
		  BackgroundFill which takes in a Paint fill of the color white, and a null
		  CornerRadii and Insets*/
		setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		/*Sets border by creating a new Border which creates a new BorderStroke which takes
		  in a stroke of the color black, a solid BorderStrokeStyle, a null CornerRadii, and 
		  a BorderWidth of 1 pixel*/
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		
		//Creates a food object and adds it to the Field
		addFood();
	}

	//Adds the Snake to the Field
	public void addSnake(Snake s) {
		snake = s;
		/*Loops through the blocks of the Snake and adds each of them them to the ArrayList of
		  blocks in the Field and the Pane, both logically and visually*/
		for(Block b : snake.blocks) { 
			addBlock(b);
		}
	}
	
	public void update() {
		/*Updates every Block's position for every Block b from the Blocks in the Field
		 by calling the update() method in the Block class*/
		for(Block b : blocks) {
			b.update();
		}
		
		/*If the snake has eaten the food the score will increase by
		  1 point and a new piece of food will be added to the Field*/
		if(isEaten(f)) {
			score += 1;
			addFood();
			/*Calls the addNewBlock() method which adds a new block to the end of the 
			  Snake once it has eaten the Food*/
			addNewBlock();
		}
	}
	
	//Checks if the Snake is dead
	public boolean isDead() {
		for(Block b : blocks) {
			if(b != snake.head) {
				/*Checks if the position of the Snake'head is the same as the 
				  position of another Block on the Snake's body
				 *If it is the Snake is dead and the method returns true*/
				if(b.positionX == snake.head.positionX && b.positionY == snake.head.positionY) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Adds a block at the end of the Snake once it has eaten the Food
	public void addNewBlock() {
		/*Creates a new Block which takes in the previous X and Y position of the Snake's  tail, the
		 the old tail as the previous block for the new tail, and this Field*/
		Block b = new Block(snake.tail.oldPositionX, snake.tail.oldPositionY, snake.tail, this);
		snake.tail = b;
		addBlock(b);
	}
	
	/*Adds all the blocks from the Snake to the ArrayList of blocks 
	  in the Field and to the Pane*/
	private void addBlock(Block b) {
		//Visually adds Block b to Pane
		getChildren().add(b);
		
		//Adds block b to ArrayList blocks
		blocks.add(b);
	}
	
	//Adds a new piece of food by choosing a random position on the Pane
	public void addFood() {
		//gets a random X and Y position
		int randomX = (int) (Math.random() * w);
		int randomY = (int) (Math.random() * h);
		
		//Creates a new Food object with a random X and Y position on the Field
		Food food = new Food(randomX, randomY);
		
		//Adds food to Pain
		getChildren().add(food);
		
		//Removes food which has previously been eaten from the Field
		getChildren().remove(f);
		
		f = food;
	}
	
	//Checks if the Snake ate the food
	public boolean isEaten(Food f) {
		if(f == null) {
			return false;
		}
		
		/*If the head position of the Snake is the same as the Food position, the Snake
		  eats the food and returns true*/
		return f.getPositionX() == snake.head.positionX && f.getPositionY() == snake.head.positionY;
	}
	
	//returns the width of the Field
	public int getW() {
		return w;
	}

	//returns the height of the Field
	public int getH() {
		return h;
	}
}