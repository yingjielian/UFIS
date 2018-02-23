package ufis.far.profile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ufis.common.Configuration;
import ufis.common.Page;
import ufis.far.common.profile.FarConstants;

/**
 * This page represents the web page located at
 * https://test.faculty.utah.edu/proxy/index.html This class should provide all
 * the functionality a test case should need to manipulate the web page, and
 * read data from it. See the main method source code for sample usage details.
 */
public class ProxySelectionPage extends Page {
	public static final String CSS_AutoComplete = "[id^='AutocompleteContainter'] > div[class^='autocomplete'] > div[id^='Autocomplete'] > div:first-child";
	public static final String CSS_Proxy = "#proxy_input";
	public static final String URL = "https://test.faculty.utah.edu/proxy/index.html";

	/**
	 * You must supply a driver object when instantiating this class.
	 */
	@SuppressWarnings("unused")
	private ProxySelectionPage() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This is the only allowable constructor.
	 * 
	 * @param driver
	 *            A WebDriver object.
	 */
	public ProxySelectionPage(final WebDriver driver) {
		super(driver, URL);
	}

	/**
	 * Overrides Page.go so there's no need to cast the return value.
	 * 
	 * @return A reference to this ProxySelectionPage object to allow method
	 *         call chaining.
	 * @see ufis.common.Page
	 */
	public ProxySelectionPage go() {
		super.go();
		return this;
	}

	/**
	 * Override Page.pause() so that this.pause() returns a ProxySelectionPage.
	 * This allows us to avoid type casting: ProxySelectionPage page = new
	 * ProxySelectionPage(driver).pause(); rather than ProxySelectionPage page =
	 * (ProxySelectionPage)(new ProxySelectionPage(driver).pause());
	 */
	public ProxySelectionPage pause() {
		super.pause();
		return this;
	}

	/**
	 * Enters the given text into the proxy field, which causes a list of
	 * suggestions to be displayed. The first option is clicked.
	 * 
	 * @param proxy
	 *            A String value with the search text to use.
	 * @return A ProfilePage object with a URL ending with the chosen proxy's
	 *         unid.
	 */
	public ProfilePage enterAProxy(final String proxy) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_Proxy)));
		WebElement proxyField = driver.findElement(By.cssSelector(CSS_Proxy));
		proxyField.click();
		proxyField.clear();
		proxyField.sendKeys(proxy);
		pause();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_AutoComplete)));
		WebElement autoField = driver.findElement(By.cssSelector(CSS_AutoComplete));
		autoField.click();
		pause();
		return new ProfilePage(driver);
	}

	/**
	 * A convenience method to use when manually testing this class. It prompts
	 * the user for the search text to enter into the proxy field.
	 * 
	 * @return A String holding the text the user supplied.
	 */
	public static String promptProxy() {
		// return JOptionPane.showInputDialog(null, "Enter the proxy search text
		// you want.");
		return Configuration.getMandatoryProperty(FarConstants.FAR_PROFILE_PROXY_STRING);
	}

	/**
	 * A quick-and-dirty way to see this class in action.
	 */
	public static void main(final String[] args) throws Throwable {
		WebDriver driver = ufis.common.TestSuiteUtils.getDriver();
		new IndexPage(driver).go().clickEditYourProfile().promptCreds().login().enterAProxy(promptProxy()).pause();
		driver.quit();
	}
}
