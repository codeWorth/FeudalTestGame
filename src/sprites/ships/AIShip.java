package sprites.ships;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Random;

import main.World;
import physics.PhysicsObject;
import physics.hitboxes.Circle;
import physics.hitboxes.Hitbox;
import sprites.projectiles.BasicProjectile;
import sprites.projectiles.EnemyProjectile;
import sprites.weapons.BasicKinetic;
import sprites.weapons.ProjectileFactory;
import util.math.Point;
import util.math.Point.PointType;
import util.math.Vector;

public class AIShip implements Ship {

	private static final double shootRange = (200*5)*(200*5);
	private static final double bailRange = (40*5)*(40*5);
	
	private static Random rand = new Random();

	private BasicKinetic weapon;
	private boolean goingToPlayer = true;
	
	private static final int collisionHash = 3;
	
	/**
	 * Must be ordered, or else collision detection
	 * won't work (maybe)
	 */
	public static int[] wantedCollisionHashes = {1};
	
	private Point position;
	private Vector engineDeltaPos;
	private Vector newDeltaPos;
	
	private double health = 5;
	private double shields = 0;
	private double armor = 0;
	
	private Circle hitbox;
	
	private double angle = -Math.PI/2;
	private double speed = 0;
	
	private static final double omega = 0.01;
	private static final double accelS = 0.2;
	private static final double maxS = 9;
	private static final double minS = 0.1;
	private static final double frictionS = 1 - 0.01;
	
	/**
	 * Initialize new basic ship.
	 * 
	 * 
	 * @param gameX		X position in game coordinates
	 * @param gameY		Y position in game coordinates
	 */
	public AIShip(double gameX, double gameY) {
		this.position = new Point(gameX, gameY, PointType.GAME);
		this.speed = 0;
		this.engineDeltaPos = new Vector(0, 0);
		this.newDeltaPos = new Vector(0, 0);
		this.hitbox = new Circle(12, 12, 12, this);
				
		this.weapon = new BasicKinetic(this, 0, 12, new ProjectileFactory() {
			@Override
			public BasicProjectile newProjectile(double x, double y, double dX, double dY) {
				return new EnemyProjectile(x, y, dX, dY);
			}
		});
	}
	
	private void drawShip(Graphics2D ctx, double x, double y, double scale, double rot) {
		double x1 = x + scale * 12 * Math.cos(rot); 
		double y1 = y + scale * 12 * Math.sin(rot);
		
		double x2 = x + scale * 8 * Math.cos(rot + Math.PI * 0.7);
		double y2 = y + scale * 8 * Math.sin(rot + Math.PI * 0.7);
		
		double x3 = x + scale * 8 * Math.cos(rot + Math.PI * 1.3);
		double y3 = y + scale * 8 * Math.sin(rot + Math.PI * 1.3);
				
		Line2D.Double line1 = new Line2D.Double(x1, y1, x2, y2);
		Line2D.Double line2 = new Line2D.Double(x2, y2, x3, y3);
		Line2D.Double line3 = new Line2D.Double(x3, y3, x1, y1);
		
		ctx.draw(line1);
		ctx.draw(line2);
		ctx.draw(line3);
	}
	
	@Override
	public void draw(Graphics2D ctx) {
		ctx.setPaint(Color.WHITE);
		drawShip(ctx, position.screenX(), position.screenY(), 1, angle);
		
		if ( this.goingToPlayer ) {
			this.weapon.shoot();
		}
	}

	@Override
	public void update(double dT) {
		double playerDx = World.player.position().gameX() - this.position.gameX();
		double playerDy = World.player.position().gameY() - this.position.gameY();
		double angleToPlayer = Math.atan2(playerDy, playerDx) + randBetween(-1, 1);
		
		if ( !this.goingToPlayer ) {
			angleToPlayer += Math.PI;
		}
		
		double dAngle = (angleToPlayer - this.angle - Math.PI) % (Math.PI*2);
		if (dAngle < 0) {
			dAngle += Math.PI;
		} else {
			dAngle -= Math.PI;
		}
		
		if (dAngle > omega) {
			dAngle = omega;
		} else if (dAngle < -omega) {
			dAngle = -omega;
		}
				
		if ( this.speed > 0 ) {
			if ( this.speed > maxS) {
				this.speed = maxS;
			} else if ( this.speed < minS ) {
				this.speed = 0;
			}
		} else {
			if ( this.speed < -maxS) {
				this.speed = -maxS;
			} else if ( this.speed > -minS ) {
				this.speed = 0;
			}
		}
		
		this.speed *= frictionS;
		
		engineDeltaPos.setX( (double)(this.speed * Math.cos(this.angle)) );
		engineDeltaPos.setY( (double)(this.speed * Math.sin(this.angle)) );	
		newDeltaPos.setXY(engineDeltaPos.gameX(), engineDeltaPos.gameY());
		
		this.position.addMult(engineDeltaPos, dT);
		this.speed += accelS + randBetween(-0.5, 0.5);
		this.angle += dAngle;
		
		double dist = playerDx*playerDx + playerDy*playerDy + randBetween(-300, 300);
		if (dist > shootRange) {
			this.goingToPlayer = true;
		} else if ( dist < bailRange && this.goingToPlayer ){
			this.goingToPlayer = false;
		}
	}
	
	@Override
	public boolean inCameraWindow() {
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
		if (other instanceof BasicProjectile) {
			this.health -= 5;
			if (this.health <= 0) {
				World.remove(this);
				World.score += 100;
				World.enemyShips--;
			}	
		}
	}

	@Override
	public Point position() {
		return this.position;
	}

	@Override
	public Vector velocity() {
		return this.engineDeltaPos;
	}

	@Override
	public void addSpeed(Vector dSpeed) {
		this.newDeltaPos.add(dSpeed);
		
		this.angle = Math.atan2(this.newDeltaPos.gameY(), this.newDeltaPos.gameX());
		this.speed = this.newDeltaPos.length();
		
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
	public double angle() {
		return this.angle;
	}

	@Override
	public double speed() {
		return this.speed;
	}

	@Override
	public long timeLeft() {
		return 1; //never end
	}

	@Override
	public void timeTick() {
		this.weapon.timeTick();
	}
	
	private static double randBetween(double min, double max) {
		double randNum = rand.nextDouble() * (max - min);
		return randNum + min;
	}
	
}
