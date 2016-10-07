package implementation;

import java.util.ArrayList;

/**
 * Defines a prerequisite defined by a list of other prerequisites
 * @author Sebastian Snyder
 */
public class CompoundPrerequisite extends Prerequisite
{
	private boolean isOr;
	ArrayList<Prerequisite> Conditions;
	
	/**
	 * Creates a CompoundPrerequisite that must meet one or all of its subprerequisites
	 * @param Prereq The list of subprerequisites that constitute this prerequisite.
	 * @param or Whether or not this prerequisite is met if only one subprerequisite is met.
	 * If false is passed in, all must be met.
	 */
	public CompoundPrerequisite(ArrayList<Prerequisite> Prereqs, boolean or)
	{
		isOr = or;
		Conditions = Prereqs;
	}

	@Override
	public boolean IsMetBy(Student stu)
	{
		for(Prerequisite p : Conditions)
		{
			if((!isOr) ^ p.IsMetBy(stu))
				return isOr;
		}
		return !isOr;
	}
	
	@Override
	public String toString()
	{
		String s = "["+Conditions.get(0);
		for(int i = 1;i < Conditions.size();i++)
		{
			s += isOr ? " or " : " and ";
			s += Conditions.get(i);
		}
		return s+"]";
	}

}
