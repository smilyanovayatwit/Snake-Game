import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Snake {
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	
	Block head;
	Block tail;
	
	/*Constructor of Snake class
	  Takes in the number of blocks a Snake starts with*/
	public Snake(int initialLength, Field f) {
		int initialPositionX = f.getW()/2;
		int initialPositionY = f.getH()/2;
		
		/*Initializes the head of the Snake with an initial position x,
		  initial position y, and a null previous */
		head = new Block(initialPositionX, initialPositionY, null, f);
		
		blocks.add(head);
		
		head.setFill(Color.PINK.desaturate());
		
		tail = head;
		
		for(int i = 1; i < initialLength; i++) {
			Block b = new Block(initialPositionX + i, initialPositionY, tail, f);
			blocks.add(b);
			tail = b;
		}
	}
	
	public int getDirection() {
		return head.direction;
	}
	
	public void setDirection(int d) {
		head.direction = d;
	}
}