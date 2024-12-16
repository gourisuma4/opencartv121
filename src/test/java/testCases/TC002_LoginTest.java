package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass
{
    @Test(groups={"Sanity","Master"})
	public void verify_LoginPage()
	{
		logger.info("*******Starting TC002_LoginTest************");
		try
		{
		//home page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//login page
		LoginPage lp=new LoginPage(driver);
		lp.setEmailAddress(p.getProperty("email"));
		lp.setPasswords(p.getProperty("password"));
		lp.clickLoginButton();
		
		//MyAccount page
		MyAccountPage macc=new MyAccountPage(driver);
	    boolean targetpage=macc.isMyAccountPageExist();
	   
	    Assert.assertTrue(targetpage);  // Assert.assertEquals(target page,true, "Login Failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
	    logger.info("*******Finishing TC002_LoginTest************\"");
	}

}
