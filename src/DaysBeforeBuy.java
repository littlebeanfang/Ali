import java.io.IOException;
import java.util.HashMap;
/**
 * count the days from first action to finally buy
 * @author Bean
 *
 */
public class DaysBeforeBuy {
	//label format: <uid_pid>
	public HashMap<String, Integer> label_dayindex=new HashMap<String,Integer>();
	public HashMap<String, Integer> label_actioncount=new HashMap<String,Integer>();
	public HashMap<Integer, Integer> buybeforedays_count=new HashMap<Integer,Integer>();
	public HashMap<String, Integer> label_buytimes=new HashMap<String,Integer>();
	public HashMap<Integer, Integer> actioncount_count=new HashMap<Integer,Integer>();
	public int notcountainbfbuycount=0;
	public void DistriProcee(String[] files) throws IOException{
		//!offer the data by date order
		
		for(int fileindex=0; fileindex<files.length; fileindex++){
			System.out.println("Process file:"+files[fileindex]+",map1size:"+label_dayindex.size()
					+",map2size:"+buybeforedays_count.size()+",map3size:"+label_buytimes.size());
			
			ReadCsvFile reader=new ReadCsvFile(files[fileindex]);
			String columns[];
			while((columns=reader.GetNextTokens())!=null){
				//System.out.println(columns[0]+","+columns[1]+","+columns[2]);
				String label;
				label=columns[0]+"_"+columns[1];
				if(columns[2].equals("4")){
					//buy
					//System.out.println("buy:"+label.toString());
					if(label_dayindex.containsKey(label.toString())){
						//former action detected
						//System.out.println("buy1");
						int firstactionday=label_dayindex.get(label.toString());
						int days=fileindex-firstactionday;
						if(buybeforedays_count.containsKey(days)){
							buybeforedays_count.put(days, buybeforedays_count.get(days)+1);
						}else{
							buybeforedays_count.put(days, 1);
						}
						if(label_buytimes.containsKey(label.toString())){
							label_buytimes.put(label.toString(), label_buytimes.get(label.toString())+1);
						}else{
							label_buytimes.put(label.toString(), 1);
						}
						label_dayindex.remove(label.toString());
						
						//actioncount process
						int actioncount=label_actioncount.get(label);
						if(actioncount_count.containsKey(actioncount)){
							actioncount_count.put(actioncount, actioncount_count.get(actioncount)+1);
						}else{
							actioncount_count.put(actioncount, 1);
						}
						label_actioncount.remove(label);
					}else{
						notcountainbfbuycount++;
						System.out.println("not contain before buy:"+label);
					}
				}else{
					//other action, store record
					if(!label_dayindex.containsKey(label)){
						//System.out.println("add:"+label);
						label_dayindex.put(label.toString(), fileindex);
					}
					if(label_actioncount.containsKey(label)){
						label_actioncount.put(label, label_actioncount.get(label)+1);
					}else{
						label_actioncount.put(label, 1);
					}
					
					if(label_buytimes.containsKey(label)){
						label_buytimes.put(label, label_buytimes.get(label)+1);
					}else{
						label_buytimes.put(label, 1);
					}
				}
			}
			//System.out.println("Process file:"+files[fileindex]+",map1size:"+label_dayindex.size()
			//		+",map2size:"+buybeforedays_count.size()+",map3size:"+label_buytimes.size());
		}
		
	}
	public void PrintDistribution(String buybeforedays, String buytimes, String actionbfbuycount) throws IOException{
		WriteDistrFile writer=new WriteDistrFile("day# before buy distribution. <day#><count>", buybeforedays);
		writer.Write(buybeforedays_count);
		WriteDistrFile writer2=new WriteDistrFile("buytime# of uid_pid pair. <uid_pid><buy#>", buytimes);
		writer2.Write(label_buytimes);
		WriteDistrFile writer3=new WriteDistrFile("action#  before buy distribution. <action#><count>", actionbfbuycount);
		writer3.Write(actioncount_count);
		System.out.println("Not contain before buy COUNT:"+notcountainbfbuycount);
	}
	public static void main(String args[]) throws IOException{
		DaysBeforeBuy test=new DaysBeforeBuy();
		
		String files[]={
				"train_user_2014-11-18.csv",
				"train_user_2014-11-19.csv",
				"train_user_2014-11-20.csv",
				"train_user_2014-11-21.csv",
				"train_user_2014-11-22.csv",
				"train_user_2014-11-23.csv",
				"train_user_2014-11-24.csv",
				"train_user_2014-11-25.csv",
				"train_user_2014-11-26.csv",
				"train_user_2014-11-27.csv",
				"train_user_2014-11-28.csv",
				"train_user_2014-11-29.csv",
				"train_user_2014-11-30.csv",
				"train_user_2014-11-18.csv",
				"train_user_2014-12-01.csv",
				"train_user_2014-12-02.csv",
				"train_user_2014-12-03.csv",
				"train_user_2014-12-04.csv",
				"train_user_2014-12-05.csv",
				"train_user_2014-12-06.csv",
				"train_user_2014-12-07.csv",
				"train_user_2014-12-08.csv",
				"train_user_2014-12-09.csv",
				"train_user_2014-12-10.csv",
				"train_user_2014-12-11.csv",
				"train_user_2014-12-12.csv",
				"train_user_2014-12-13.csv",
				"train_user_2014-12-14.csv",
				"train_user_2014-12-15.csv",
				"train_user_2014-12-16.csv",
				"train_user_2014-12-17.csv",
				"train_user_2014-12-18.csv",
				};
				
		//String files[]={"distritest.csv"};
		test.DistriProcee(files);
		test.PrintDistribution("BuyBfDaysDistri.csv", "PairBuyTimes.csv","ActionCountBfBuy.csv");
	}
}
