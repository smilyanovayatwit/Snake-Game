import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
	//static directions for the variables
	static final int UP = 0;
	static final int RIGHT = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;
	
	int positionX;
	int positionY;
	
	/*Blocks are following each other so their old position X and Y need 
	  to be saved because blocks following other blocks will use them*/
	int oldPositionX;
	int oldPositionY;
	
	Block previous;
	
	/*To move the head
	 *Initialized to LEFT because at the beginning of each new game the snake
	  always begins to move in the left direction*/
	int direction = LEFT;
	
	int maximumX;
	int maximumY;
	
	/*Constructor of Block class
	 *Takes in the position x, position y, the previous Block and the Field
	 *Takes in the Field because every block should know the maximum X and Y so
	  if it goes out of the Field it can get back from the other side
	 *Takes in the Field from the Snake because the Snake takes takes a Field 
	 *and passes it to the Block*/
	public Block (int x, int y, Block p, Field f) {
		
		/*calls the constructor of the Rectangle class and gives the size
		  of the block that is saved in the Main class*/
		super(Main.blockSize,Main.blockSize);
		this.positionX = x;
		this.positionY = y;
		
		/*Sets the value of property translateX which defines the x coordinate of
		  the translation that is added to this Node's transformation
		 *Not set to positionX because its in blocks which is like the column in the 
		  grid where the block is
		 *The position in pixels is calculated using positionX 
		  and multiplying it by the blockSize */
		setTranslateX(positionX * Main.blockSize);
		
		/*Sets the value of property translateX which defines the y coordinate of
		  the translation that is added to this Node's transformation
		 *positionY in pixels is calculated by multiplying positionY by the 
		  blockSize*/
		setTranslateY(positionY * Main.blockSize);
		previous = p;
		
		//the maximumX equals the width of the Field
		maximumX = f.getW();
		
		//the maximumY equals the height of the Field
		maximumY = f.getH();
	}
	
	public void update() {
		//Sets the old position X and Y to the current X and Y position
		oldPositionX = positionX;
		oldPositionY = positionY;
		
		//If the previous block is null, the block is the head
		if(previous == null) {
			//logically updates the direction of the Snake
			switch(direction) {
				case UP: 
					moveUp(); 
					break;
				case RIGHT: 
					moveRight(); 
					break;
				case DOWN: 
					moveDown(); 
					break;
				case LEFT: 
					moveLeft();
					break;
			} 
		  //If the block is not a head 	
		} else { 
			/*logically updates the direction of the Snake
			 *gets the coordinates of the previous block*/
			positionX = previous.oldPositionX;
			positionY = previous.oldPositionY;
		}
		//visually updates the direction of the Snake
		updatePosition();
	}
	
	/*In Java coordinate system the upper-left corner of the Pane is the origin
	  (0,0). Therefore X coordinate values increase to the right, and Y coordinate
	  values increase as the go down*/
	public void moveUp() {
		positionY--;
		if(positionY < 0) {
			positionY = maximumY - 1;
		}
	}
	
	public void moveDown() {
		positionY++;
		if(positionY >= maximumY) {
			positionY = 0;
		}
	}
	
	public void moveLeft() {
		positionX--;
		if(positionX < 0) {
			positionX = maximumX - 1;
		}
	}
	
	public void moveRight() {
		positionX++;
		if(positionX >= maximumX) {
			positionX = 0;
		}
	}
	
	//Applies the movements done on the position X and Y coordinates
	public void updatePosition() {
		setTranslateX(positionX * Main.blockSize);
		setTranslateY(positionY * Main.blockSize);
	}	
}