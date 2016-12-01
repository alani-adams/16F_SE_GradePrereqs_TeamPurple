package implementation;

/**
 * A class that holds what days and times a course meets.
 * 
 * @author Sebastian Snyder
 */
public class ScheduleData
{
	private final TimeRange[] Data;
	
	public ScheduleData()
	{
		Data = new TimeRange[Day.values().length];
	}
	
	public void setData(Day D, short StartTime, short EndTime)
	{
		if(Data[D.ordinal()] == null)
		{
			TimeRange T = new TimeRange();
			T.StartTime = StartTime;
			T.EndTime = EndTime;
			Data[D.ordinal()] = T;
		}
		else
		{
			throw new IllegalStateException("Course Data for "+D.name()+" defined twice.");
		}
	}

	public void setData(Day day, int St, int Et) {
		setData(day,(short)St,(short)Et);
	}

	public int getFirstStartTime()
	{
		for(int i = 0;i < Day.values().length;i++)
		{
			if(Data[i] != null)
				return Data[i].StartTime;
		}
		throw new IllegalStateException("No start time found.");
	}
	
	public boolean SlotFree(Day D, short ST, short ET)
	{
		TimeRange T = Data[D.ordinal()];
		if(T == null)
			return true;
		else
			return (T.StartTime > ET) || (T.EndTime < ST);
	}

	public int getFirstEndTime()
	{
		for(int i = 0;i < Day.values().length;i++)
		{
			if(Data[i] != null)
				return Data[i].EndTime;
		}
		throw new IllegalStateException("No end time found.");
	}
}

class TimeRange
{
	public short StartTime;
	public short EndTime;
}

