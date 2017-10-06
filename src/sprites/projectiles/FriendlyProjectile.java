package sprites.projectiles;

public class FriendlyProjectile extends BasicProjectile {

	public static int[] wantedCollisionHashes = {2, 3};
	
	public FriendlyProjectile(double x, double y, double dX, double dY) {
		super(x, y, dX, dY);
	}

	@Override
	public int collisionHash() {
		return 1;
	}

	@Override
	public int[] wantedCollisionHashes() {
		return wantedCollisionHashes;
	}	
	
}
