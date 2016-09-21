//
//  THIS IS A SKELETON. ON EACH NEW ECLIPSE PROJECT:
//    Edit the batch file to reflect the actual test class and base class files.
//    Edit the comment below to match the batch file.
//    Properly set Cucumber's run configuration.
//    Remove this checklist comment.
//    

//
//  Compile with:
//      javac -cp "jars/*;." step_definitions/BaseTest.java implementation/BaseClass.java
//      java -cp "jars/*;." cucumber.api.cli.Main -p pretty --snippets camelcase -g step_definitions features
//
package implementation;


/**
 * 
 * @author Sebastian Snyder
 */
public class GradePrerequisites
{
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			Student S = new Student();
			S.SetBanner(args[0]);
			System.out.println(S.CanTakeCourse(args[1]));
		}
		else
			System.out.println("This program requires exactly two things to run: A BannerID and a CRN.");
	}
}
