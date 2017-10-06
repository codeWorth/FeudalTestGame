package sprites.statics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import graphics.Camera;
import graphics.Drawable;
import physics.PhysicsObject;
import physics.hitboxes.Circle;
import physics.hitboxes.Hitbox;
import util.math.Point;
import util.math.Point.PointType;
import util.math.Vector;

public class Tree implements Drawable, PhysicsObject {

	public static final int collisionHash = 2;
	
	/**
	 * Must be ordered, or else collision detection
	 * won't work (maybe)
	 */
	public static int[] wantedCollisionHashes = {1};
	
	private Point position;
	private double radius;
	
	private Circle hitbox;
	
	private Vector speed = new Vector(0, 0); //maybe planets can move eventually?
	
	public Tree(double x, double y, double radius) {
		this.position = new Point(x, y, PointType.GAME);
		this.radius = radius;
		
		this.hitbox = new Circle(this.radius, this.radius, this.radius, this);
	}
	
	@Override
	public void update(double dT) {
		
	}

	@Override
	public void draw(Graphics2D ctx) {
		
		ctx.setPaint(Color.GREEN);
		
		Ellipse2D.Double outline = new Ellipse2D.Double(position.screenX(), position.screenY(), this.radius*2, this.radius*2);
		ctx.fill(outline);
	}
	
	@Override
	public boolean inCameraWindow() {
		double x1 = -this.radius*2;
		double y1 = -this.radius*2;
		double x2 = Camera.CAM_WIDTH;
		double y2 = Camera.CAM_HEIGHT;
		
		double myX = position.screenX();
		double myY = position.screenY();
		
		if (myX < x1 || myX > x2 || myY < y1 || myY > y2) {
			return false;
		}
		
		return true;
		
	}

	@Override
	public boolean shouldCollideWithObject(PhysicsObject other) {
		for (int hash : wantedCollisionHashes) {
			if (hash == other.collisionHash()) {
				return this.hitbox.touching(other.hitbox());
			}
		}
		
		return false;
	}

	@Override
	public Hitbox hitbox() {
		return this.hitbox;
	}

	@Override
	public int collisionHash() {
		return collisionHash;
	}

	@Override
	public void collideWithObject(PhysicsObject other) {
	}

	@Override
	public Point position() {
		return this.position;
	}

	@Override
	public Vector velocity() {
		return this.speed;
	}

	@Override
	public void addSpeed(Vector dSpeed) {
		System.out.println("You can't move me, I'm uNMovAbLE!1!!1!!");
	}

}
