package step_definitions;

import implementation.*;

import cucumber.api.java.en.*;
import cucumber.api.java.Before;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Assert;
/**
 * @author Sebastian Snyder
 */
public class GradePrerequisitesTest
{
	HashMap<String,Student> Students;
	Course C;
	
	Student TestStudent;
	
	@Before
	public void Before()
	{
		Students = new HashMap<String,Student>();
	}
	
	@Given("^\"(.*?)\" with a CRN of \"(.*?)\" with a prereq of \"(.*?)\"$")
	public void with_a_CRN_of_with_a_prereq_of(String arg1, String arg2, String arg3) throws Throwable {
	    Course.RegisterCourse(arg1,arg2,arg3);
	}

	@Given("^\"(.*?)\" has taken \"(.*?)\" and recieved a \"(.*?)\"$")
	public void has_taken_and_recieved_a(String arg1, String arg2, String arg3) throws Throwable
	{
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

	@Given("^A student with BannerID \"(.*?)\"$")
	public void banner_and_CRN(String arg1) throws Throwable {
	    TestStudent = Students.get(arg1);
	}

	@Then("^the student \"(.*?)\" allowed to be enrolled in \"(.*?)\"$")
	public void the_student_allowed_to_be_enrolled_in_the_course(String arg1, String arg2) throws Throwable {
	    boolean disallowed = arg1.equals("is not");
	    Assert.assertTrue(disallowed ^ TestStudent.CanTakeCourse(Course.GetFromCRN(arg2)));
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
