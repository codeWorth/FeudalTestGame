package sprites.mobs;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import graphics.Camera;
import physics.PhysicsObject;
import physics.hitboxes.Circle;
import physics.hitboxes.Hitbox;
import sprites.statics.Tree;
import sprites.weapons.DamageSource;
import util.input.InputBinds;
import util.math.Point;
import util.math.Point.PointType;
import util.math.Vector;

public class Player implements Entity {
	
	private static int HASH_NUMBER = 0;
	private static int[] COLLISION_HASH = {};
	private static double FRICTION = 0.95;
	
	private long timeSinceDamage = 0;
	
	private double health = 100;
	public double healthRegen = 1;
	public double healthRegenDelay = 500;
	
	private double shields = 50;
	public double shieldRegen = 3;
	public double shieldRegenDelay = 300;
	
	private double armor = 50;
	public double armorRegen = 0;
	public double armorRegenDelay = 750;
	
	public double moveSpeed = 12;
	public double strafeSpeed = 7;
	
	private Point position = new Point(0, 0, PointType.GAME);
	
	private Vector velocity = new Vector(0, 0);
	private Vector realVelocity = new Vector(0, 0);
	private double rotation = Math.PI/2;
	
	private Circle hitbox;

	public Player() {
		this.hitbox = new Circle(10, 10, 10, this);
	}
	
	@Override
	public void draw(Graphics2D ctx) {
		double x = this.position.screenX();
		double y = this.position.screenY();
		double scale = 1;
		
		double x1 = x + scale * 12 * Math.cos(rotation); 
		double y1 = y + scale * 12 * Math.sin(rotation);
		
		double x2 = x + scale * 8 * Math.cos(rotation + Math.PI * 0.7);
		double y2 = y + scale * 8 * Math.sin(rotation + Math.PI * 0.7);
		
		double x3 = x + scale * 8 * Math.cos(rotation + Math.PI * 1.3);
		double y3 = y + scale * 8 * Math.sin(rotation + Math.PI * 1.3);
				
		Line2D.Double line1 = new Line2D.Double(x1, y1, x2, y2);
		Line2D.Double line2 = new Line2D.Double(x2, y2, x3, y3);
		Line2D.Double line3 = new Line2D.Double(x3, y3, x1, y1);
		
		ctx.draw(line1);
		ctx.draw(line2);
		ctx.draw(line3);
	}

	@Override
	public boolean inCameraWindow() {
		return true;
	}

	@Override
	public void update(double dT) {
		
		this.rotation = InputBinds.direction.angle;
		
		this.realVelocity.mult(FRICTION);
		
		if ( InputBinds.forward.state ) {
			this.realVelocity.setX(this.moveSpeed);
		} else if ( InputBinds.back.state ) {
			this.realVelocity.setX(-this.moveSpeed);
		}
		
		if ( InputBinds.left.state ) {
			this.realVelocity.setY(this.strafeSpeed);
		} else if ( InputBinds.right.state ) {
			this.realVelocity.setY(-this.strafeSpeed);
		}
		
		this.velocity.set(this.realVelocity);
		this.velocity.rotate(this.rotation);
		
		this.position.addMult(this.velocity, dT);
		Camera.CAM_X = this.position.gameX();
		Camera.CAM_Y = this.position.gameY();
		
	}

	@Override
	public boolean shouldCollideWithObject(PhysicsObject other) {
		return false;
	}

	@Override
	public Hitbox hitbox() {
		return this.hitbox;
	}

	@Override
	public int collisionHash() {
		return HASH_NUMBER;
	}

	@Override
	public void collideWithObject(PhysicsObject other) {
		
		if (other instanceof Tree) {
			this.realVelocity.mult(-1);
		}
		
	}

	@Override
	public Point position() {
		return this.position;
	}

	@Override
	public Vector velocity() {
		return this.velocity;
	}

	@Override
	public void addSpeed(Vector dSpeed) {
		dSpeed.rotate(-this.rotation);
		this.realVelocity.add(dSpeed);
	}

	@Override
	public long timeLeft() {
		return 1; //the player should never run out of "time"
	}

	@Override
	public void timeTick() {
		//do nothing
	}

	@Override
	public double health() {
		return this.health;
	}

	@Override
	public double shields() {
		return this.shields;
	}

	@Override
	public double armor() {
		return this.armor;
	}

	@Override
	public void takeDamage(DamageSource source) {
		// TODO Auto-generated method stub
		
	}

}
