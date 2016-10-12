package step_definitions;

import implementation.*;

import cucumber.api.java.en.*;
import cucumber.api.PendingException;
import cucumber.api.java.Before;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Assert;
/**
 * @author Sebastian Snyder
 */
public class GradePrerequisitesTest
{
	static GradePrerequisites G;
	Student TestStudent;
	
	@Before
	public void Before()
	{
		if(G == null)
		{
			G = new GradePrerequisites();
		}
	}
	/*
	@Given("^\"(.*?)\" has no prerequisite$")
	public void has_no_prerequisite(String arg1) throws Throwable {
	    //Course.RegisterCourse(arg1, null);
	}

	@Given("^\"(.*?)\" has a prerequisite of \"(.*?)\"$")
	public void has_a_prerequisite_of(String arg1, String arg2) throws Throwable {
	    //Course.RegisterCourse(arg1, arg2);
	}

	@Given("^\"(.*?)\" with a CRN of \"(.*?)\"$")
	public void with_a_CRN_of(String arg1, String arg2) throws Throwable {
	    //Course.RegisterCRN(arg2, arg1);
	}
	@Given("^\"(.*?)\" has taken course \"(.*?)\" and received a \"(.*?)\"$")
	public void has_taken_course_and_recieved_a(String arg1, String arg2, String arg3) throws Throwable {
		Student S;
		if(!Students.containsKey(arg1))
		{
			S = new Student(arg1);
			Students.put(arg1, S);
		}
		else
		{
			S = Students.get(arg1);
		}
	    S.TakeCourse(Course.GetFromCode(arg2,arg3.charAt(0)));
	}

	@Given("^\"(.*?)\" has a \"(.*?)\" score of \"(.*?)\"$")
	public void has_a_score_of(String arg1, String arg2, String arg3) throws Throwable {
		Student S;
		if(!Students.containsKey(arg1))
		{
			S = new Student(arg1);
			Students.put(arg1, S);
		}
		else
		{
			S = Students.get(arg1);
		}
	    S.SetTestScore(arg2, Integer.parseInt(arg3));
	}

	@Given("^\"(.*?)\" has taken \"(.*?)\" and received a \"(.*?)\"$")
	public void has_taken_and_received_a(String arg1, String arg2, String arg3) throws Throwable {
		Student S;
		if(!Students.containsKey(arg1))
		{
			S = new Student(arg1);
			Students.put(arg1, S);
		}
		else
		{
			S = Students.get(arg1);
		}
	    S.TakeCourse(Course.GetFromCRN(arg2,arg3.charAt(0)));
	}

*/
	
	@Given("^A student with BannerID \"(.*?)\"$")
	public void banner_and_CRN(String arg1) throws Throwable {
	    TestStudent = G.Student(arg1);
	}
	@Then("^the student \"(.*?)\" allowed to be enrolled in \"(.*?)\"$")
	public void the_student_allowed_to_be_enrolled_in_the_course(String arg1, String arg2) throws Throwable {
		Course TestCourse = Course.GetFromCode(arg2);
	    boolean disallowed = arg1.equals("is not");
	    boolean testallowed = TestStudent.CanTakeCourse(TestCourse);
	    Assert.assertTrue(disallowed ^ testallowed);
	}

    
}

/**
 * An Exception that exists purely for my convenience. I'll throw it if and when I need
 * to fail a test, but assertions aren't sufficiently elegant or logical for my purposes.
 * 
 * @author Sebastian Snyder
 */
class TestFailedException extends Exception
{
	//So that the compiler stops yelling at me
	private static final long serialVersionUID = 1L;
	
	public TestFailedException(String M)
	{
		super(M);
	}
}
