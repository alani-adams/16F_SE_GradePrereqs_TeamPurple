//
//  Compile with:
//      javac -cp "jars/*;." step_definitions/BaseTest.java implementation/BaseClass.java
//      java -cp "jars/*;." cucumber.api.cli.Main -p pretty --snippets camelcase -g step_definitions features
//
package implementation;

import java.io.IOException;
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
	private String MyBan;
	
	public GradePrerequisites()
	{
		this(null);
	}
	
	public GradePrerequisites(String Banner)
	{
		MyBan = Banner;
		init(Banner);
		Students = new HashMap<String,Student>();
	}
	
	public void init(String Banner)
	{
		try
		{
			//CSV.openColumns("csv.csv",new String[] {"Column1"});
			///*
			String[] ColumnNames = {"Subject Code", "Course Number", "Grade Code", "Banner ID", "Class Code"};
			StudentData = CSV.openColumns("cs374_anon-modified.csv",ColumnNames);
			//StudentData.printToStream(System.out,8,0,50);
			//StudentData = CSV.open("cs374_anon-modified.csv");
			//*
			{
				CSV CourseData = CSV.open("CourseData.csv");
				for(int i = 0;i < CourseData.rowCount();i++)
				{
					String CourseCode = CourseData.getDataPoint("Course Code",i);
					String PrereqString = CourseData.getDataPoint("Prerequisites", i);
					Course.RegisterCourse(CourseCode,PrereqString.equals("")?null:PrereqString);
				}
			}
			//Set Scoping Level
			/*
			{
				CSV CRNData = CSV.open("CRNData.csv");
				for(int i = 0;i < CRNData.rowCount();i++)
				{
					String Code = CRNData.getDataPoint("Course Code", i);
					String crn = CRNData.getDataPoint("CRN",i);
					Course.RegisterCRN(crn,Code);
					//if(Code.equals("CS120"))
					//	System.out.println(crn+"="+Code);
				}
			}
			//*/
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static void main(String[] args)
	{
		//*
		try
		{
			if(args.length == 2)
				System.out.println("Prerequisites "+(new GradePrerequisites(args[0]).run(args[1])?"are":"not")+" met by student " +args[0]+" for course "+args[1]);
			else
				System.out.println("This program requires exactly two things to run: A BannerID and a Course Code.");
		}
		catch(IllegalStateException e)
		{
			System.out.println(e.getMessage());
		}
		//*/
		/*
		GradePrerequisites G = new GradePrerequisites("876727");
		Course C = Course.GetFromCode("CS130");
		//System.out.println(S.GetClassification());
		Student S = G.Student(G.MyBan);
		for(Course c: S.GetTakenCourses())
			System.out.println(c);
		System.out.println("\n"+C);
		System.out.println(G.run("CS130"));
		//*/
	}
	
	public boolean run(String crn)
	{
		//CourseData.printToStream(System.out,0,50);
		
		Student S = Student(MyBan);
		return S.CanTakeCourse(Course.GetFromCode(crn));
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
					String Code = StudentData.getDataPoint("Subject Code", i)+StudentData.getDataPoint("Course Number", i);
					Course C = Course.GetFromCode(Code, G.length()==0? null :(G.charAt(0)) );
					//if(Code.equals("CS130"))
						//System.out.println(C);
					S.TakeCourse(C);
					S.SetClassificationMax(StudentData.getDataPoint("Class Code", i));
				}
			}
			Students.put(banner, S);
			return S;
		}
	}
}
