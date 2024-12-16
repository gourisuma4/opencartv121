package baseClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;  //log4j
import org.apache.logging.log4j.Logger;   //log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;




public class BaseClass 
{
	public  Logger logger ;  //log4j
	public static WebDriver driver;  //we are creating driver, referring every where, addiationally in report manager this base class object also having theire own driver 
	                                 // we have to make that also static to maintaine common variable instead of 2   
	public Properties p;
    
    @BeforeClass(groups={"Sanity","Regression","Master"})  //"DataDriven"
    @Parameters({"os","browser"})
    public void setup(String os,String br) throws IOException
    {
    	//LOADING configuration file
    	FileReader file=new FileReader("./src//test//resources//config.properties");  //alternative class for FileInputStream
    	p=new Properties();
    	p.load(file);
    	logger=LogManager.getLogger(this.getClass());
    	logger.info("PROGRAM STRATED******************");
    	//from which class we want to generate the logs, but here there is no specific class because we can run multiple classes
    			//runtime it dynamically should take the class name which test case we are running by using "this" keyword
    			//"this" represents  the object of the dynamic class 
    	//it will get the current class loggers and stored in the variable logger;		
    	
     /* below set up for execution in grid enviorment * 
      */
     if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
     {
    	 DesiredCapabilities capabilities=new DesiredCapabilities();
    	 
    	 //os
    	 if(os.equalsIgnoreCase("windows"))
    	 {
    		 capabilities.setPlatform(Platform.WIN11);
    		 
    	 }
    	 else if(os.equalsIgnoreCase("LINUX"))
    	 {
    		 capabilities.setPlatform(Platform.LINUX);
    	 }
    	 else if(os.equalsIgnoreCase("mac"))
    	 {
    		 capabilities.setPlatform(Platform.MAC);
    	 }
    	 else
    	 {
    		 System.out.println("no matching os ");
    		 return;
    		 
    	 }
    	 
    	 //browser
    	 switch(br.toLowerCase())	  //this is for remote enviornment 
         {
        	case "chrome":capabilities.setBrowserName("chrome"); break;
        	case "edge":capabilities.setBrowserName("MicrosoftEdge"); break;
        	case "firefox":capabilities.setBrowserName("Firefox"); break;
            default:System.out.println("No Matching browser name"); return;
         }
    	 
    	 driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
    	 
     }
     if(p.getProperty("execution_env").equalsIgnoreCase("local"))
     {
    	 
       switch(br.toLowerCase())	  //this is for local enviornment 
       {
    	case "chrome":driver=new ChromeDriver(); break;
    	case "edge":driver=new EdgeDriver(); break;
    	case "firefox":driver=new FirefoxDriver(); break;
        default:System.out.println("Invalid browser name"); return;
       }
       
     }
   	 driver.manage().deleteAllCookies();  //deletes all the cookies
   	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
     driver.get(p.getProperty("appURL2"));  //reading url from the properties file
     driver.manage().window().maximize();
    
    }
    
     @AfterClass(groups={"Sanity","Regression","Master"})//"DataDriven"
    public void teardown()
    {
   	 driver.quit();
    }
     public String randomString()
     {
    	 
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
     }
     
  
	public String randomNumber()
     {
    	 String generatednum=RandomStringUtils.randomNumeric(10);
 		return generatednum;
     }
	public String randomAlphaNumeric()
    {
   	 String generatedString=RandomStringUtils.randomAlphabetic(3);
   	 String generatednum=RandomStringUtils.randomNumeric(3);
		return (generatedString+"@"+generatednum);
    }
	
	public String captureScreen(String tname) throws IOException //when test method is failed execute this method
	{
		String timestamp=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
		TakesScreenshot takeScreenShot= (TakesScreenshot) driver;
		File sourceFile =takeScreenShot.getScreenshotAs(OutputType.FILE);
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+tname + "_" +timestamp +".png";
		File targetFile=new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
    
}
