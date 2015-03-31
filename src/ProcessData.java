import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;

public class ProcessData {
	
	class Feature{
		int action_num;
		HashSet<String> action_day;
		HashSet<Integer> distinct;
		
		public Feature() {
			super();
			action_num = 0;
			action_day = new HashSet<String>();
			distinct = new HashSet<Integer>();
		}
	}
	
	HashMap<String, Feature[]> pairMap;
	HashMap<String, Feature[]> userMap;
	HashMap<String, Feature[]> itemMap;
	
	public ProcessData() {
		super();
		pairMap = new HashMap<String, Feature[]>();
		userMap = new HashMap<String, Feature[]>();
		itemMap = new HashMap<String, Feature[]>();
	}
	
	public void readData(String filename){
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(filename));
			String line = null;
			br.readLine();
			int count = 1;
			while((line = br.readLine()) != null){
				
				if(count % 1000 == 0)
					System.out.println("Count: " + String.valueOf(count));
				
				String[] strs = line.trim().split(",");
				String keyPair = strs[0] + '_' + strs[1];
				String keyUser = strs[0];
				String keyItem = strs[1];
				
				int cat = Integer.parseInt(strs[2]);
				String date = strs[5].trim().split(" ")[0];
				
				//pair feature
				Feature[] fea = null;
				if(!pairMap.containsKey(keyPair)){
					fea = new Feature[4];
					for(int i = 0; i < 4; i++) fea[i] = new Feature();
				}else{
					fea = pairMap.get(keyPair);
				}
				fea[cat-1].action_num += 1;
				fea[cat-1].action_day.add(date);
				pairMap.put(keyPair, fea);
				
				//user feature
				fea = null;
				if(!userMap.containsKey(keyUser)){
					fea = new Feature[4];
					for(int i = 0; i < 4; i++) fea[i] = new Feature();
				}else{
					fea = userMap.get(keyUser);
				}
				fea[cat-1].action_num += 1;
				fea[cat-1].action_day.add(date);
				fea[cat-1].distinct.add(Integer.parseInt(strs[1]));
				userMap.put(keyUser, fea);
				
				//item feature
				fea = null;
				if(!itemMap.containsKey(keyItem)){
					fea = new Feature[4];
					for(int i = 0; i < 4; i++) fea[i] = new Feature();
				}else{
					fea = itemMap.get(keyItem);
				}
				fea[cat-1].action_num += 1;
				fea[cat-1].action_day.add(date);
				fea[cat-1].distinct.add(Integer.parseInt(strs[0]));
				itemMap.put(keyItem, fea);
				
				count++;
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void dumpPairStatistic(String filename){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(filename));
			bw.write("user_id,item_id,"
					+ "action#,action_days#,behavior_type,"
					+ "action#,action_days#,behavior_type,"
					+ "action#,action_days#,behavior_type,"
					+ "action#,action_days#,behavior_type");
			bw.newLine();
			
			for(String key : pairMap.keySet()){
				Feature[] fea = pairMap.get(key);
				String[] strs = key.split("_");
				bw.write(strs[0] + "," + strs[1]);
				int i = 1;
				for(Feature f : fea){
					bw.write(","+String.valueOf(f.action_num)+","+String.valueOf(f.action_day.size())
							+","+String.valueOf(i));
					i++;
				}
				bw.newLine();
				bw.flush();
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void dumpUserStatistic(String filename){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(filename));
			bw.write("user_id,"
					+ "action#,action_days#,distinct_action_item#,behavior_type,"
					+ "action#,action_days#,distinct_action_item#,behavior_type,"
					+ "action#,action_days#,distinct_action_item#,behavior_type,"
					+ "action#,action_days#,distinct_action_item#,behavior_type");
			bw.newLine();
			
			for(String key : userMap.keySet()){
				Feature[] fea = userMap.get(key);
				bw.write(key);
				int i = 1;
				for(Feature f : fea){
					bw.write(","+String.valueOf(f.action_num)+","+String.valueOf(f.action_day.size())
							+","+String.valueOf(f.distinct.size())+","+String.valueOf(i));
					i++;
				}
				bw.newLine();
				bw.flush();
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void dumpItemStatistic(String filename){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(filename));
			bw.write("item_id,"
					+ "action#,action_days#,distinct_action_user#,behavior_type,"
					+ "action#,action_days#,distinct_action_user#,behavior_type,"
					+ "action#,action_days#,distinct_action_user#,behavior_type,"
					+ "action#,action_days#,distinct_action_user#,behavior_type");
			bw.newLine();
			
			for(String key : itemMap.keySet()){
				Feature[] fea = itemMap.get(key);
				bw.write(key);
				int i = 1;
				for(Feature f : fea){
					bw.write(","+String.valueOf(f.action_num)+","+String.valueOf(f.action_day.size())
							+","+String.valueOf(f.distinct.size())+","+String.valueOf(i));
					i++;
				}
				bw.newLine();
				bw.flush();
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProcessData pd = new ProcessData();
		System.out.println("read data...");
		pd.readData("data\\tianchi_mobile_recommend_train_user.csv");
		
		System.out.println("dump train item...");
		pd.dumpItemStatistic("data\\train_item.csv");
		
		System.out.println("dump train user...");
		pd.dumpUserStatistic("data\\train_user.csv");
		
		System.out.println("dump train pair...");
		pd.dumpPairStatistic("data\\train_pair.csv");
	}

}
