package wordrecite;

import java.io.*;
import java.util.ArrayList;

public class ReadFile{
	
	public  ArrayList<String> Read(){
		ArrayList<String> wordwordList = new ArrayList<String>();
		System.out.println("read again");
		try{
			File file = new File("voca.dat");
			
			if(file.isFile() && file.exists()){
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine())!=null){
					//System.out.println(lineTxt);
					
					wordwordList.add(lineTxt);
					//System.out.println(line);
				}
				read.close();
			}
			else{System.out.println("file not found");
			}
		}catch (Exception e){
			System.out.println("error on read file");
			e.printStackTrace();
		}
		
		return wordwordList;

	}
	
	
}	 
