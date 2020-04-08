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
	
	//Declares width and height variables
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
		
		//Sets minimum size
		setMinSize(w * Main.blockSize, h * Main.blockSize);
		
		//Sets background
		setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		/*Sets border which takes in the color black, a solid BorderStrokeStyle,
		  a null corner radius, and a BorderWidth of 1 pixel*/
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		addFood();
	}

	//Adds the Snake to the Field
	public void addSnake(Snake s) {
		snake = s;
		for(Block b : snake.blocks) { // s.blocks????? 17 mins
			addBlock(b);
		}
	}
	
	public void update() {
		for(Block b : blocks) {
			b.update();
		}
		
		/*If the snake has eaten the food the score will increase by
		  1 point and a new piece of food will be added to the Field*/
		if(isEaten(f)) {
			score += 1;
			addFood();
			addNewBlock();
		}
	}
	
	//Checks if the snake is dead
	public boolean isDead() {
		for(Block b : blocks) {
			if(b != snake.head) {
				if(b.positionX == snake.head.positionX && b.positionY == snake.head.positionY) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void addNewBlock() {
		Block b = new Block(snake.tail.oldPositionX, snake.tail.oldPositionY, snake.tail, this);
		snake.tail = b;
		addBlock(b);
	}
	
	/*Adds all the blocks from the Snake to the ArrayList of blocks 
	  in the Field and to the Pain*/
	private void addBlock(Block b) {
		//Adds Block b to Pain
		getChildren().add(b);
		
		//Adds block b to ArrayList blocks
		blocks.add(b);
	}
	
	//Adds a new piece of food to the Pain by choosing a random position 
	public void addFood() {
		int randomX = (int) (Math.random() * w);
		int randomY = (int) (Math.random() * h);
		
		Food food = new Food(randomX, randomY);
		
		//Adds food to Pain
		getChildren().add(food);
		
		getChildren().remove(f);
		f = food;
	}
	
	//Checks if the snake ate the food
	public boolean isEaten(Food f) {
		if(f == null) {
			return false;
		}
		return f.getPositionX() == snake.head.positionX && f.getPositionY() == snake.head.positionY;
	}
	
	//returns the width
	public int getW() {
		return w;
	}

	//returns the height
	public int getH() {
		return h;
	}
}