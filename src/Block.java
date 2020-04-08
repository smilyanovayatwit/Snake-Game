import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
	static final int UP = 0;
	static final int RIGHT = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;
	
	int positionX;
	int positionY;
	int oldPositionX;
	int oldPositionY;
	
	Block previous;
	
	int direction = LEFT;
	
	int maximumX;
	int maximumY;
	
	/*Constructor of Block class
	  Takes in the position x and the position y and the previous Block*/
	public Block (int x, int y, Block p, Field f) {
		super(Main.blockSize,Main.blockSize);
		this.positionX = x;
		this.positionY = y;
		
		setTranslateX(positionX * Main.blockSize);
		setTranslateY(positionY * Main.blockSize);
		previous = p;
		maximumX = f.getW();
		maximumY = f.getH();
	}
	
	public void update() {
		oldPositionX = positionX;
		oldPositionY = positionY;
		
		//If the block is a head
		if(previous == null) {
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
		} else { //If the block is not a head 
			positionX = previous.oldPositionX;
			positionY = previous.oldPositionY;
		}
		updatePosition();
	}
	
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
	
	public void updatePosition() {
		setTranslateX(positionX * Main.blockSize);
		setTranslateY(positionY * Main.blockSize);
	}	
}