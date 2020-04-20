import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
		
		setFill(randomColor());
		setStroke(randomColor());
	}	
	
	//Creates a random color for the Food object whenever the method is called
	public Paint randomColor() {
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return Color.rgb(r,g,b);
	}
}