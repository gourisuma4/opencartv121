package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
      //DataProvider 1
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\opencart_testdata2.xlsx"; //taking xl file from testdata folder
		ExcelUtility xlutil=new ExcelUtility(path);  //creating object for xlutility
		
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String [totalrows][totalcols];  //created for 2 dimension Array which can store excel data 
		
		for(int i=1;i<=totalrows;i++)  //1  //read data from excel and store in 2 dimensional array
		{
			for(int j=0;j<totalcols; j++)  //0 //i=rows j=cols
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j); //1,0
				
			}
			
		}
		return logindata;  //returning 2 dimensional array
		
	}
	
	
	//DataProvider 2
	
	//DataProvider 3
	
	//DataProvider 4
	
}
