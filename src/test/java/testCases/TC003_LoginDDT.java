

package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import baseClass.BaseClass;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

/* Data is valid -login Success-test pass -logout
 *                 login unSuccess-test Fail 
   Data is Invalid-login Success-test FAIL 
 *                  login unSuccess-test pass -logout
*/
public class TC003_LoginDDT extends BaseClass

{
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="DataDriven")  //getting data provider from different class //we are explicitly saying dataprovider class this is present in different package 
	public void verify_LoginDDT(String email,String pwd,String exp)
	{
		logger.info("******Starting TC003_LoginDDT*********");
		
		
		try
		{
		//home page
		HomePage hp=new HomePage(driver);
		
		logger.info("hp object created");
		
		hp.clickMyAccount();
		hp.clickLogin();
		
		//login page
		LoginPage lp=new LoginPage(driver);
		lp.setEmailAddress(email);
		lp.setPasswords(pwd);
		lp.clickLoginButton();
		
		//MyAccount page
		MyAccountPage macc=new MyAccountPage(driver);
	    boolean targetpage=macc.isMyAccountPageExist();
	   
	    /* Data is valid -login Success-test pass -logout
	     *                 login unSuccess-test Fail 
	       Data is Invalid-login Success-test FAIL 
	     *                  login unSuccess-test pass -logout
	    */
	    
	    if(exp.equalsIgnoreCase("valid"))
	    {
	          if(targetpage==true)
	          {
	             macc.clickLogout();
	             Assert.assertTrue(true);
	          }
	          else
	        	  
	          {
	        	  Assert.assertTrue(false);
	          }
	    }
	    if(exp.equalsIgnoreCase("Invalid"))
	    {
	    	if(targetpage==true)
	    	{
	    		macc.clickLogout();
	    		Assert.assertTrue(false);
	    	}
	    	else
	    	{
	    		Assert.assertTrue(true);
	    	}
	    }
	}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("******Finished TC003_LoginDDT*********");
	    
}
}
