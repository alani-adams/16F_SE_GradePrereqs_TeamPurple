package implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * A class that represents a CSV file
 * @author Sebastian Snyder
 */
public class CSV
{
	private final HashMap<String,ArrayList<String>> Data;
	private ArrayList<String> ColumnNames;
	
	private static final boolean CONSERVE_MEMORY_MODE = false;
	private static HashMap<String,String> StringHolder = CONSERVE_MEMORY_MODE?new HashMap<String,String>():null;
	public static boolean DEBUG_MODE = false;
	
	private CSV(String filepath,int NumRows,String[] ColNames) throws IOException
	{
		Data = new HashMap<String,ArrayList<String>>();
		parse(new File(filepath),NumRows,ColNames);
	}
	
	private void parse(File f,int NumRows, String[] PassedNames) throws IOException
	{
		try(BufferedReader Fin = new BufferedReader(new FileReader(f)))
		{
			//
			//	Parse the column names
			//
			ColumnNames = parseRow(Fin.readLine(),false,null);
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
			
			//
			//	Parse the rest of the file
			//
			String line = null;
			
			while((line = Fin.readLine()) != null && rowCount() != NumRows)
			{
				ArrayList<String> RowData = parseRow(line,Consume);
				//*/
				for(int i = 0;i < ColumnNames.size();i++)
				{
					Data.get(ColumnNames.get(i)).add(RowData.get(i));
				}
				///*
				if(DEBUG_MODE && (rowCount() % 25000) == 0)
					System.out.println(String.format("%8s",rowCount()) +" rows parsed of CSV["+f.getName()+"].");
				//*/
			}
		}
	}
	
	private ArrayList<String> parseRow(String Row,ArrayList<Boolean> Consume)
	{
		return parseRow(Row,true,Consume);
	}
	
	private ArrayList<String> parseRow(String Row,boolean allowDupes,ArrayList<Boolean> Consume)
	{
		ArrayList<String> ToRet = new ArrayList<String>();
		int ColIndex = 0;
		int StartIndex = 0;
		for(int i = 0 ;i < Row.length();i++)
		{
			if(StartIndex == i && Row.charAt(i) == '"')
			{
				for(i++;i <= Row.length();i++)
				{
					if(Row.charAt(i) == '"' && (i+1 == Row.length() || Row.charAt(i+1) == ','))
					{
						if(Consume == null || Consume.get(ColIndex++))
							ToRet.add(Row.substring(StartIndex+1, i));
						StartIndex = ++i + 1;
						
						break;
					}
				}
				continue;
			}
			if(Row.charAt(i) == ',')
			{
				if(Consume == null || Consume.get(ColIndex++))
					ToRet.add(Row.substring(StartIndex, i));
				StartIndex = i+1;
			}
			if(i+1 == Row.length())
			{
				if(Consume == null || Consume.get(ColIndex++))
					ToRet.add(Row.substring(StartIndex, i+1));
			}
		}
		
		
		
		//To conserve memory, Identical strings' references may be reused
		//*
		if(CONSERVE_MEMORY_MODE)
			for(int i = 0;i < ToRet.size();i++)
			{
				String test = ToRet.get(i);
				if(StringHolder.containsKey(test))
					ToRet.set(i, StringHolder.get(test));
				else
					StringHolder.put(test, test);
			}
		return ToRet;
	}
	
	/**
	 * Creates a CSV object that contains all of the rows in the CSV.
	 * @param filepath The path of the CSV to open
	 * @return A CSV object with the parsed data
	 * @throws IOException 
	 */
	public static CSV open(String filepath) throws IOException
	{
		return new CSV(filepath,-1,null);
	}
	
	/**
	 * Creates a CSV object that contains the number of specified rows in the CSV.
	 * @param n The number of rows to open. If n = -1, all rows will be parsed.
	 * @param filepath The path of the CSV to open
	 * @return A CSV object with the parsed data
	 * @throws IOException 
	 */
	public static CSV openRows(int n, String filepath) throws IOException
	{
		return new CSV(filepath,n,null);
	}
	
	/**
	 * Creates a CSV object that contains only the specified columns in the CSV.
	 * @param ColNames An array of Column Names
	 * @param filepath The path of the CSV to open. All rows will be opened if null is passed.
	 * @return A CSV object with the parsed data
	 * @throws IOException 
	 * @throws IllegalStateException if a column name specified is not found
	 */
	public static CSV openColumns(String filepath, String[] ColNames) throws IOException {
		return new CSV(filepath,-1,ColNames);
	}
	
	/**
	 * Creates a CSV object that contains only the specified columns in the CSV.
	 * @param ColNames An array of Column Names
	 * @param filepath The path of the CSV to open. All rows will be opened if null is passed.
	 * @return A CSV object with the parsed data
	 * @throws IOException 
	 * @throws IllegalStateException if a column name specified is not found
	 */
	public static CSV openColumnsWithConditions(String filepath, String[] ColNames) throws IOException {
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