package wordrecite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Copy {
	public Copy(String source ) throws IOException{
		 Files.copy(Paths.get(source), Paths.get("voca.dat"), StandardCopyOption.REPLACE_EXISTING);
		 
	}
}
