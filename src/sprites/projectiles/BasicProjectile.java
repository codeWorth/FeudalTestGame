package sprites.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import graphics.Camera;
import graphics.Drawable;
import main.World;
import physics.PhysicsObject;
import physics.Timeout;
import physics.hitboxes.Circle;
import physics.hitboxes.Hitbox;
import util.math.Point;
import util.math.Point.PointType;
import util.math.Vector;

public abstract class BasicProjectile implements Drawable, PhysicsObject, Timeout {
	
	public abstract int[] wantedCollisionHashes();
	
	private Point position;
	private Vector speed;
	private Vector newSpeed;
	
	private double width = 5;
	private double height = 5;
	
	private Circle hitbox;
	
	/**
	 * In milliseconds
	 */
	private long time;
	
	@Override
	public void timeTick() {
		this.time -= 10;
	}
	
	@Override
	public long timeLeft() {
		return this.time;
	}
	
	public BasicProjectile(double x, double y, double dX, double dY) {
		this.position = new Point(x, y, PointType.GAME);
		this.speed = new Vector(dX, dY);		
		this.newSpeed = new Vector(dX, dY);
		this.time = 200;
		
		double radius = width/2;
		if (height > width) {
			radius = height/2;
		}
		this.hitbox = new Circle(radius, this.width/2, this.height/2, this);
	}
	
	@Override
	public void update(double dT) {
		this.speed.setXY(this.newSpeed.gameX() * dT, this.newSpeed.gameY() * dT);		
		this.position.add(this.speed);
	}

	@Override
	public void draw(Graphics2D ctx) {
		ctx.setPaint(Color.RED);
				
		Ellipse2D.Double outline = new Ellipse2D.Double(position.screenX(), position.screenY(), this.width, this.height);
		ctx.fill(outline);
	}

	@Override
	public boolean inCameraWindow() {
		double x1 = -this.width;
		double y1 = -this.height;
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
		for (int hash : wantedCollisionHashes()) {
			if (hash == other.collisionHash()) {
				return this.hitbox.touching(other.hitbox());
			}
		}
		
		return false;
	}

	@Override
	public void collideWithObject(PhysicsObject other) {
		World.remove(this);
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
		this.newSpeed.add(dSpeed);
	}

	@Override
	public Hitbox hitbox() {
		return this.hitbox;
	}

}
