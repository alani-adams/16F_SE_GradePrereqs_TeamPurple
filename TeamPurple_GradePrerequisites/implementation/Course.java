package implementation;

/*
 * A class designed to store a course and its prerequisites
 */
public class Course
{
	private String CRN;
	private char Grade;
	public Prerequisite[] Prereqs;
	
	public Course(String CRN, char Grade)
	{
		this.CRN = CRN;
		this.Grade = Grade;
	}
	public String GetCRN()
	{
		return CRN;
	}
	public char GetGrade()
	{
		return Grade;
	}
}
