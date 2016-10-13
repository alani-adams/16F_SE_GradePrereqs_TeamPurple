package implementation;

import java.util.ArrayList;
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
		return BuildIn(p,new ArrayList<Prerequisite>());
	}
	
	private static Prerequisite BuildIn(String p,ArrayList<Prerequisite> Memories)
	{
		//System.out.printf("%-32s:%s%n",p,Memories.toString());
		Matcher m = Pattern.compile("\\((.*?)\\)").matcher(p);
		if(m.find())
		{
			String NewP = p;
			do
			{
				String Mem ="%"+Memories.size();
				NewP = NewP.replaceFirst("\\("+Pattern.quote(m.group(1))+"\\)",Mem);
				Memories.add(BuildIn(m.group(1),Memories));
				//System.out.println(Memories);
			}
			while(m.find());
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
			return buildSimple(p,Memories);
		}
		else
		{//compound
			ArrayList<Prerequisite> SubPrereqs = new ArrayList<Prerequisite>();
			for(String s : data)
			{
				SubPrereqs.add(BuildIn(s,Memories));
			}
			return new CompoundPrerequisite(SubPrereqs,or);
		}
	}
	
	private static Prerequisite buildSimple(String p,ArrayList<Prerequisite> Memories)
	{
		if(p.charAt(0) == '%')
			return Memories.get(Integer.parseInt(p.substring(1)));
		
		switch(p)
		{
		case "FR":
			return ClassificationPrerequisite.FRESHMAN;
		case "SO":
			return ClassificationPrerequisite.SOPHOMORE;
		case "JR":
			return ClassificationPrerequisite.JUNIOR;
		case "SR":
			return ClassificationPrerequisite.SENIOR;
		case "GR":
			return ClassificationPrerequisite.GRADUATE;
		}
		
		if(p.contains(" "))
		{
			String[] data = p.split(" ");
			return new TestScorePrerequisite(p.substring(0, p.length()-data[data.length-1].length()-1),Integer.parseInt(data[data.length-1]));
		}
		else
			return new CoursePrerequisite(p,'C');
		}
	
	/**
	 * 
	 * @param stu
	 * @return
	 */
  public abstract boolean IsMetBy(Student stu);
}
