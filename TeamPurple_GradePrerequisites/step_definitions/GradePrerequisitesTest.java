package step_definitions;

import implementation.*;

import cucumber.api.java.en.*;
import cucumber.api.java.Before;

import static org.junit.Assert.*;
/**
 * @author Sebastian Snyder
 */
public class GradePrerequisitesTest
{
	@Before
	public void Before()
	{
		GradePrerequisites G = new GradePrerequisites();
		G.equals(G);
	}
	
    @Given("^\"(.*?)\" and \"(.*?)\"$")
    public void and(String banner, String crn) throws Throwable {
        
        
    }
    
    @Then("^the student is \"(.*?)\" to be enrolled in the course$")
    public void theStudentIsToBeEnrolledInTheCourse(String enrollment) throws Throwable {
        
        
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
	private String Message;
	
	public TestFailedException(String M)
	{
		Message = M;
	}
	
	@Override
	public String getMessage()
	{
		return Message;
	}
}
