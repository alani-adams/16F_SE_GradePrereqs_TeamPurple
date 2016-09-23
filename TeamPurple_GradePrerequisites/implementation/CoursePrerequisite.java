package implementation;

public class CoursePrerequisite{
    private String courseCode;
    private char minGrade;
  
    public CoursePrerequisite( String code, char grade)
    {
    courseCode = code;
    minGrade = grade;
    }
  
    public String GetCourseCode()
    {
        return courseCode;
    }
  
    public char GetMinGrade(){
        return minGrade;
    }
  
    @Override
    public boolean IsMetBy(Student stu){
        ArrayList<Course> cour = stu.GetTakenCourses();
        for(Course c : cour)
        {
            if(courseCode.equals(c.GetCourseCode))
                if((char)(c.GetGrade()) >= GetMinGrade())
                    return true;
        }
      
    return false;
  }
}
