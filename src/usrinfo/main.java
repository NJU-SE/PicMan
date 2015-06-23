package usrinfo;
import java.io.IOException;


public class main
{
	public static void main(String[] args) throws IOException
	{
		//datahandler handler = new datahandler();
		//handler.ini_handler();
		//handler.train();
		//handler.get_neighbour();
		//handler.get_near_nei();
		//handler.get_relation();
		newFileHandler handler = new newFileHandler();
		handler.readfile();
		handler.getNei();
	}
}
