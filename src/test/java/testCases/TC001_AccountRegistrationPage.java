package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import baseClass.BaseClass;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationPage extends BaseClass
{
       
      
     @Test(groups={"Regression","Master"})
     public void verify_account_registration()
     {
    	 
    	 logger.info("********starting TC001 AccountRegistartionPage ********");
        try 
        {
    	HomePage hp=new HomePage(driver);
    	 
    	hp.clickMyAccount();
    	
    	logger.info("Clicked on MyAccount Link");
    	 hp.clickRegister();
    	 logger.info("Clicked on clickRegister Link");
    	 AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
    	 logger.info("providing customer details");
    	 regpage.setFirstName(randomString().toUpperCase());
    	 regpage.setLastName(randomString().toUpperCase());
    	 regpage.setEmail(randomString()+"@gmail.com");  //randomly generated email
    	 regpage.setTelePhone(randomNumber());
    	 String pwd=randomAlphaNumeric();
    	 regpage.setPassWord(pwd);
    	 regpage.setConfPassWord(pwd);
    	 regpage.setPrivacyPolicy();
    	 regpage.clickButton();
    	 logger.info("Validating expected message");
    	 String confmsg=regpage.getConfirmationMsg();
    	if(confmsg.equals("Your Account Has Been Created!"))
    	{
    		Assert.assertTrue(true);
    	}
    	else
    	{
    		logger.error("Test failed...");
    		logger.debug("Debug logs ...");
    		Assert.assertTrue(false);
    	}
    	// Assert.assertEquals(confmsg,"Your Account Has Been Created!!!!");
     }
    catch(Exception e)
    {
    	//logger.error("Test failed...");
    	//logger.debug("Debug logs ...");  //in xml file debug is not enough here also we have to write debug method get detailed steps for testcase fail
    	 Assert.fail();
     }
        logger.info("***finished TC001 AccountRegistartionPage ****");
     }
    	
    	
    	
}
