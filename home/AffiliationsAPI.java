package ufis.far.profile.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ufis.common.Utils;
import ufis.far.beans.profile.service.Affiliations.Affiliation;

public class AffiliationsAPI {
	private WebDriver driver;

	public AffiliationsAPI(final WebDriver driver) {
		this.driver = driver;
	}

	public void enterDetails(Affiliation affiliation) {
		driver.findElement(By.id("organization")).sendKeys(affiliation.getName());

		driver.findElement(By.id("abbreviation")).sendKeys(affiliation.getAbbreviation());

		Boolean affiliationType = affiliation.isOrganization();
		if (affiliationType != null) {
			if (affiliationType) {
				driver.findElement(By.id("type1")).click();
			} else {
				driver.findElement(By.id("type2")).click();
			}
		}

		driver.findElement(By.id("role")).sendKeys(affiliation.getRole());

		driver.findElement(By.id("startDate")).sendKeys(affiliation.getStartDate());

		driver.findElement(By.id("endDate")).sendKeys(affiliation.getEndDate());

		Utils.checkBox(driver, By.id("displayed1"), affiliation.isPublishToMyWebpage());
	}

	public void updateName(String name) {
		driver.findElement(By.id("organization")).clear();
		driver.findElement(By.id("organization")).sendKeys(name);
	}
}
