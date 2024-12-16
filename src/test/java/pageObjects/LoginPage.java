package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage
{
    
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-email']")  //selenium.testing@gmail.com
	WebElement txtEmailaddress;
	
	@FindBy(xpath="//input[@id='input-password']")  //test@123
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;
	
	public void setEmailAddress(String email)
	{
		txtEmailaddress.clear();
		txtEmailaddress.sendKeys(email);
	}
	
	public void setPasswords(String pwd)
	{
		txtPassword.clear();
		txtPassword.sendKeys(pwd);
	}
	
	public void clickLoginButton()
	{
		btnLogin.click();
	}
}
