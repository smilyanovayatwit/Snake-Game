import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Snake {
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	
	Block head;
	Block tail;
	
	/*Constructor of Snake class
	 *Takes in initialLength, the number of blocks the Snake starts off with, 
	  and the Field so the Snake can spawn in the center of the Field at the 
	  beginning of each game. By passing in the Field, we want to know its 
	  width and height to calculate the center*/
	public Snake(int initialLength, Field f) {
		
		//initial positionX of the Snake equals the width of the field divided by 2
		int initialPositionX = f.getW()/2;
		
		//initial positionY of the Snake equals the height of the field divided by 2
		int initialPositionY = f.getH()/2;
		
		/*Initializes the head of the Snake with an initial position x,
		  initial position y, a null previous, and a Field f */
		head = new Block(initialPositionX, initialPositionY, null, f);
		
		//Adds the head of the Snake to the ArrayList of blocks
		blocks.add(head);
		
		head.setFill(Color.PINK.desaturate());
		
		//uses tail as previous 
		tail = head;
		
		/*For loop which iterates over the Snake's initial length and adds each
		  of the Blocks into the ArrayList blocks
		 *Loop starts at 1 because the first block (the head) has already been created
		  and added into the ArrayList
		 *When the loop is finished tail will be the last block*/
		for(int i = 1; i < initialLength; i++) {
			
			/*the Block contructor takes in initialPositionX + 1 because every time a new 
			  block is created it will be offset it to the right */
			Block b = new Block(initialPositionX + i, initialPositionY, tail, f);
			
			//Adds the block to the block ArrayList
			blocks.add(b);
			
			//tail points to the new block created
			tail = b;
		}
	}
	
	//returns the direction of the head
	public int getDirection() {
		return head.direction;
	}
	
	//changes the direction of the head
	public void setDirection(int d) {
		head.direction = d;
	}
}