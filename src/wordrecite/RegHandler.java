package wordrecite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegHandler {
	private String line;
	public RegHandler(String line){
		this.line = line;
	}
	 public String re(){

		Pattern p;
		Matcher r;
		String english = null;
		p = Pattern.compile("([a-z]|[A-Z]).+([a-z]|[A-Z])");
		r = p.matcher(line);
		if(r.find()){
			english = r.group();
		}

		return english;
	}
}
