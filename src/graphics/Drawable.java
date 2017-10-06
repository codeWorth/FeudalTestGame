package graphics;

import java.awt.Graphics2D;

public interface Drawable {

	/**
	 * Draws a visual representation of the object.
	 * Will use the Camera class to find
	 * Camera X, Y, Scale, and Rotation.
	 * <p>
	 * 
	 * @param ctx		context to draw the object on
	 */
	public void draw(Graphics2D ctx);
	
	/**
	 * Returns a boolean which will decide if the object is drawn or not
	 * This method should be lightweight, so err on the side of
	 * quick execute than sometimes draws the object unnecessarily, rather
	 * than complicated equations.
	 * 
	 * @return Whether or not the object is in the window
	 */
	public boolean inCameraWindow();
	
}
