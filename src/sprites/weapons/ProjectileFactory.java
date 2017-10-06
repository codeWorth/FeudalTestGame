package sprites.weapons;

import sprites.projectiles.BasicProjectile;

public interface ProjectileFactory {

	public BasicProjectile newProjectile(double x, double y, double dX, double dY);
	
}
