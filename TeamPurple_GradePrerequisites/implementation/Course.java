package implementation;

public class Course
{
	private String CRN;
	private char Grade;
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
