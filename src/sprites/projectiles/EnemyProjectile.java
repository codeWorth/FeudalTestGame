package sprites.projectiles;

public class EnemyProjectile extends BasicProjectile {

	private static int[] wantedCollisionHashes = {0, 2};
	
	public EnemyProjectile(double x, double y, double dX, double dY) {
		super(x, y, dX, dY);
	}

	@Override
	public int collisionHash() {
		return 4;
	}

	@Override
	public int[] wantedCollisionHashes() {
		return wantedCollisionHashes;
	}
	
}
