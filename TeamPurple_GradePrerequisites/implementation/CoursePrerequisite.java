package implementation;

public class CoursePrerequisite{
  private String courseCode;
  private char minGrade;
  
  public CoursePrerequisite( String code, char grade){
    courseCode = code;
    minGrade = grade;
  }
  
  public String getCourseCode(){
    return courseCode;
  }
  
  public char getMinGrade(){
    return minGrade;
  }
}
