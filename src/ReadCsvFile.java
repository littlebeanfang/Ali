import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ReadCsvFile {
	public String splitter=",";
	public BufferedReader reader;
	public int columnnum=0;
	public int linecount=0;
	public ReadCsvFile(String csvfile) throws IOException{
		reader=new BufferedReader(new FileReader(csvfile));
		//skip first line
		String line=reader.readLine();
		columnnum=line.split(splitter).length;
	}
	public String[] GetNextTokens() throws IOException{
		String line=reader.readLine();
		if(line!=null){
			String[] columns=line.split(splitter);
			if(columns.length==columnnum){
				linecount++;
				return columns;
			}else{
				System.out.println("data column error: line "+linecount);
			}
		}
		return null;
	}
}
