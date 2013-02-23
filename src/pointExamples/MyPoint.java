package pointExamples;

import processing.core.PApplet;
import processing.core.PImage;
import util.InteractivePoint;

public class MyPoint extends InteractivePoint {

	private PImage imageUnselected;
	private PImage imageSelected;
	private int widthImage;
	private int heightImage;
	
	public MyPoint(int posX, int posY, PApplet mainApplet) {
		super(posX, posY, mainApplet);
		widthImage = 25;
		heightImage = 25;
	}
	public boolean mouseIsOver()
	{
		float upperCornerX = posX - widthImage/2;
		float upperCornerY = posY - heightImage/2;

		if((mainApplet.mouseX >= upperCornerX && mainApplet.mouseX <= upperCornerX + widthImage) &&
				(mainApplet.mouseY >= upperCornerY && mainApplet.mouseY <= upperCornerY + heightImage))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void setImages(String imageSelected, String imageUnselected)
	{
		this.imageSelected = mainApplet.loadImage(imageSelected);
		this.imageUnselected = mainApplet.loadImage(imageUnselected);
	}
	
	public void setImageSize(int width, int height)
	{
		widthImage = width;
		heightImage = height;
	}
	@Override
	protected void drawUnselectedState() {
		mainApplet.image(imageUnselected, posX - widthImage/2, posY - heightImage/2, widthImage, heightImage);
	}

	@Override
	protected void drawSelectedState() {

		mainApplet.image(imageSelected, posX - widthImage/2, posY - heightImage/2, widthImage, heightImage);
	}
}
