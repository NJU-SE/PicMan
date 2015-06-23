package usrinfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class pre_work
{
	public static void gain_data() throws IOException
	{
		String replace_sym = "[!,.:;?��()\\[\\]{}/<>\t&\\-=\\*\\\\]";
		File tempFile = new File("train_data.txt");
		tempFile.createNewFile();
		PrintWriter fileoutput = new PrintWriter(tempFile);
		
	}
}
