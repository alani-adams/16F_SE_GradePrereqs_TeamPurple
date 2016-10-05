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
	
	private CSV(String filepath,int NumRows) throws FileNotFoundException
	{
		Data = new HashMap<String,ArrayList<String>>();
		parse(new File(filepath),NumRows);
	}
	
	private void parse(File f,int NumRows) throws FileNotFoundException
	{
		//System.out.println(f.getAbsolutePath());
		Scanner Scan = new Scanner(f);
		ColumnNames = parseRow(Scan.nextLine());
		for(String s: ColumnNames)
		{
			//System.out.println(s);
			Data.put(s, new ArrayList<String>());
		}
		while(Scan.hasNextLine() && rowCount() != NumRows)
		{
			ArrayList<String> RowData = parseRow(Scan.nextLine());
			for(int i = 0;i < ColumnNames.size();i++)
			{
				try
				{
					Data.get(ColumnNames.get(i)).add(RowData.get(i));
				}
				catch (Exception e)
				{
					System.out.println(RowData.get(i));
					Scan.close();
					throw e;
				}
			}
		}
		Scan.close();
	}
	
	private ArrayList<String> parseRow(String Row)
	{
		String errRow = Row;
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
		//*/
		if(ColumnNames != null && ColumnNames.size() != NewList.size())
			throw new IllegalStateException("CSV row "+(rowCount()+1)+" is of length "+NewList.size()+" (!= "+ColumnNames.size()+") :\n"+errRow);
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
		return new CSV(filepath,-1);
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
		return new CSV(filepath,n);
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