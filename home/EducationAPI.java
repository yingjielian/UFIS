package ufis.far.common.profile.home;

import java.text.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ufis.common.Page;
import ufis.common.WaitUtil;
import ufis.far.beans.profile.home.Education;
import ufis.far.beans.profile.home.Education.EducationDetails;
import ufis.far.beans.profile.home.ResearchSummary;

/**
 * 
 * @author Jack Lian
 *
 */
public class EducationAPI {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	@SuppressWarnings("unused")
	private EducationAPI() {
		throw new UnsupportedOperationException();
	}
	
	public EducationAPI(final WebDriver driver)
	{
		this.driver = driver;
		wait = new WebDriverWait(driver, Page.TIMEOUT);
	}
	
	public void openHome()
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"nav-list\"]/a")));
		driver.findElement(By.xpath("//*[@id=\"nav-list\"]/a")).click();
	}
	
	public void openEducation() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincol\"]/div/div[2]/div[2]/ul[2]/li[2]/a")));
		driver.findElement(By.xpath("//*[@id=\\\"maincol\\\"]/div/div[2]/div[2]/ul[2]/li[2]/a")).click();
	}
	
	public void clickAddItem()
	{
		driver.findElement(By.xpath("//*[@id=\"main-content\"]/h3[2]/a[2]/img")).click();
	}
	
	public String getAddItemHeader()
	{
		if(driver.findElement(By.xpath("/html/body/div[2]/h1")) != null)
			return driver.findElement(By.xpath("/html/body/div[2]/h1")).getText();
		else
			return "Failed";
	}
	
	
	public void addEducation(EducationDetails education) throws ParseException
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("degree")));
//		String summary = researchSummary.getResearchSummary();
		if(education.getDegreeType() != null)
		{
			driver.findElement(By.id("degree")).clear();
			driver.findElement(By.id("degree")).sendKeys(education.getDegreeType());
		}
		
		if(education.getYearReceived() != null)
		{
			driver.findElement(By.id("year")).clear();
			driver.findElement(By.id("year")).sendKeys(education.getYearReceived());
		}
		
		if(education.getDepartment() != null)
		{
			driver.findElement(By.id("subject")).clear();
			driver.findElement(By.id("subject")).sendKeys(education.getDepartment());
		}
		
		if(education.getInstituition() != null)
		{
			driver.findElement(By.id("institution")).clear();
			driver.findElement(By.id("institution")).sendKeys(education.getInstituition());
		}
		
		if(education.getProjectTitle() != null)
		{
			driver.findElement(By.id("title")).clear();
			driver.findElement(By.id("title")).sendKeys(education.getProjectTitle() );
		}
	}
	

 //TODO:Start here.
	public void clickSave()
	{
		driver.findElement(By.xpath("//*[@id=\"form1\"]/input[3]")).click();
	}
	
	public void clickEditCancel()
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/input[4]")));
		driver.findElement(By.xpath("//*[@id=\"form1\"]/input[4]")).click();
	}
	
	public void clickDelete()
	
	{wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main-content\"]/h3[1]/a[3]/img")));
		if(driver.findElement(By.xpath("//*[@id=\"main-content\"]/h3[1]/a[3]/img")) != null)
		{
			driver.findElement(By.xpath("//*[@id=\"main-content\"]/h3[1]/a[3]/img")).click();
		}
		else {
			System.out.print("Research Summary hasn't posted yet.");
		}
		
	}
	
	public void clickDeleteDelete()
	{
		driver.findElement(By.xpath("//*[@id=\"obliviate\"]/input[2]")).click();
	}
	
	public void clickDeleteCancel()
	{
		driver.findElement(By.xpath("//*[@id=\"obliviate\"]/input[3]")).click();
	}
	
	public String getResearchSummaryContent()
	{
		if(driver.findElement(By.xpath("//*[@id=\"main-content\"]/p[1]")) != null)
			return driver.findElement(By.xpath("//*[@id=\"main-content\"]/p[1]")).getText();
		else
			return "Failed";
	}
	
	public boolean isDeleteButtonVisable()
	{
		try{
			driver.findElement(By.xpath("//*[@id=\"main-content\"]/h3[1]/a[3]/img"));
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isExitStateProper()
	{
		return driver.findElements(By.xpath("//*[@id=\"menu-item-8\"]/h3/a")).size() != 0;
	}
	
	public void switchToIFrame() {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cboxIframe")));
		driver.switchTo().frame(driver.findElement(By.className("cboxIframe")));
		WaitUtil.pause(driver, 3);
	}
	
}