package physics;

import physics.hitboxes.Hitbox;
import util.math.Point;
import util.math.Vector;

public interface PhysicsObject {

	/**
	 * Update the internal data of this object, called every physics tick.
	 */
	public void update(double dT);
	
	/**
	 * Return whether or not this physics object should collide with
	 * the given other object. 
	 * 
	 * @param other		the other physics object that is being checked against
	 * @return 			true if should collide, false if should not collide
	 */
	public boolean shouldCollideWithObject(PhysicsObject other);
	
	/**
	 * This hitbox will be used for collision detection. It should
	 * not be changed.
	 * 
	 * @return This object's hitbox object.
	 */
	public Hitbox hitbox();
	
	/**
	 * The hash that will be used to collide with other PhysicsObjects.
	 * Many objects can (and should) have the same hash
	 * 
	 * @return	The hash
	 */
	public int collisionHash();
	
	/**
	 * Change this object's properties based on the collision.
	 * The other object should not be modified. If the other object
	 * also needs to change its properties, then that change should be done
	 * in the other object's 'collideWithObject' method
	 * 
	 * @param other	The other object that just collided with this object
	 */
	public void collideWithObject(PhysicsObject other);
	
	public Point position();
	
	/**
	 * Should be in Cartesian, not polar coordinates
	 * 
	 * @return	The speed, as it was seen last update
	 */
	public Vector velocity();
	
	/**
	 * Adds a speed to this physics object. This speed change
	 * shouldn't take affect until the 'update' function is called.
	 * 
	 * @param dSpeed
	 */
	public void addSpeed(Vector dSpeed);
	
}
