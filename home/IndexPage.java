package ufis.far.profile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ufis.common.LoginPage;
import ufis.common.Page;

/**
 * This class represents the https://test.faculty.utah.edu/index.jsp page.
 * It's job is to provide all the methods ever needed by a test case 
 * to manipulate and get information from this web page.
 */
public class IndexPage extends Page
{
	public static final String	URL = "https://test.faculty.utah.edu/index.jsp";
	public static final String	CSS_EditYourProfile = "#top-content > div > div.col-md-5.col-sm-5.col-xs-12 > div > div > a > img";
	
	/**
	 * This class cannot be instantiated without passing in a WebDriver object.
	 */
	@SuppressWarnings("unused")
	private IndexPage()
	{	throw new UnsupportedOperationException();
	}
	
	/**
	 * Constructor.
	 * @param driver A WebDriver object.
	 */
	public IndexPage(final WebDriver driver)
	{	super(driver, URL);
	}
	
	/**
	 * Overrides Page.go() so there's no need to cast the return type
	 * to IndexPage.
	 * @return A reference to this IndexPage object.
	 */
	public IndexPage go()
	{	super.go();
		return this;
	}
	
	/**
	 * Simulates clicking the "Edit Your Profile" button, which takes
	 * you to the login page.
	 * @return A LoginPage object.
	 */
	public LoginPage clickEditYourProfile()
	{	WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_EditYourProfile)));
		WebElement anchor = driver.findElement(By.cssSelector(CSS_EditYourProfile));
		anchor.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CSS_EditYourProfile)));
		return new LoginPage(driver);
	}

	/**
	 * Overrides Page.pause() so there's no need to cast the return type
	 * to an Index Page
	 * @return A reference to this IndexPage object.
	 * @see ufis.common.Page
	 */
	public IndexPage pause()
	{	super.pause();
		return this;
	}

	/**
	 * The main method is a quick and dirty test of this class.
	 * To run, goto the base directory, and type:
	 * 		ant run -Dclass=som.pages.far.profile.IndexPage
	 */
	public static void main(String[] args)
	{	WebDriver driver = ufis.common.TestSuiteUtils.getDriver();
		IndexPage page = new IndexPage(driver).go();
		page.clickEditYourProfile().setPause(5).pause();
		//Close the browser
		driver.quit();
	}
}
