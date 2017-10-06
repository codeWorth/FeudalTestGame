package physics.hitboxes;

import physics.Physics;
import physics.PhysicsObject;
import util.math.Point;
import util.math.Point.PointType;
import util.math.Vector;

public class Circle implements Hitbox {

	public double radius;
	public Vector offset;
	public PhysicsObject owner;
	
	public Circle(double r, double xOff, double yOff, PhysicsObject owner) {
		this.radius = r * Physics.resolution;
		this.offset = new Vector(xOff * Physics.resolution, yOff * Physics.resolution);
		this.owner = owner;
	}
	
	@Override
	public boolean touching(Hitbox other) {
		if (other instanceof Circle) {
			
			Circle otherCircle = (Circle)other;
			Point myCenter = this.center();
			Point theirCenter = otherCircle.center();
			double dist = myCenter.distance2(theirCenter);
			double maxDist = (this.radius + otherCircle.radius) * (this.radius + otherCircle.radius);
		
			if (dist <= maxDist) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public Point center() {
		Point center = new Point(offset.gameX(), offset.gameY(), PointType.GAME);
		center.add(owner.position());
		return center;
	}

}
