package wordrecite;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class WordHandler {
	private static ReadFile readf = new ReadFile();
	private ArrayList<String> wordlist = readf.Read();//存放从文件中读取的内容，只读
	private ArrayList<String> reciteList = new ArrayList<>();//put viewed word into it
	private ArrayList<String> writelist = new ArrayList<>(wordlist); //wordlist的副本，写入文件时用，执行删除等操作	调用writefile时，重新赋值
	Random rd = new Random();
	
	public String showWord(int i) {
		if(i == -1) return "词库不存在，请导入新词库";
		else{
		System.out.println("writelist size " + writelist.size());
		System.out.println("wordlist size " + wordlist.size());
		RegHandler someword = new RegHandler(wordlist.get(i));
		reciteList.add(wordlist.get(i));
		return someword.re();
		}
	}
	
	public int makeRandom() {
		int i;
		if(wordlist.size() == 0) i = -1;
		else{
			i = rd.nextInt(wordlist.size());
			if (reciteList.contains(wordlist.get(i) ) )
				i = rd.nextInt(wordlist.size());
		}
		return i;
	}
	//when wirtelist's size less than daily learning mount, make random index according to writelist's size 
	//and ignore repeat index.
	public int makeLowRandom(){
		System.out.println("random upper " + writelist.size());
		int i = rd.nextInt(writelist.size());
		return i;
	}
			 
	public String showExplain(int i){
		
		return wordlist.get(i);
	}
	//用iterator将已记住的单词删除
	public void delword(int i){	
		Iterator<String> iterator = writelist.iterator();
		while(iterator.hasNext()) {
		    String str = iterator.next();
		    if(wordlist.get(i).equals(str)) {
		    	iterator.remove();
		    }
		}    
		System.out.println("delete word is " + wordlist.get(i));
	}
	
	public int getWritelistSize(){
//		System.out.println("showwordlistsize"+ writelist.size());
		return writelist.size();
	}
	
	public int getTotalSize(){
		return writelist.size();
	}

	public ArrayList<String> getWordlist() {
//		System.out.println("writelist in Readline"+ writelist.size());
		return wordlist;
	}

	 public void writefile() throws UnsupportedEncodingException, FileNotFoundException{
 
		 OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream("voca.dat"),"UTF-8");
		 BufferedWriter bw = new BufferedWriter(output);	
//		 System.out.println("the writelist when writing" + writelist.size());

		 try{
	
			 for(String englishandchinese: writelist){
				 bw.write(englishandchinese);
				bw.newLine();
			 }
			 bw.close();
		 }
		 catch(IOException e){}
		 
	 }
	
}
