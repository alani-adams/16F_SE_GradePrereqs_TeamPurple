//
//  Compile with:
//      javac -cp "jars/*;." step_definitions/BaseTest.java implementation/BaseClass.java
//      java -cp "jars/*;." cucumber.api.cli.Main -p pretty --snippets camelcase -g step_definitions features
//
package implementation;


/**
 * A class that contains the main method that allows this program
 * to run as a command line tool.
 * 
 * @author Sebastian Snyder
 */
public class GradePrerequisites
{
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			//Parse packaged data here
			
			Student S = new Student(args[0]);
			System.out.println(S.CanTakeCourse(Course.GetFromCRN(args[1])));
		}
		else
			System.out.println("This program requires exactly two things to run: A BannerID and a CRN.");
	}
}
