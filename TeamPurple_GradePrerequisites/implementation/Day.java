package implementation;

/**
 * An enumeration representing all of the days of the week
 * @author Sebastian Snyder
 */
public enum Day
{
	SUNDAY,
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY;

	public static Day getFromChar(char charAt)
	{
		char c = Character.toUpperCase(charAt);
		switch(c)
		{
		case 'U':
			return SUNDAY;
		case 'M':
			return MONDAY;
		case 'T':
			return TUESDAY;
		case 'W':
			return WEDNESDAY;
		case 'R':
			return THURSDAY;
		case 'F':
			return FRIDAY;
		case 'S':
			return SATURDAY;
		default:
			throw new IllegalArgumentException(c+" recieved, but one of (U,M,T,W,R,F,S) expected.");
		}
	}
}
