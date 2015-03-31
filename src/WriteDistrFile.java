import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class WriteDistrFile {
	public String splitter=",";
	FileWriter writer;
	
	public WriteDistrFile(String commentatfirstline,String writefile) throws IOException{
		writer=new FileWriter(writefile);
		writer.write(commentatfirstline+"\n");
	}
	public void Write(HashMap map) throws IOException{
		Iterator iterator=map.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry entry=(Entry) iterator.next();
			writer.write(entry.getKey()+splitter+entry.getValue()+"\n");
		}
		writer.close();
	}
}
