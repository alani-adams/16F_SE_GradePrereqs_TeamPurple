package implementation;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Alani Peters, Sebastian Snyder
 */
public abstract class Prerequisite{
	
	/**
	 * Creates an instance of the prerequisite represented a given String
	 * @param p The String representation of the prerequisite
	 * @return the Prerequisite that the String represented
	 */
	public static Prerequisite Build(String p)
	{
		HashMap<String,Prerequisite> Memories = null;
		Matcher m = Pattern.compile("\\((.*?)\\)").matcher(p);
		if(m.matches())
		{
			m.reset();
			Memories = new HashMap<String,Prerequisite>();
			String NewP = p;
			int count = 0;
			while(m.find())
			{
				String Mem ="%"+(count++);
				NewP = NewP.replaceFirst("\\("+m.group(1)+"\\)",Mem);
				Memories.put(Mem, Build(m.group(1)));
			}
			p = NewP;
		}
		String[] data = p.split(" or ");
		boolean or = true;
		if(data.length == 1)
		{
			data = p.split(" and ");
			or = false;
		}
		if(data.length == 1)
		{// Not compound
			if(Memories != null && Memories.containsKey("%0"))
				return Memories.get("%0");
			else
			{
				if(p.contains(" "))
				{
					data = p.split(" ");
					return new TestScorePrerequisite(p.substring(0, p.length()-data[data.length-1].length()-1),Integer.parseInt(data[data.length-1]));
				}
				else
					return new CoursePrerequisite(p,'C');
			}
		}
		else
		{//compound
			Prerequisite A = (data[0].charAt(0) == '%')? Memories.get(data[0]):Build(data[0]);
			Prerequisite B = (data[1].charAt(0) == '%')? Memories.get(data[1]):Build(data[1]);
			CompoundPrerequisite P = new CompoundPrerequisite(A,B,or);
			for(int i = 2;i < data.length;i++)
			{
				P = new CompoundPrerequisite(P,(data[i].charAt(0) == '%')? Memories.get(data[i]):Build(data[i]),or);
			}
			return P;
		}
		//return null;
	}
	
	/**
	 * 
	 * @param stu
	 * @return
	 */
  public abstract boolean IsMetBy(Student stu);
}
