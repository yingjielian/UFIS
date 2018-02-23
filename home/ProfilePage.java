package ufis.far.profile;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ufis.common.Page;

/**
 * This page represents the web page located at
 * https://test.faculty.utah.edu/biography/index.html?proxy_unid=[unid] This
 * class should provide all the functionality a test case should need to
 * manipulate the web page, and read data from it.
 */
public class ProfilePage extends Page {
	public static final String CSS_LOGOUT = "#menu-item-8 > h3 > a";

	/**
	 * This URL is incomplete. It is missing the unid, which gets set in the
	 * constructors.
	 */
	public static final String URL = "https://test.faculty.utah.edu/biography/index.html?proxy_unid=";

	private String unid;
	private LeftNav leftNav;

	/**
	 * Not allowed to create a ProfilePage object without a WebDriver parameter, and
	 * optionally, an unid.
	 */
	@SuppressWarnings("unused")
	private ProfilePage() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This is the constructor that is used by ProxySelectionPage after it selects a
	 * proxy. The unid is obtained from the url, and is assumed to be there.
	 * 
	 * @param driver
	 *            A WebDriver object.
	 */
	public ProfilePage(final WebDriver driver) {
		super(driver, driver.getCurrentUrl());
		int idx = driver.getCurrentUrl().lastIndexOf("=");
		this.unid = driver.getCurrentUrl().substring(idx);
		leftNav = new LeftNav(driver);
	}

	public ProfilePage clickEditAnnualActivityReport() {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#menu-item-1 > h3 > a")));
		WebElement anchor = driver.findElement(By.cssSelector("#menu-item-1 > h3 > a"));
		anchor.click();
		new ProxySelectionPage(driver).enterAProxy(ProxySelectionPage.promptProxy());
		driver.findElement(By.cssSelector(
				"#main-inner-container > div > div.col-sm-8 > div:nth-child(2) > div:nth-child(2) > div.landingApptContent > p > a"))
				.click();
		return new ProfilePage(driver);
	}

	/**
	 * This is the constructor that allows you to navigate directly to the profile
	 * page for a given faculty member's unid. All you have to do is call go(). You
	 * must already be logged in before calling go(). USAGE: WebDriver driver = ...
	 * LoginPage loginPage = new LoginPage(driver).go(); ProfilePage profilePage =
	 * new ProfilePage(driver, [unid]).go();
	 */
	public ProfilePage(final WebDriver driver, final String unid) {
		super(driver, ProfilePage.URL + unid);
		this.unid = unid;
	}

	public LeftNav getLeftNav() {
		return leftNav;
	}

	/**
	 * @return the unid value that was either passed in by the constructor, or that
	 *         appeared in the browser's URL after selecting a value from the
	 *         ProxySelectionPage.
	 */
	public String getUnid() {
		return unid;
	}

	/**
	 * Overrides Page.go() so you can call ProfilePage.go() without casting the
	 * return value to ProfilePage.
	 * 
	 * @return A reference to this ProfilePage object.
	 * @see ufis.common.Page
	 */
	public ProfilePage go() {
		super.go();
		return this;
	}

	/**
	 * Simulates clicking the logout button.
	 * 
	 * @return An IndexPage object.
	 */
	public IndexPage logout() {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_LOGOUT)));
		WebElement logoutButton = driver.findElement(By.cssSelector(CSS_LOGOUT));
		logoutButton.click();
		return new IndexPage(driver);
	}

	/**
	 * Override Page.pause() so that this.pause() returns a ProxySelectionPage. This
	 * allows us to avoid type casting: ProxySelectionPage page = new
	 * ProxySelectionPage(driver).pause(); rather than ProxySelectionPage page =
	 * (ProxySelectionPage)(new ProxySelectionPage(driver).pause());
	 * 
	 * @return A reference to this ProfilePage object to allow method call chaining.
	 * @see ufis.common.Page
	 */
	public ProfilePage pause() {
		super.pause();
		return this;
	}

	/**
	 * A utility method for manual testing. Prompts the user to enter a unid, and
	 * returns the entered value. USAGE: ProfilePage page = new ProfilePage(driver,
	 * ProfilePage.promptUnid()).go();
	 * 
	 * @return String The unid the user supplied.
	 */
	public static String promptUnid() {
		return JOptionPane.showInputDialog(null, "Enter the faculty unid of the profile you want to view.");
	}

	/**
	 * A quick-and-dirty method to see this class in action.
	 */
	public static void main(final String[] args) throws Throwable {
		WebDriver driver = ufis.common.TestSuiteUtils.getDriver();
		new IndexPage(driver).go().clickEditYourProfile().promptCreds().login()
				.enterAProxy(ProxySelectionPage.promptProxy()).pause().logout().pause();
		driver.quit();
	}
}
