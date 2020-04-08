import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
	int positionX;
	int positionY;
	
	public int getPositionX() {
		return positionX;
	}
	
	public int getPositionY() {
		return positionY;
	}
	
	public Food(int x, int y) {
		//Initializes rectangle by calling super constructor and giving it the block size
		super(Main.blockSize, Main.blockSize);
		positionX = x;
		positionY = y;
		setTranslateX(positionX * Main.blockSize);
		setTranslateY(positionY * Main.blockSize);
		
		setFill(Color.LIGHTGREEN);
		setStroke(Color.GREEN);
	}	
}