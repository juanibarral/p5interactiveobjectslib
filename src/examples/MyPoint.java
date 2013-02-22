package examples;

import processing.core.PApplet;
import util.VAPoint;

public class MyPoint extends VAPoint {

	public MyPoint(int posX, int posY, PApplet mainApplet) {
		super(posX, posY, mainApplet);
	}
	@Override
	protected void drawUnselectedState() {
		mainApplet.rect((float)posX, (float)posY, size, size);
	}

	@Override
	protected void drawSelectedState() {

		mainApplet.triangle((float)posX, (float)posY, (float)posX + size, (float)posY, (float)posX + (size/2), (float)posY - size);
	}
}
