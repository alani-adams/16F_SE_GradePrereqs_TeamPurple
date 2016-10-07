package implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that represents a CSV file
 * @author Sebastian Snyder
 */
public class CSV
{
	private final HashMap<String,ArrayList<String>> Data;
	private ArrayList<String> ColumnNames;
	private static HashMap<String,String> StringHolder = new HashMap<String,String>();
	
	private CSV(String filepath,int NumRows,String[] ColNames) throws FileNotFoundException
	{
		Data = new HashMap<String,ArrayList<String>>();
		parse(new File(filepath),NumRows,ColNames);
	}
	
	private void parse(File f,int NumRows, String[] PassedNames) throws FileNotFoundException
	{
		//System.out.println(f.getAbsolutePath());
		Scanner Scan = new Scanner(f);
		ColumnNames = parseRow(Scan.nextLine(),false,null);
		ArrayList<Boolean> Consume = new ArrayList<Boolean>();
		if(PassedNames == null)
		{
			for(int i = 0; i < ColumnNames.size();i++)
				Consume.add(Boolean.TRUE);
		}
		else
		{	
			for(int i = 0; i < ColumnNames.size();i++)
				Consume.add(Boolean.FALSE);
			
			Comparator<String> c = new Comparator<String>() {
				@Override
				public int compare(String a, String b)
				{
					return a.compareTo(b);
				}
			};
			
OuterLoop:	for(int i = 0;i < PassedNames.length;i++)
			{
				for(int j = 0;j < ColumnNames.size();j++)
				{
					if(PassedNames[i].equals(ColumnNames.get(j)))
					{
						Consume.set(j, Boolean.TRUE);
						continue OuterLoop;
					}
				}
				Scan.close();
				throw new IllegalStateException("Column name not found: "+PassedNames[i]);
			}
			ArrayList<String> NewCnames = new ArrayList<String>();
			for(int i = 0;i < ColumnNames.size();i++)
			{
				if(Consume.get(i))
					NewCnames.add(ColumnNames.get(i));
			}
			ColumnNames = NewCnames;
		}

		for(String s: ColumnNames)
		{
			Data.put(s, new ArrayList<String>());
		}
		
		while(Scan.hasNextLine() && rowCount() != NumRows)
		{
			ArrayList<String> RowData = parseRow(Scan.nextLine(),Consume);
			for(int i = 0;i < ColumnNames.size();i++)
			{
				try
				{
					Data.get(ColumnNames.get(i)).add(RowData.get(i));
					//Thread.sleep(1000);
				}
				catch (Exception e)
				{
					System.out.println(RowData.get(i));
					System.exit(-1);
					//Scan.close();
					//throw e;
				}
				//System.out.println(Data.get(ColumnNames.get(i)));
				//
			}
			///*
			if((rowCount() % 25000) == 0)
				System.out.println(String.format("%8s",rowCount()) +" rows parsed of CSV["+f.getName()+"].");
			//*/
		}
		Scan.close();
	}
	
	private ArrayList<String> parseRow(String Row,ArrayList<Boolean> Consume)
	{
		return parseRow(Row,true,Consume);
	}
	
	private ArrayList<String> parseRow(String Row,boolean allowDupes,ArrayList<Boolean> Consume)
	{
		
		String StartRow = Row;
		boolean SkipFirstEmpty = Row.charAt(0) == '\"';
		Pattern P = Pattern.compile("(^|,)\"[^\"]*\"(?=(,|$))");
		Matcher M = P.matcher(Row);
		ArrayList<String> Predata = new ArrayList<String>();
		while(M.find())
		{
			String S = M.group().replaceAll("\"\"", "\"");
			Row = Row.replaceFirst(Pattern.quote(S), ",%"+Predata.size()+"%");
			Predata.add(S);
		}
		//System.out.println(Row);
		String[] BaseList = Row.split(",");
		ArrayList<String> NewList = new ArrayList<String>();
		//*
		for(int i = (SkipFirstEmpty && BaseList[0].equals(""))?1:0 ;i < BaseList.length;i++)
		{
			String toAdd = BaseList[i];
			if(toAdd.matches("%\\d+%"))
			{
				int index = Integer.parseInt(toAdd.substring(1,toAdd.length()-1));
				String s = Predata.get(index);
				NewList.add(s.substring(2-(s.charAt(0)=='\"'?1:0),s.length()-1));
			}
			else
				NewList.add(BaseList[i]);
		}
		for(int i = StartRow.length()-1;i >= 0;i--)
			if(StartRow.charAt(i) == ',')
				NewList.add("");
			else
				break;
		//*/
		if(!allowDupes)
		{
			ArrayList<String> A = new ArrayList<String>(NewList);
			A.sort(new Comparator<String>() {
				@Override
				public int compare(String A, String B)
				{
					return A.compareTo(B);
				}
			});
			/*
			System.out.println(A.size());
			for(int i = 0;i < NewList.size();i++)
				System.out.println(NewList.get(i));
				*/
			for(int i = 0;i < A.size()-1;i++)
			{
				if(A.get(i).equals(A.get(i+1)))
					throw new IllegalStateException("Duplicate entry \""+A.get(i)+"\" found.");
			}
		}
		if(Consume != null)
		{
			ArrayList<String> ActualNewList = new ArrayList<String>();
			for(int i = 0;i < Consume.size();i++)
			{
				if(Consume.get(i))
					ActualNewList.add(NewList.get(i));
			}
			NewList = ActualNewList;
		}

		if(ColumnNames != null && ColumnNames.size() != NewList.size())
			throw new IllegalStateException("CSV row "+(rowCount()+1)+" is of length "+NewList.size()+" (!= "+ColumnNames.size()+") :\n"+StartRow);
		
		//To conserve memory, Identical strings' references will be reused
		for(int i = 0;i < NewList.size();i++)
		{
			String test = NewList.get(i);
			if(StringHolder.containsKey(test))
				NewList.set(i, StringHolder.get(test));
			else
				StringHolder.put(test, test);
		}
		//System.out.println(NewList);
		return NewList;
	}
	
	/**
	 * Creates a CSV object that contains all of the rows in the CSV.
	 * @param filepath The path of the CSV to open
	 * @return A CSV object with the parsed data
	 * @throws FileNotFoundException when there is no file at the specified path
	 */
	public static CSV open(String filepath) throws FileNotFoundException
	{
		return new CSV(filepath,-1,null);
	}
	
	/**
	 * Creates a CSV object that contains the number of specified rows in the CSV.
	 * @param n The number of rows to open. If n = -1, all rows will be parsed.
	 * @param filepath The path of the CSV to open
	 * @return A CSV object with the parsed data
	 * @throws FileNotFoundException when there is no file at the specified path
	 */
	public static CSV openRows(int n, String filepath) throws FileNotFoundException
	{
		return new CSV(filepath,n,null);
	}
	
	/**
	 * Creates a CSV object that contains only the specified columns in the CSV.
	 * @param ColNames An array of Column Names
	 * @param filepath The path of the CSV to open. All rows will be opened if null is passed.
	 * @return A CSV object with the parsed data
	 * @throws FileNotFoundException when there is no file at the specified path
	 * @throws IllegalStateException if a column name specified is not found
	 */
	public static CSV openColumns(String filepath, String[] ColNames) throws FileNotFoundException {
		return new CSV(filepath,-1,ColNames);
	}
	
	/**
	 * Gets the String at the specified row number and column header.
	 * @param colName The column name of the desired data
	 * @param index	The row number of the data
	 * @return The string at the specified location
	 */
	public String getDataPoint(String colName,int index)
	{
		return Data.get(colName).get(index);
	}

	/**
	 * Gets the String at the specified row number and column number.
	 * @param colName The column number of the desired data
	 * @param index	The row number of the desired data
	 * @return The string at the specified location
	 */
	public String getDataPoint(int i, int j)
	{
		return getDataPoint(ColumnNames.get(i), j);
	}
	
	
	/**
	 * Gets the column name of the specified column number
	 * @param index The column number of the column whose name is desired
	 * @return The column's name
	 */
	public String getColumnName(int index)
	{
		return ColumnNames.get(index);
	}
	
	/**
	 * Gets the number of rows in this CSV
	 * @return the number of rows in this CSV
	 */
	public int rowCount()
	{
		return Data.entrySet().iterator().next().getValue().size();
	}
	
	/**
	 * Gets the number of columns in this CSV
	 * @return the number of columns in this CSV
	 */
	public int columnCount()
	{
		return ColumnNames.size();
	}
	
	/**
	 * Prints this CSV in a tabular format through the desired PrintStream
	 * @param out The PrintStream to print to
	 */
	public void printToStream(PrintStream out)
	{
		printToStream(out,12);
	}

	/**
	 * Prints this CSV in a tabular format through the desired PrintStream
	 * @param out The PrintStream to print to
	 * @param ColSize the desired column size
	 */
	public void printToStream(PrintStream out,int ColSize)
	{
		int[] toPass = new int[columnCount()];
		Arrays.fill(toPass, ColSize);
		printToStream(out,toPass);
	}

	/**
	 * Prints this CSV in a tabular format through the desired PrintStream
	 * @param out The PrintStream to print to
	 * @param ColSizes An array containing the desired size of each column
	 */
	public void printToStream(PrintStream out,int[] ColSizes)
	{
		printToStream(out,ColSizes,0,-1);
	}
	

	/**
	 * Prints this CSV in a tabular format through the desired PrintStream
	 * @param out The PrintStream to print to
	 * @param ColSizes An array containing the desired size of each column
	 */
	public void printToStream(PrintStream out,int StartIndex,int EndIndex)
	{
		printToStream(out,12,StartIndex,EndIndex);
	}

	/**
	 * Prints this CSV in a tabular format through the desired PrintStream
	 * @param out The PrintStream to print to
	 * @param ColSize the desired column size
	 * @param StartIndex the row number to begin printing at
	 * @param EndIndex the row number to stop printing at; all rows will be printed if -1 is passed
	 */
	public void printToStream(PrintStream out,int ColSize,int StartIndex,int EndIndex)
	{
		int[] toPass = new int[columnCount()];
		Arrays.fill(toPass, ColSize);
		printToStream(out,toPass,StartIndex,EndIndex);
	}

	/**
	 * Prints this CSV in a tabular format through the desired PrintStream
	 * @param out The PrintStream to print to
	 * @param ColSizes An array containing the desired size of each column
	 * @param StartIndex the row number to begin printing at
	 * @param EndIndex the row number to stop printing at; all rows will be printed if -1 is passed
	 */
	public void printToStream(PrintStream out,int[] ColSizes,int StartIndex,int EndIndex)
	{
		
		//if(1 == 1)
		//	throw new IllegalArgumentException();
		if(EndIndex != -1 && StartIndex > EndIndex)
			throw new IllegalArgumentException("End index "+EndIndex+" < "+StartIndex);
		
		if(ColSizes.length != columnCount())
			throw new IllegalArgumentException("Supplied int[] is of size "+ColSizes.length+", but there are "+columnCount()+" columns.");
		int NumDashes = ColSizes.length-1;
		for(int i = 0;i< columnCount();i++)
		{
			String s = getColumnName(i);
			out.printf("|%-"+ColSizes[i]+"s",s.substring(0, Math.min(s.length(), ColSizes[i])));
			NumDashes += ColSizes[i];
		}
		out.println("|\n|"+new String(new char[NumDashes]).replaceAll("\0", "-")+"|");
		for(int j = StartIndex;j< rowCount() && j != EndIndex;j++)
		{
			for(int i = 0;i< columnCount();i++)
			{
				String s = getDataPoint(i,j);
				out.printf("|%-"+ColSizes[i]+"s",s.substring(0, Math.min(s.length(), ColSizes[i])));
			}
			out.println("|");
		}
	}
}