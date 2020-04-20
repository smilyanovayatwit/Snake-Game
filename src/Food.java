import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
	int positionX;
	int positionY;
	
	//gets the positionX of the Food object
	public int getPositionX() {
		return positionX;
	}
	
	//gets the positionY of the Food object
	public int getPositionY() {
		return positionY;
	}
	
	public Food(int x, int y) {
		//Initializes rectangle by calling super constructor and giving it the block size
		super(Main.blockSize, Main.blockSize);
		positionX = x;
		positionY = y;
		
		/*positionX and positionY are in blocks not pixels. To get the actual position
		  positionX and positionY have to be multiplied by the blockSize*/
		setTranslateX(positionX * Main.blockSize);
		setTranslateY(positionY * Main.blockSize);
		
		setFill(Color.LIGHTGREEN);
		setStroke(Color.GREEN);
	}	
}