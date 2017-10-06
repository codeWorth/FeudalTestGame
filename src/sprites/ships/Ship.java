package sprites.ships;

import graphics.Drawable;
import physics.PhysicsObject;
import physics.Timeout;

public interface Ship extends Drawable, PhysicsObject, Timeout {

	public double health();
	public double shields();
	public double armor();
	
	public double angle();
	public double speed();
	
}
