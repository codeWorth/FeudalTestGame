package sprites.mobs;

import graphics.Drawable;
import physics.PhysicsObject;
import physics.Timeout;
import sprites.weapons.DamageSource;

public interface Entity extends Drawable, PhysicsObject, Timeout {

	public double health();
	public double shields();
	public double armor();
	
	public void takeDamage(DamageSource source);
	
}
