package sprites.ships;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import graphics.Camera;
import physics.PhysicsObject;
import physics.hitboxes.Circle;
import physics.hitboxes.Hitbox;
import sprites.projectiles.BasicProjectile;
import sprites.projectiles.FriendlyProjectile;
import sprites.weapons.BasicKinetic;
import sprites.weapons.ProjectileFactory;
import util.actions.Action;
import util.input.InputBinds;
import util.math.Point;
import util.math.Point.PointType;
import util.math.Vector;

public class BasicShip implements Ship {

	private static final int collisionHash = 0;
	
	/**
	 * Must be ordered, or else collision detection
	 * won't work (maybe)
	 */
	private static int[] wantedCollisionHashes = {2, 4};
	
	private Point position;
	private Vector engineDeltaPos;
	private Vector newDeltaPos;
	
	private double health = 150;
	private double shields = 0;
	private double armor = 0;
	public BasicKinetic equipedWeapon;
	
	private Circle hitbox;
	
	private double angle = -Math.PI/2;
	private double speed = 0;
	
	private static final double omega = 0.02;	
	private static final double accelS = 0.2;
	private static final double maxS = 12;
	private static final double minS = 0.1;
	private static final double frictionS = 1 - 0.01;
	
	/**
	 * Initialize new basic ship.
	 * 
	 * 
	 * @param gameX		X position in game coordinates
	 * @param gameY		Y position in game coordinates
	 */
	public BasicShip(double gameX, double gameY) {
		this.position = new Point(gameX, gameY, PointType.GAME);
		this.speed = 0;
		this.engineDeltaPos = new Vector(0, 0);
		this.newDeltaPos = new Vector(0, 0);
		this.hitbox = new Circle(10, 10, 10, this);
				
		this.equipedWeapon = new BasicKinetic(this, 0, 12, new ProjectileFactory() {
			@Override
			public BasicProjectile newProjectile(double x, double y, double dX, double dY) {
				return new FriendlyProjectile(x, y, dX, dY);
			}
		});
		
		InputBinds.reload.addDownAction(new Action(this.equipedWeapon) {
			@Override
			public void execute() {
				BasicKinetic wep = (BasicKinetic)this.obj;
				wep.reload();
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
		ctx.setPaint(Color.getHSBColor((float)health/200, 1, 1));
		drawShip(ctx, position.screenX(), position.screenY(), 1, angle);
		
		if (InputBinds.shoot.state) {
			this.equipedWeapon.shoot();
		}
	}

	@Override
	public void update(double dT) {
		double dAngle = (InputBinds.move.angle - this.angle - Math.PI) % (Math.PI*2);
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
		Camera.CAM_X = this.position.gameX();
		Camera.CAM_Y = this.position.gameY();
		
		if ( InputBinds.forward.state ) {
			this.speed += accelS;
		} else if ( InputBinds.back.state ) {
			this.speed -= 0.75 * accelS;
		}
		
		this.angle += dAngle;
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
				System.exit(0);
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
		this.equipedWeapon.timeTick();
	}
	
}
