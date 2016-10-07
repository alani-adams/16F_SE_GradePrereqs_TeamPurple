package implementation;

import java.util.ArrayList;
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
			if(p.charAt(0) == '%')
			{
				return Memories.get(Integer.parseInt(p.substring(1)));
			}
			else if(p.contains(" "))
			{
				data = p.split(" ");
				return new TestScorePrerequisite(p.substring(0, p.length()-data[data.length-1].length()-1),Integer.parseInt(data[data.length-1]));
			}
			else
				return new CoursePrerequisite(p,'C');
		}
		else
		{//compound
			ArrayList<Prerequisite> SubPrereqs = new ArrayList<Prerequisite>();
			for(String s : data)
			{
				SubPrereqs.add(BuildIn(s,Memories));
			}
			return new CompoundPrerequisite(SubPrereqs,or);
			/*
			CompoundPrerequisite P = new CompoundPrerequisite(BuildIn(data[0],Memories),BuildIn(data[1],Memories),or);
			for(int i = 2;i < data.length;i++)
			{
				P = new CompoundPrerequisite(P,BuildIn(data[i],Memories),or);
			}
			return P;
			*/
			
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
