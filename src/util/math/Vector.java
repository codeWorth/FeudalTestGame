package util.math;

import physics.Physics;

public class Vector implements General2D {

	private double x, y;
	
	/**
	 * Sets vector X and Y
	 * <p>
	 * 
	 * @param x		x position
	 * @param y		y position
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets the x value of this point.
	 * 
	 * @param x	new X value
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Sets the y value of this point.
	 * 
	 * @param y	new Y value
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	public void setXY(double x, double y) {
		setX(x);
		setY(y);
	}
	
	
	public double screenX() {
		return this.x / Physics.resolution;
	}
	
	public double screenY() {
		return this.y / Physics.resolution;
	}
	
	public double gameX() {
		return this.x;
	}
	
	public double gameY() {
		return this.y;
	}
	
	
	/**
	 * Adds vector other to this vector. Changes this vector itself.
	 * 
	 * @param other		Any other Vector
	 */
	public void add(General2D other) {
		
		this.x += other.gameX();
		this.y += other.gameY();
		
	}
	
	/**
	 * Subtracts vector other from this vector. Changes this vector itself.
	 * 
	 * @param other		Any other Vector
	 */
	public void subtract(General2D other) {
		
		this.x -= other.gameX();
		this.y -= other.gameY();
		
	}
	
	/**
	 * Multiplies a scalar by this vector. Changes this vector itself.
	 * 
	 * @param scalar	Any number
	 */
	public void mult(double scalar) {
	
		this.x *= scalar;
		this.y *= scalar;
		
	}
	
	/**
	 * Set length of vector to l while maintaining ratio   d
	 * 
	 * @param s	New length
	 */
	public void normalize(double l) {
		
		double oldL = Math.sqrt(this.x * this.x + this.y * this.y);
		
		this.x /= oldL;
		this.y /= oldL;
		
		this.x *= l;
		this.y *= l;
	}
	
	public void set(Vector other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	/**
	 * Rotate the vector relative to its current rotation
	 * 
	 * @param radians The number of radians to rotate by. Positive is CCW.
	 */
	public void rotate(double radians) {
		double length = this.length();
		double rotation = Math.atan2(this.y, this.x) + radians;
		
		this.x = length * Math.cos(rotation);
		this.y = length * Math.sin(rotation);
	}
	
	/**
	 * Calculate length of vector, and point that length
	 * in the direction given.
	 * 
	 * @param radians The direction to point in
	 */
	public void setRotation(double radians) {
		double length = this.length();
		
		this.x = length * Math.cos(radians);
		this.y = length * Math.sin(radians);
	}
	
	/**
	 * Finds length of the vector. Uses the square root function so
	 * this is a bit expensive
	 * 
	 * @return	Double length of this vector
	 */
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * Dot product of 2 vectors. Returns a new vector.
	 * 
	 * @param a		first vector in dot product
	 * @param b		second vector in dot product
	 */
	public static Vector mult(Vector a, Vector b) {
		Vector newVec = new Vector(a.gameX() * b.gameX(), a.gameX() * b.gameX());
		return newVec;
	}

}
