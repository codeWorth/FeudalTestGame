package util.math;

import graphics.Camera;
import physics.Physics;

public class Point implements General2D {
	
	public enum PointType { SCREEN, GAME };

	private double screenX, screenY;
	private double gameX, gameY;
	
	/**
	 * Sets point X and Y
	 * <p>
	 * 
	 * @param x		x position
	 * @param y		y position
	 * @param type	PointType of the x and y inputed
	 */
	public Point(double x, double y, PointType type) {
		setX(x, type);
		setY(y, type);
	}
	
	/**
	 * Manually set the coordinates of this point
	 * 
	 * @param screenX	x relative to upper left of window
	 * @param screenY	y relative to upper left of window
	 * @param gameX		x relative to (0,0) in the world
	 * @param gameY		y relative to (0,0) in the world
	 */
	public Point(double screenX, double screenY, double gameX, double gameY) {
		this.screenX = screenX;
		this.screenY = screenY;
		this.gameX = gameX;
		this.gameY = gameY;
	}
	
	/**
	 * Sets the x value of this point. Keep in mind that this
	 * recalculates the point's screen and game values
	 * 
	 * @param x	new X value
	 */
	public void setX(double x, PointType type) {
		if (type == PointType.GAME) {
			this.gameX = x;
		} else {
			this.screenX = x;
			this.gameX = ( (this.screenX - Camera.CAM_WIDTH/2) * Physics.resolution ) + Camera.CAM_X;
		}
	}
	
	public void setY(double y, PointType type) {
		if (type == PointType.GAME) {
			this.gameY = y;
		} else {
			this.screenY = y;
			this.gameY = ( (this.screenY - Camera.CAM_HEIGHT/2) * Physics.resolution ) + Camera.CAM_Y;
		}
	}
	
	public void setXY(double x, double y, PointType type) {
		setX(x, type);
		setY(y, type);
	}
	
	
	public double screenX() {
		this.screenX = ( (this.gameX - Camera.CAM_X) / Physics.resolution ) + Camera.CAM_WIDTH/2;
		return this.screenX;
	}
	
	public double screenY() {
		this.screenY = ( (this.gameY - Camera.CAM_Y) / Physics.resolution ) + Camera.CAM_HEIGHT/2;
		return this.screenY;
	}
	
	public double gameX() {
		return this.gameX;
	}
	
	public double gameY() {
		return this.gameY;
	}
	
	
	/**
	 * Adds point other to this point. Changes this point itself.
	 * 
	 * @param other		Any other Point
	 */
	public void add(General2D other) {
		
		this.gameX += other.gameX();
		this.gameY += other.gameY();
		
		this.screenX += other.screenX();
		this.screenY += other.screenY();
		
	}
	
	/**
	 * Subtracts point other from this point. Changes this point itself.
	 * 
	 * @param other		Any other Point
	 */
	public void subtract(General2D other) {
		
		this.gameX -= other.gameX();
		this.gameY -= other.gameY();
		
		this.screenX -= other.screenX();
		this.screenY -= other.screenY();
		
	}
	
	/**
	 * Multiplies a scalar by this point. Changes this point itself.
	 * 
	 * @param scalar	Any number
	 */
	public void mult(double scalar) {
	
		this.gameX *= scalar;
		this.gameY *= scalar;
		
		this.screenX *= scalar;
		this.screenY *= scalar;
		
	}
	
	/**
	 * Multiplies a General2D by a scalar, then adds it to this point. Only modifies this point.
	 * 
	 * @param other Other point to add
	 * @param scalar Number to multiply it before hand
	 */
	public void addMult(General2D other, double scalar) {
		
		this.gameX += other.gameX() * scalar;
		this.gameY += other.gameY() * scalar;
		
		this.screenX += other.screenX() * scalar;
		this.screenY += other.screenY() * scalar;
		
	}
	
	/**
	 * Returns the distance to anther point squared. This is higher
	 * performance than distance, so use if possible.
	 * 
	 * @param other	The point who's distance to this point will be found
	 * @return	A double which represents the distance between
	 */
	public double distance2(Point other) {
		double dX = other.gameX() - this.gameX();
		double dY = other.gameY() - this.gameY();
		
		return dX * dX + dY * dY;
	}
	
	/**
	 * Dot product of 2 points. Returns a new point.
	 * 
	 * @param a		first point in dot product
	 * @param b		second point in dot product
	 */
	public static Point mult(General2D a, General2D b) {
		
		double screenX = a.screenX() * b.screenX();
		double screenY = a.screenY() * b.screenY();
		double gameX = a.gameX() * b.gameX();
		double gameY = a.gameY() * b.gameY();
		
		Point newPoint = new Point(screenX, screenY, gameX, gameY);
		return newPoint;
	}
	
}
