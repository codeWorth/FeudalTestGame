package sprites.weapons;

import main.World;
import sprites.projectiles.BasicProjectile;
import sprites.ships.Ship;
import util.math.Vector;

public class BasicKinetic implements Weapon {

	private ProjectileFactory projFactory;
	private Ship owner;
	private int time = 0;
	
	private boolean reloadingClip = false;
	
	/**
	 * x is the angle offset
	 * y is the distance offset
	 */
	private Vector offset;
	
	private int ammoInClip = 5;
	private int totalAmmo = 100;
	
	/**
	 * Create the weapon at some position with an owner ship
	 * 
	 * @param owner		Ship that owns this
	 * @param offsetA	Angle offset in radians
	 * @param offsetD	Distance offset in screen units
	 * @param proj		A projectile factory that generates the correct kind of projectile
	 */
	public BasicKinetic(Ship owner, double offsetA, double offsetD, ProjectileFactory proj) {
		this.owner = owner;
		this.offset = new Vector(offsetA, offsetD);
		this.projFactory = proj;
	}
	
	@Override
	public void timeTick() {
		time -= 1;
		
		if (time == 0 && reloadingClip) {
			reloadingClip = false;
			
			int dAmmo = totalAmmo;
			totalAmmo -= this.maxAmmoPerClip();
			if (totalAmmo < 0) {
				totalAmmo = 0;
			}
			
			dAmmo -= totalAmmo;
			this.ammoInClip = dAmmo;
		}
	}
	
	@Override
	public void shoot() {
		if (this.ammoInClip <= 0) {
			return;
		}
		if (this.time > 0) {
			return;
		}
		
		this.ammoInClip -= 1;
		
		double dX = (double)Math.cos(this.owner.angle());
		double dY = (double)Math.sin(this.owner.angle());
		
		double offX = offset.gameY() * (double)Math.cos(this.owner.angle() + offset.gameX());
		double offY = offset.gameY() * (double)Math.sin(this.owner.angle() + offset.gameX());
		
		double newX = this.owner.position().gameX() + offX;
		double newY = this.owner.position().gameY() + offY;
		double newDx = (10 + (double)this.owner.speed()) * dX;
		double newDy = (10 + (double)this.owner.speed()) * dY;
		
		BasicProjectile proj = this.projFactory.newProjectile(newX, newY, newDx, newDy);
		World.add(proj);
		
		time = this.shotTime();
		
		if (this.ammoInClip <= 0) {
			reload();
		}
	}
	
	@Override
	public void reload() {
		if (this.ammoInClip < this.maxAmmoPerClip() && this.totalAmmo > 0) {
			time = this.clipReloadTime();
			reloadingClip = true;
		}
	}

	@Override
	public int maxAmmoPerClip() {
		return 5;
	}

	@Override
	public int ammoInClip() {
		return this.ammoInClip;
	}

	@Override
	public int totalAmmo() {
		return this.totalAmmo;
	}

	@Override
	public int clipReloadTime() {
		return 20;
	}

	@Override
	public int shotTime() {
		return 2;
	}

	@Override
	public double damagePerShotMultiplier() {
		return 1;
	}

	@Override
	public double armorPenMultiplier() {
		return 1;
	}

	@Override
	public double shieldPenMultiplier() {
		return 1;
	}

}
