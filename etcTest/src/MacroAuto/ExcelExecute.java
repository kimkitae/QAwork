package MacroAuto;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Connection;

public class ExcelExecute extends Thread {

	
	public static void test1() throws IOException, InterruptedException {
		String DIR = System.getProperty("user.dir");
		String homePath = System.getProperty("user.home");
		System.out.println(DIR);
		String dir = DIR +"\\macro.xlsm";

		try {

			Process process = Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + dir);
			process.waitFor();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		

	}

	

}
