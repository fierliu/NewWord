package wordrecite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
public class ReadDailyNum {
	public Integer readDailyNum() {
		Integer dailyNum = null;
		try{
			File file = new File("dailyNum.dat");
			
			if(file.isFile() && file.exists()){
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine())!=null){
					//System.out.println(lineTxt);
					dailyNum = Integer.parseInt(lineTxt);				
					//System.out.println(line);
				}
				read.close();
			}
			else{System.out.println("dailyNum file not found");
			}
		}catch (Exception e){
			System.out.println("error on read dailyNum file");
			e.printStackTrace();
		}
		return dailyNum;
	}
	
	public void writeDailyNum(String num) {
		 OutputStreamWriter output = null;
		try {
			output = new OutputStreamWriter(new FileOutputStream("dailyNum.dat"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 BufferedWriter bw = new BufferedWriter(output);	
//		 System.out.println("the writelist when writing" + writelist.size());
		 try{	
			 bw.write(num);
			 bw.close();
		 }
		 catch(IOException e){System.out.println("cant write dailyNum.");}
	}
}
