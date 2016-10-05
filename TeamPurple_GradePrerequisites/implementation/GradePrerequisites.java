//
//  Compile with:
//      javac -cp "jars/*;." step_definitions/BaseTest.java implementation/BaseClass.java
//      java -cp "jars/*;." cucumber.api.cli.Main -p pretty --snippets camelcase -g step_definitions features
//
package implementation;

import java.io.FileNotFoundException;

/**
 * A class that contains the main method that allows this program
 * to run as a command line tool.
 * 
 * @author Sebastian Snyder
 */
public class GradePrerequisites
{
	public static CSV CourseData;
	
	public static void init()
	{
		try
		{
			CourseData = CSV.openRows(20000,"cs374_anon.csv");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static void main(String[] args)
	{
		//if(args.length == 2)
		{
			init();
			
			CourseData.printToStream(System.out,0,50);
			
			//Student S = new Student(args[0]);
			//System.out.println(S.CanTakeCourse(Course.GetFromCRN(args[1])));
		}
		//else
		//	System.out.println("This program requires exactly two things to run: A BannerID and a CRN.");
	}
}
