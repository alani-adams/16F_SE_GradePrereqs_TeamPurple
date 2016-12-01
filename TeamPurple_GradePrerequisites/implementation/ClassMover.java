package implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @author Sebastian Snyder
 */
public class ClassMover
{
	private final HashMap<String,Student> Students;
	private final HashMap<String,HashMap<String,Course>> Courses;
	private final HashMap<String,Course> CoursesByCRN;
	private final HashMap<String,Course> CoursesByCode;
	
    private final HashMap<String,Professor> Professors;
	private static CSV data = null;
	private static HashMap<String,Room> Rooms = null;
	
	public static HashMap<String,Room> getRoomsMap()
	{
		return Rooms;
	}
	
	public HashMap<String,Course> getCoursesMap(String termcode)
	{
		//Set<String> KeySet = Courses.keySet();
		return Courses.get(termcode);
	}
	public HashMap<String, Student> getStudentsMap()
	{
		return Students;
	}
	public HashMap<String, Professor> getProfessorsMap()
	{
		return Professors;
	}
	
	private static void initRooms() throws IOException
	{
		String[] columnNames = new String[] {"Room Code","Max Seats"};
	
		CSV RoomData = CSV.openColumns("roomData.csv",columnNames);
	
		for(int i = 0;i < RoomData.rowCount();i++)
		{
			String RC = RoomData.getDataPoint("Room Code", i);
			int MaxSeats = Integer.parseInt(RoomData.getDataPoint("Max Seats", i));
			
			Rooms.put(RC, new Room(RC, MaxSeats));
		}
	}
	
	public ClassMover() throws IOException
	{
		Rooms = new HashMap<>();
		initRooms();
		
		Students = new HashMap<>();
		Courses = new HashMap<>();
		Professors = new HashMap<>();
		
		CoursesByCRN = new HashMap<>();
		CoursesByCode = new HashMap<>();
		
		
		String[] columnNames = new String[] {"Term Code", "Class Code", "Subject Code", "Course Number", "Section Number",
											"Instructor Name", "Banner ID", "Begin Time 1", "End Time1", "Bldg Code1", "Room Code1",
											"Monday Ind1", "Tuesday Ind1", "Wednesday Ind1", "Thursday Ind1", "Friday Ind1", "Saturday Ind1",
											"Sunday Ind1", "Ovrall Cumm GPA  Hours Earned", "Section Max Enrollment","CRN"};
		if(data == null)
		{
			data = CSV.openColumns("cs374_f16_anon.csv",columnNames);
			//data.printToStream(System.out, 10, 1, 50);
		}
		
		for(int i = 0;i < data.rowCount();i++)
		{

            String TimeStart = data.getDataPoint("Begin Time 1",i);
            String TimeEnd = data.getDataPoint("End Time1", i);
            
            if(TimeStart.equals("") || TimeEnd.equals(""))
            	continue;
            
            String TermCode = data.getDataPoint("Term Code",i);
            String Course = data.getDataPoint("Subject Code", i) + data.getDataPoint("Course Number", i)
            			+ "." + String.format("%2s", data.getDataPoint("Section Number", i)).replaceFirst(" ", "0");
            String bannerID = data.getDataPoint("Banner ID", i);
            String profName = data.getDataPoint("Instructor Name", i);
            String roomNum = data.getDataPoint("Bldg Code1", i) + data.getDataPoint("Room Code1", i);
            Student S;		Professor P;		Course C;
            HashMap<String,Course> Cmap;
            
            Room R = Rooms.get(roomNum);
            
            
			if(!Students.containsKey(bannerID))
			{
				//System.out.println("ADDED "+bannerID);
                S = new Student(bannerID,data.getDataPoint("Class Code", i));
                Students.put(bannerID, S);
			}
            else S = Students.get(bannerID);
			
            if(!Professors.containsKey(profName))
            {
                P = new Professor(profName);
                Professors.put(profName, P);
            }
            else P = Professors.get(profName);
            
            if(!Courses.containsKey(TermCode))
            {
            	Cmap = new HashMap<String,Course>();
            	Courses.put(TermCode, Cmap);
            }
            else Cmap = Courses.get(TermCode);
            	
            if(!Cmap.containsKey(Course))
            {
                C = new Course(TermCode,Course,R,P);
                
                boolean[] HasDay = new boolean[Day.values().length];
                HasDay[0] = !data.getDataPoint("Sunday Ind1", i).equals("");
                HasDay[1] = !data.getDataPoint("Monday Ind1", i).equals("");
                HasDay[2] = !data.getDataPoint("Tuesday Ind1", i).equals("");
                HasDay[3] = !data.getDataPoint("Wednesday Ind1", i).equals("");
                HasDay[4] = !data.getDataPoint("Thursday Ind1", i).equals("");
                HasDay[5] = !data.getDataPoint("Friday Ind1", i).equals("");
                HasDay[6] = !data.getDataPoint("Saturday Ind1", i).equals("");
	            
                for(int d = 0;d < HasDay.length;d++)
                {
                	if(HasDay[d])
                		C.setClassPeriod(Day.values()[d],Integer.parseInt(TimeStart),Integer.parseInt(TimeEnd));
                }
                
                Cmap.put(Course,C);
                CoursesByCode.put(Course, C);
                CoursesByCRN.put(data.getDataPoint("CRN", i), C);
            }
            else C = Cmap.get(Course);
               
            C.addStudent(S);
            S.addCourse(C);
		}
        
	}

	public boolean CanMoveProfessor(Course C,Day D,short StartTime,short EndTime)
	{
		Professor P = C.getInstructor();
		P.getCourses(C.getTermCode()).remove(C);
		try
		{
			return P.SlotFree(C.getTermCode(), D, StartTime, EndTime);
		}
		finally
		{
			P.addCourse(C);
		}
	}
	
	public HashSet<Student> GetStudentConflicts(Course C,Day D,short StartTime,short EndTime)
	{
		HashSet<Student> Conflicted = new HashSet<>();
		for(Student S:C.getStudents())
		{
			S.getCourses(C.getTermCode()).remove(C);
			try
			{
				if(! S.SlotFree(C.getTermCode(), D, StartTime, EndTime))
				{
					Conflicted.add(S);
					continue;
				}
			}
			finally
			{
				S.addCourse(C);
			}
		}
		return Conflicted;
	}
	
	public static void main(String... args) throws Exception
	{
		if(args.length == 0 || args[0].equals("help"))
		{
			System.out.println("Run the program with:");
			System.out.println("  CRN [NumberToShow]");
			System.out.println(" or ");
			System.out.println("  Section [NumberToShow]");
			System.out.println();
			System.out.println("For example:");
			System.out.println("  10844");
			System.out.println(" or ");
			System.out.println("  IT220.01 20");
			return;
		}
		else if(args.length > 2)
		{
			System.out.println("Please pass only one CRN or one Course Section in the form similar to IT211.01");
			return;
		}
		ClassMover Mover = new ClassMover();
		int Printed = 0;
		
		Course C = Mover.getCourseFromCRN(args[0]);
		if(C == null)
			C = Mover.getCourseFromCode(args[0]);
		
		if(C == null)
		{
			System.out.println("Data for course "+args[0]+" not found");
			return;
		}
		
		ArrayList<ConflictData> Cset =  Mover.GetPotentialSlots(C);
		Cset.sort(new Comparator<ConflictData>() {
			@Override
			public int compare(ConflictData a, ConflictData b)
			{
				return a.Score - b.Score;
			}
		});
		//Cset.remove(0);
		
		int MaxIters = 5;
		
		if(args.length == 2)
			MaxIters = Integer.parseInt(args[1]);
		
		MaxIters = Math.min(MaxIters, Cset.size());
		
		System.out.print("Top "+MaxIters+" timeslots for course "+args[0]+". (currently ");
		
		for(int i = 0;i < 7;i++)
		{
			Day D = Day.values()[i];
			if(!C.SlotFree(D, (short)800, (short)1700))
			{
				char ToPrint = 'X';
				switch(D)
				{
					case SUNDAY:
						ToPrint = 'U'; break;
					case MONDAY:
						ToPrint = 'M'; break;
					case TUESDAY:
						ToPrint = 'T'; break;
					case WEDNESDAY:
						ToPrint = 'W'; break;
					case THURSDAY:
						ToPrint = 'R'; break;
					case FRIDAY:
						ToPrint = 'F'; break;
					case SATURDAY:
						ToPrint = 'S'; break;
				}
				System.out.print(ToPrint);
			}
		}

		System.out.printf(" %2d:%02d to ",C.getFirstStartTime()/100,C.getFirstStartTime()%100);
		System.out.printf("%2d:%02d  ",C.getFirstEndTime()/100,C.getFirstEndTime()%100);
		System.out.println("in "+C.getRoom().getRoomNumber()+")");
		for(ConflictData CD : Cset)
		{
			System.out.printf("%3d:  ",++Printed);
			if(CD.Days[0] == Day.MONDAY)
				System.out.print("MWF ");
			else
				System.out.print("TR  ");
			
			System.out.printf("%2d:%02d to ",CD.StartTime/100,CD.StartTime%100);
			System.out.printf("%2d:%02d  ",CD.EndTime/100,CD.EndTime%100);
			
			System.out.printf("in room %-8s(",CD.Room.getRoomNumber());
			
			if(CD.StudentConflicts == 0)
			{
				System.out.print("No conflicts");
			}
			else
			{
				System.out.printf("%3d total conflicts:",CD.StudentConflicts);
				if(CD.SeniorConflicts > 0)
					System.out.print(" "+CD.SeniorConflicts+" SR");
				if(CD.JuniorConflicts > 0)
					System.out.print(" "+CD.JuniorConflicts+" JR");
				if(CD.SophomoreConflicts > 0)
					System.out.print(" "+CD.SophomoreConflicts+" SO");
				if(CD.FreshmanConflicts > 0)
					System.out.print(" "+CD.FreshmanConflicts+" FR");
				if(CD.GraduateConflicts > 0)
					System.out.print(" "+CD.FreshmanConflicts+" GR");
				
			}
			if(CD.Room != null)
			{
				int Room = CD.Room.getMaxSize() + (-C.getStudents().size()) + CD.StudentConflicts;
				if(CD.SpaceExcess != 0)
				{
					System.out.print(", Students that can't fit: "+CD.SpaceExcess);
				}
				else if(Room < 0)
				{
					System.out.print(", Students that can't fit: "+ -Room);
				}
				else
				{
					if(CD.Room.getMaxSize() == C.getStudents().size() - CD.StudentConflicts)
						System.out.println(", Room exactly full");
					else
						System.out.print(", Seats remaining: "+Room);
				}
			}
			System.out.println(")");
			//
			if(Printed == MaxIters)
				break;
		}
	}
	
	private Course getCourseFromCode(String string)
	{
		//System.out.println(CoursesByCode.keySet().iterator().next());
		return CoursesByCode.get(string);
	}
	private Course getCourseFromCRN(String string)
	{
		//System.out.println(CoursesByCRN.keySet().iterator().next());
		return CoursesByCRN.get(string);
	}
	private ArrayList<ConflictData> GetPotentialSlots(Course TestCourse)
	{
		ArrayList<ConflictData> CD = new ArrayList<>();
		
		//MWF
		short StartTime = 800;
		short EndTime = 850;
		while(EndTime < 1700)
		{
			//System.out.println(StartTime+"->"+EndTime);
			StartTime = (short)((StartTime % 100 == 30)?StartTime+70:StartTime+30);
			EndTime = (short)((EndTime % 100 == 50)?EndTime+70:EndTime+30);
			
			Day[] Ds = new Day[] {Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY};
			ConflictData C = TestSlots(TestCourse,Ds,StartTime,EndTime);
			if(C != null && !(TestCourse.getFirstStartTime() == StartTime && TestCourse.getFirstEndTime() == EndTime))
				CD.add(C);
		}
		
		//TR
		StartTime = 800;
		EndTime = 920;
		while(EndTime < 1700)
		{
			//System.out.println(StartTime+"->"+EndTime);
			StartTime = (short)((StartTime % 100 == 30)?StartTime+70:StartTime+30);
			EndTime = (short)((EndTime % 100 == 50)?EndTime+70:EndTime+30);
			
			Day[] Ds = new Day[] {Day.TUESDAY, Day.THURSDAY};
			ConflictData C = TestSlots(TestCourse,Ds,StartTime,EndTime);

			if(C != null && !(TestCourse.getFirstStartTime() == StartTime && TestCourse.getFirstEndTime() == EndTime))
				CD.add(C);
		}
		return CD;
	}
	
	private ConflictData TestSlots(Course TestCourse, Day[] Ds, short StartTime, short EndTime)
	{
		for(Day D:Ds)
		{
			if(!CanMoveProfessor(TestCourse, D, StartTime, EndTime))
				return null;
		}
		
		ConflictData C = new ConflictData(StartTime,EndTime,0,Ds);
		
		HashSet<Student> ConStu = new HashSet<>();
		ArrayList<Student> AllStu = new ArrayList<Student>();
		
		for(Student S:TestCourse.getStudents())
		{
			if(S.getClassification() != null)
				AllStu.add(S);
		}
		
		for(Day D:Ds)
			for(Student S : GetStudentConflicts(TestCourse, D, StartTime, EndTime))
				ConStu.add(S);
			
		AllStu.sort(new Comparator<Student>(){
			@Override
			public int compare(Student a, Student b)
			{
				return a.getClassification().Weight - b.getClassification().Weight;
			}
		});
		
		int RoomSize = Integer.MAX_VALUE;
		if(TestCourse.getRoom() != null)
		{
			ArrayList<Room> TestRooms = new ArrayList<>();
			
			TestCourse.getRoom().removeCourse(TestCourse);
			for(Map.Entry<String,Room> R: Rooms.entrySet())
			{
				Room Rm = R.getValue();
				
				for(Day D: C.Days)
				{
					if(!Rm.SlotFree(D, C.StartTime, C.EndTime))
						continue;
				}
				TestRooms.add(Rm);
			
			}
			TestCourse.getRoom().addCourse(TestCourse);
			
			final Room OrigRoom = TestCourse.getRoom();
			TestRooms.sort(new Comparator<Room>(){
				@Override
				public int compare(Room a, Room b)
				{
					if(b == OrigRoom)
						return 1;
					else if(a == OrigRoom)
						return -1;
					else
						return b.getMaxSize() - a.getMaxSize();
				}
			});
			int i = 0;
			do
			{
				C.Room = TestRooms.get(i++);
				if(i == TestRooms.size() || TestRooms.get(i).getMaxSize() < AllStu.size()-ConStu.size())
					break;
			}
			while(C.Room != OrigRoom);
			RoomSize = C.Room.getMaxSize();
		}
		
		int i = 0;
		while(AllStu.size() - ConStu.size() - C.SpaceExcess > RoomSize)
		{
			Student CurStu = AllStu.get(i);
			if(ConStu.contains(CurStu))
			{
				i++; continue;
			}
			
			C.SpaceExcess++;
			C.Score += CurStu.getClassification().Weight;
			//
			i++;
		}
		
		for(Student S : ConStu)
		{
			Classification SC = S.getClassification();
			if(SC == null)
				C.Score += Classification.FRESHMAN.Weight;
			else
				C.Score += SC.Weight;
			
			C.StudentConflicts++;
			if(SC != null)
				switch(SC)
				{
					case FRESHMAN:
						C.FreshmanConflicts++;
						break;
					case SOPHOMORE:
						C.SophomoreConflicts++;
						break;
					case JUNIOR:
						C.JuniorConflicts++;
						break;
					case SENIOR:
						C.SeniorConflicts++;
						break;
					case GRADUATE:
						C.GraduateConflicts++;
						break;
				}
		}
		return C;
	}
}

class ConflictData
{
	public short StartTime;
	public short EndTime;
	public Room Room;
	
	public int StudentConflicts;
	public int FreshmanConflicts;
	public int SophomoreConflicts;
	public int JuniorConflicts;
	public int SeniorConflicts;
	public int GraduateConflicts;
	
	public int SpaceExcess;
	
	public Day[] Days;
	public int Score;
	
	public ConflictData(short ST,short ET, int Sc,Day... D)
	{
		StartTime = ST;
		EndTime = ET;
		Score = Sc;
		Days = D;
	}
}

