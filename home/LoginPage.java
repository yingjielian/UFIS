package ufis.common;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ufis.far.common.profile.FarConstants;
import ufis.far.profile.ProxySelectionPage;
import ufis.far.reports.ReportPage;

/**
 * This page represents the web page located at
 * https://test.go.utah.edu/cas/login?service=https://test.faculty.utah.edu/
 * proxy/index.html This class should provide all the functionality a test case
 * should need to manipulate the web page, and read data from it.
 */
public class LoginPage extends Page {
	public static final String URL = "https://test.go.utah.edu/cas/login?service=https://test.faculty.utah.edu/proxy/index.html";
	public static final String CSS_uNID = "#username";
	public static final String CSS_Password = "#password";
	public static final String CSS_LOGIN = ".inputareabutton";

	private String unid; // User id
	private String pass; // Password

	/**
	 * The default constructor is not supported. You must supply a WebDriver
	 * object as a parameter.
	 */
	@SuppressWarnings("unused")
	private LoginPage() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This is the only valid constructor. You should set the unid and password
	 * before using this object. A default unid and password are set in the
	 * constructor, but they are fake and will fail.
	 * 
	 * @param driver
	 *            A WebDriver object.
	 */
	public LoginPage(final WebDriver driver) {
		super(driver, LoginPage.URL);
		/*
		 * this.unid = "u1234567"; this.pass = "ThisShouldFail";
		 */
		this.unid = Configuration.getMandatoryProperty(FarConstants.FAR_LOGIN_USERNAME);
		this.pass = Configuration.getMandatoryProperty(FarConstants.FAR_LOGIN_PASSWORD);
	}

	/**
	 * Overrides Page.go() to obviate the need to cast the return type. Without
	 * this override, you would have to write: LoginPage page = (LoginPage)(new
	 * LoginPage(driver).go()); rather than: LoginPage page = new
	 * LoginPage(driver).go();
	 * 
	 * @return A reference to this LoginPage object.
	 */
	public LoginPage go() {
		super.go();
		return this;
	}

	/**
	 * This method enters the unid and password in the appropriate fields and
	 * clicks the Login button. You should set the unid and password prior to
	 * calling this method. There are two ways to do so. First, you may do so
	 * programmatically: loginPage.setUnid([unid]).setPass([password]).login();
	 * Second, you may manually enter the values:
	 * loginPage.promptCreds().login();
	 * 
	 * @return A ProxySelectionPage object.
	 */
	public ProxySelectionPage login() {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_uNID)));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_Password)));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_LOGIN)));
		WebElement domUnid = driver.findElement(By.cssSelector(CSS_uNID));
		WebElement domPass = driver.findElement(By.cssSelector(CSS_Password));
		WebElement domLogin = driver.findElement(By.cssSelector(CSS_LOGIN));
		domUnid.click();
		domUnid.sendKeys(this.unid);
		domPass.click();
		domPass.sendKeys(this.pass);
		this.pause();
		domLogin.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CSS_LOGIN)));
		return new ProxySelectionPage(driver);
	}
	
	
	public ReportPage loginForReports() {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_uNID)));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_Password)));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_LOGIN)));
		WebElement domUnid = driver.findElement(By.cssSelector(CSS_uNID));
		WebElement domPass = driver.findElement(By.cssSelector(CSS_Password));
		WebElement domLogin = driver.findElement(By.cssSelector(CSS_LOGIN));
		domUnid.click();
		domUnid.sendKeys(this.unid);
		domPass.click();
		domPass.sendKeys(this.pass);
		this.pause();
		domLogin.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CSS_LOGIN)));
		return new ReportPage(driver);
	}

	/**
	 * Overrides Page.pause().
	 * 
	 * @return A reference to this LoginPage object.
	 * @see ufis.common.Page
	 */
	public LoginPage pause() {
		super.pause();
		return this;
	}

	/**
	 * Prompts the user for a unid and password, and sets this object's unid and
	 * pass fields accordingly.
	 * 
	 * @return A reference to this LoginPage object for use in method call
	 *         chaining: object.method().method()...
	 */
	public LoginPage promptCreds() {
		JTextField unidField = new JTextField();
		JPasswordField passField = new JPasswordField();
		unidField.setColumns(10);
		passField.setColumns(10);
		// TODO remove commenting, if required.
		/*
		 * JOptionPane.showMessageDialog(null, new Object[]{"uNid", unidField,
		 * "Password", passField}); this.unid = unidField.getText(); this.pass =
		 * passField.getText();
		 */
		return this;
	}

	/**
	 * Sets the password that will be used to login.
	 * 
	 * @param pass
	 *            A non-null String.
	 * @return A reference to this LoginPage object for method call chaining:
	 *         loginPage.setPass([password]).setUnid([unid]).login();
	 */
	public LoginPage setPass(final String pass) {
		this.pass = pass;
		return this;
	}

	/**
	 * Sets the unid that will be used to login.
	 * 
	 * @param unid
	 *            A non-null String.
	 * @return A reference to this LoginPage object for method call chaining:
	 *         loginPage.setPass([password]).setUnid([unid]).login();
	 */
	public LoginPage setUnid(final String unid) {
		this.unid = unid;
		return this;
	}

	/**
	 * Provides a quick-and-dirty way to manually test this class.
	 */
	public static void main(String[] args) {
		WebDriver driver = ufis.common.TestSuiteUtils.getDriver();
		LoginPage page = new LoginPage(driver).go().promptCreds();
		page.login().pause();
		// Close the browser
		driver.quit();
	}
}
