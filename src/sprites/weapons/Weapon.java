package sprites.weapons;

public interface Weapon {

	public void shoot();
	
	/**
	 * Trigger once every 10 milliseconds
	 */
	public void timeTick();
	
	/**
	 * Does nothing if your clip is full.
	 * Automatically called when the last bullet in a clip is shot
	 */
	public void reload();
	
	public int maxAmmoPerClip();
	public int ammoInClip();
	public int totalAmmo();
	
	/**
	 * Time it takes to refill one clip
	 * @return	Time in centiseconds
	 */
	public int clipReloadTime();
	
	/**
	 * Time between every shot, even those in the clip
	 * @return	Time in centiseconds
	 */
	public int shotTime();
	
	public double damagePerShotMultiplier();
	
	/**
	 * A lower number means more armor penetration. As this number decreases,
	 * less of the damage from this weapon is absorbed by enemy armor
	 * 
	 * @return A double which represents a percent, usually between 0 and 1
	 */
	public double armorPenMultiplier();
	
	/**
	 * A lower number means more shield penetration. As this number decreases,
	 * less of the damage from this weapon is absorbed by enemy shields
	 * 
	 * @return A double which represents a percent, usually between 0 and 1
	 */
	public double shieldPenMultiplier();
	
}
