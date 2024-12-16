package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import baseClass.BaseClass;

public class ExtentReportManager implements ITestListener
{

	
	public ExtentSparkReporter sparkReporter;  //UI of the report
	public ExtentReports extent;               //populate common information on the report  
	public ExtentTest test;                    //creating test case entries in the report and updating the status of the test methods   
	String RepName;
	
	public void onStart(ITestContext testContext)
	{
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");  //which format we want to generate the report
		Date dt=new Date();                                                 //to create date
		String currentdatetimestamp=df.format(new Date());
		*/
		
		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());  //time stamp to generate the report
		
		RepName="Test-Report"+timestamp+".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+RepName);  //specify the location of the report to be generated
		sparkReporter.config().setDocumentTitle("Opencart Automation Report"); //title of the report
		sparkReporter.config().setReportName("Opencart Functional Testing");   //name of the report
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application","Opencart");
		extent.setSystemInfo("Module","Admin");
		extent.setSystemInfo("Sub Module","Customers");
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Enviornment","QA");
		
		String os=testContext.getCurrentXmlTest().getParameter("os");   //capture browser name from xml file
		extent.setSystemInfo("Operating System",os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser",browser);
		
		List<String> includeGroups=testContext.getCurrentXmlTest().getIncludedGroups(); //will capture the group names from xml file 
		if(!includeGroups.isEmpty())
		{
			extent.setSystemInfo("Groups",includeGroups.toString());
		}
		
	}
		public void onTestSuccess(ITestResult result) 
		 {
			 test=extent.createTest(result.getTestClass().getName()); //create a new entry in the report
			 test.assignCategory(result.getMethod().getGroups());  //to display groups in report
			 test.log(Status.PASS,result.getName()+"got successfully executed"); //update status p/f/s
		 }
		
		public void onTestFailure(ITestResult result) 
		 {
			 test=extent.createTest(result.getTestClass().getName()); //create a new entry in the report
			 test.assignCategory(result.getMethod().getGroups());
			 
			 test.log(Status.FAIL,result.getName()+"got failed"); //update status p/f/s
			 test.log(Status.INFO, result.getThrowable().getMessage());
			 try
			 {
				 String imgpath=new BaseClass().captureScreen(result.getName());  //return the location of the image
				 test.addScreenCaptureFromPath(imgpath);
			 }
			 catch(IOException e1)
			 {
				 e1.printStackTrace();  //it will print some warning message in console window
			 }
		 }
		public void onTestSkipped(ITestResult result) 
		 {
			test=extent.createTest(result.getTestClass().getName()); //create a new entry in the report
			test.assignCategory(result.getMethod().getGroups()); 
			test.log(Status.SKIP, result.getName()+"got skipped:"); //update status p/f/s
			test.log(Status.INFO, result.getThrowable().getMessage());
		 }
		 public void onFinish(ITestContext testContext)
		 {
				extent.flush();
				String pathOffExtentReport=System.getProperty("user.dir")+"\\reports\\"+RepName;  //path of the report
				File extentReport=new File(pathOffExtentReport); //
				try
				{
					Desktop.getDesktop().browse(extentReport.toURI());  //it will automatically open the report no need to do manually
				}catch(IOException e1)
				 {
					 e1.printStackTrace();  //it will print some warning message in console window
				 }
				
			/*	//if you want to send a report automatically we need this code
				  try
				 {
				 URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+RepName);
				 
				 //create the email message
				 ImageHtmlEmail email=new ImageHtmlEmail();
				 email.setDataSourceResolver(new DataSourceUrlResolver(url));
				 email.setHostName("smtp.googlemail.com") ;
				 email.setSmtpPort(465);
				 email.setAuthenticator(new DefaultAuthenticator("selenium.testing@gmail.com"," test@123"));
				 email.setSSLOnConnect (true) ;
				 email.setFrom("selenium.testing@gmail.com"); //Sender
				 email.setSubject("Test Results");
				 email.setMsg("Please find the attached report...");
				 email.addTo("gourisuma4@gmail.com");  //receiver
				 email.attach(url, "extent report", "please check report....");
				 email.send();  //send email
				 }
				  catch(Exception e)
				  {
					  e.printStackTrace();  //it will print some warning message in console window
					 
				  }
						 
				*/		 
				 
		 	}
	
	
}
