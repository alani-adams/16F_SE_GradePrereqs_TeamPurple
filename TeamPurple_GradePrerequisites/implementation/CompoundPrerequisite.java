package implementation;

/**
 * Defines a prerequisite defined by two other prerequisites
 * @author Sebastian Snyder
 */
public class CompoundPrerequisite extends Prerequisite
{
	private boolean isOr;
	Prerequisite first;
	Prerequisite second;
	
	/**
	 * Creates a CompoundPrerequisite that must meet one or both
	 * prerequisites.
	 * @param a The first prerequisite
	 * @param b The second prerequisite
	 * @param or Whether or not this prerequisite is met if only one of a and b are met.
	 * If false is passed in, both must be met.
	 */
	public CompoundPrerequisite(Prerequisite a, Prerequisite b, boolean or)
	{
		isOr = or;
		first = a;
		second = b;
	}

	@Override
	public boolean IsMetBy(Student stu)
	{
		return isOr?( first.IsMetBy(stu) || second.IsMetBy(stu) ):( first.IsMetBy(stu) && first.IsMetBy(stu) ); 
	}

}
