package implementation;

/**
 * @author Sebastian Snyder
 *
 */
public enum Classification
{
	FRESHMAN(2), SOPHOMORE(7), JUNIOR(22), SENIOR(67), GRADUATE(5);
	
	public final int Weight;
	private Classification(int W)
	{
		Weight = W;
	}
}
