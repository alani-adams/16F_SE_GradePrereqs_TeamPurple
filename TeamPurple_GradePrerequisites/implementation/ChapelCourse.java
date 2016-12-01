package implementation;

/**
 * @author Sebastian Snyder
 *
 */
public class ChapelCourse extends Course
{
	
	/**
	 * @param termcode
	 * @param course
	 * @param p
	 */
	public ChapelCourse()
	{
		super("ALL", "CHAPEL", null, null);
		setClassPeriod(Day.MONDAY, 1100, 1130);
		setClassPeriod(Day.TUESDAY, 1100, 1130);
		setClassPeriod(Day.WEDNESDAY, 1100, 1130);
		setClassPeriod(Day.THURSDAY, 1100, 1130);
		setClassPeriod(Day.FRIDAY, 1100, 1130);
	}
	
}
