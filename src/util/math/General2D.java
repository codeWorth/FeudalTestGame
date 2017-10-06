package util.math;

public interface General2D {

	public double screenX();
	public double screenY();
	
	public double gameX();
	public double gameY();
	
	public void add(General2D other);
	public void subtract(General2D other);
	public void mult(double scalar);
}
