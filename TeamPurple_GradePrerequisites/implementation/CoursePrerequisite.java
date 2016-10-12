package implementation;

import java.util.ArrayList;

/**
 * 
 * @author Alani Peters, Sebastian Snyder
 *
 */
public class CoursePrerequisite extends Prerequisite{
    private String courseCode;
    private char minGrade;
  
    /**
     * 
     * @param code
     * @param grade
     */
    public CoursePrerequisite(String code, char grade)
    {
	    courseCode = code;
	    minGrade = grade;
    }
  
    @Override
    public boolean IsMetBy(Student stu){
    	
        ArrayList<Course> cour = stu.GetTakenCourses();
        for(Course c : cour)
        {
            if(courseCode.equals(c.GetCourseCode()))
            {
            	Character G = c.GetGrade();
                if(G != null && (char)(G) <= minGrade)
                {
                    return true;
                }
            }
        }
      
    return false;
  }

    
    @Override
    public String toString()
    {
    	return courseCode+"("+minGrade+")";
    }
}
