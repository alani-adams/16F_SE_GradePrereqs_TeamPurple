//
//  Compile with:
//      javac -cp "jars/*;." step_definitions/BaseTest.java implementation/BaseClass.java
//      java -cp "jars/*;." cucumber.api.cli.Main -p pretty --snippets camelcase -g step_definitions features
//
package implementation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that contains the main method that allows this program
 * to run as a command line tool.
 * 
 * @author Sebastian Snyder
 */
public class GradePrerequisites
{
	public CSV StudentData;
	private HashMap<String,Student> Students;
	
	
	public GradePrerequisites()
	{
		init();
		Students = new HashMap<String,Student>();
	}
	
	public void init()
	{
		try
		{
			//StudentData = CSV.open("StudentData.csv");
			//StudentData = CSV.open("cs374_anon.csv");
			//StudentData = CSV.open("cs374_anon-modified.csv");
			StudentData = CSV.openColumns("cs374_anon-modified.csv",new String[]{"CRN","Grade Code","Banner ID"});
			{
				CSV CourseData = CSV.open("CourseData.csv");
				for(int i = 0;i < CourseData.rowCount();i++)
				{
					String CourseCode = CourseData.getDataPoint("Course Code",i);
					String PrereqString = CourseData.getDataPoint("Prerequisites", i);
					//System.out.println("{"+CourseCode+":"+PrereqString+"}");
					Course.RegisterCourse(CourseCode,PrereqString.equals("")?null:PrereqString);
				}
				//CourseData.printToStream(System.out);
			}
			
			{
				CSV CRNData = CSV.open("CRNData.csv");
				for(int i = 0;i < CRNData.rowCount();i++)
				{
					Course.RegisterCRN(CRNData.getDataPoint("CRN",i),CRNData.getDataPoint("Course Code", i));
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static void main(String[] args)
	{
		/*
		if(args.length == 2)
			System.out.println(new GradePrerequisites().run(args[0],args[1]));
		else
			System.out.println("This program requires exactly two things to run: A BannerID and a CRN.");
		//*/
		GradePrerequisites G = new GradePrerequisites();

		//Matcher m = Pattern.compile("\\((.*?)\\)").matcher("(ACT Science 20 or SAT 950) and BIOL101");
		
		//System.out.println(m.find());
		//Course.RegisterCourse("BIOL261","NUTR221 and NUTR322 and (CHEM112 and CHEM114) or (CHEM132 and CHEM134)");
		
	}
	
	public boolean run(String banner,String crn)
	{
		//CourseData.printToStream(System.out,0,50);
		
		Student S = Student(banner);
		return S.CanTakeCourse(Course.GetFromCRN(crn));
	}
	
	public Student Student(String banner)
	{
		if(Students.containsKey(banner))
			return Students.get(banner);
		else
		{
			Student S = new Student(banner);
			for(int i = 0;i < StudentData.rowCount();i++)
			{
				if(StudentData.getDataPoint("Banner ID", i).equals(banner))
				{
					String G = StudentData.getDataPoint("Grade Code", i);
					String CRN = StudentData.getDataPoint("CRN", i);
					S.TakeCourse(Course.GetFromCRN(CRN, G.length()==0? null :(G.charAt(0)) ));
				}
			}
			Students.put(banner, S);
			return S;
		}
	}
}
