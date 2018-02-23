package ufis.far.profile;

import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ufis.common.MouseUtil;
import ufis.common.Page;
import ufis.common.Utils;
import ufis.common.WaitUtil;
import ufis.far.survey.SurveyAPI.ActionType;

/**
 * @author Michelle Nguyen
 */
public class ProfileAPI {
	private WebDriver driver;
	private WebDriverWait wait;

	public ProfileAPI(final WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Page.TIMEOUT);
	}

	public boolean checkForEntryInTable(String sectionName, String entry) {
		System.out.println("Checking list of " + sectionName + " for: " + entry);

		boolean seenEntry = false;
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().equals(sectionName)) {
				WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));
				Scanner scanner = new Scanner(div.getText());
				while (scanner.hasNext()) {
					if (scanner.nextLine().contains(entry)) {
						seenEntry = true;
						System.out.println("\"" + entry + "\" found!");
						break;
					}
				}

				scanner.close();
			}
		}

		if (!seenEntry) {
			System.out.println("\"" + entry + "\" NOT found!");
		}

		return seenEntry;
	}

	public void clickAction(ActionType actionType) {
		switchToIFrame();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("input[value=" + actionType.toString() + "]")));
		driver.findElement(By.cssSelector("input[value=" + actionType.toString() + "]")).click();
		driver.switchTo().defaultContent();
	}

	public void clickAddItem(String sectionName) {
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().contains(sectionName)) {
				List<WebElement> sectionButtons = section.findElements(By.tagName("A"));
				sectionButtons.get(1).click();
				break;
			}
		}
	}

	public void clickCheckBoxToHideShowItem(int index, boolean show) {
		try {
			Utils.checkBox(driver, By.xpath("//*[@id=\"list[" + index + "].displayable1\"]"), show);
		} catch (Exception e) {
			Utils.checkBox(driver, By.xpath("//*[@id=\"list[" + index + "].displayed1\"]"), show);
		}
	}

	public void clickDeleteEntry(String sectionName, int index) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().contains(sectionName)) {
				WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));
				List<WebElement> entries = div.findElements(By.tagName("li"));
				WebElement entry = entries.get(index);
				List<WebElement> buttons = entry.findElements(By.tagName("a"));
				buttons.get(buttons.size() - 1).click();
			}
		}
	}

	public void clickEditEntry(String sectionName, int index) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().contains(sectionName)) {
				WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));
				List<WebElement> entries = div.findElements(By.tagName("li"));
				WebElement entry = entries.get(index);
				List<WebElement> buttons = entry.findElements(By.tagName("a"));
				buttons.get(buttons.size() - 2).click();
			}
		}
	}

	public void clickSelectAll() {
		switchToIFrame();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#selectNoneLink")));
		driver.findElement(By.cssSelector("#selectAllLink")).click();
		driver.switchTo().defaultContent();
	}

	public void clickSelectNone() {
		switchToIFrame();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#selectNoneLink")));
		driver.findElement(By.cssSelector("#selectNoneLink")).click();
		driver.switchTo().defaultContent();
	}

	public void clickShowAndHideItems(String sectionName) {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().contains(sectionName)) {
				List<WebElement> sectionButtons = section.findElements(By.tagName("A"));
				sectionButtons.get(3).click();
			}
		}
	}

	public void clickSortItems(String sectionName) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().contains(sectionName)) {
				List<WebElement> sectionButtons = section.findElements(By.tagName("A"));
				sectionButtons.get(2).click();
			}
		}
	}

	public void clickTab(String moduleName) {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//*[@id=\"maincol\"]/div/div[2]/div[2]/ul[1]")));
			WebElement element = driver.findElement(By.xpath("//*[@id=\"maincol\"]/div/div[2]/div[2]/ul[1]"));
			List<WebElement> tabs = element.findElements(By.id("nav-list"));
			for (WebElement tab : tabs) {
				if (tab.getAttribute("class").contains(moduleName)) {
					tab.click();
					break;
				}
			}
		} catch (Exception e) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"header-mini-navigation\"]")));
			WebElement element = driver.findElement(By.xpath("//*[@id=\"header-mini-navigation\"]"));
			List<WebElement> tabs = element.findElements(By.id("nav-list"));
			for (WebElement tab : tabs) {
				if (tab.getAttribute("class").contains(moduleName)) {
					tab.click();
					break;
				}
			}
		}
	}

	/*
	 * Never gets used by tests, but useful for clearing clutter
	 */
	public void deleteAllEntriesInSection(String sectionName) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().contains(sectionName)) {
				WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));
				List<WebElement> entries = div.findElements(By.tagName("li"));

				for (WebElement entry : entries) {
					try {
						List<WebElement> buttons = entry.findElements(By.tagName("a"));
						buttons.get(buttons.size() - 1).click();
						clickAction(ActionType.DELETE);
						deleteAllEntriesInSection(sectionName);
					} catch (Exception e) {
						return;
					}
				}
			}
		}
	}

	public String getErrorMessage() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("*.errors")));
		WebElement element = driver.findElement(By.id("*.errors"));
		return element.getText();
	}

	public int getIndexOfEntryToEditOrDelete(String sectionName, String entry) {
		int index = 0;

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().equals(sectionName)) {
				WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));
				List<WebElement> rows = div.findElements(By.tagName("li"));

				for (int i = 0; i < rows.size(); i++) {
					if (rows.get(i).getText().contains(entry)) {
						index = i;
						break;
					}
				}
			}
		}

		return index;
	}

	public int getIndexOfEntryToShowOrHide(String sectionName, String entry) {
		switchToIFrame();

		int index = 0;

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"form1\"]/fieldset/table")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"form1\"]/fieldset/table"));
		List<WebElement> rows = element.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			List<WebElement> tds = row.findElements(By.tagName("td"));

			if (tds.get(1).getText().contains(entry)) {
				index = rows.indexOf(row);
				break;
			}
		}

		return index;
	}

	public int getNumOfEntries(String sectionName) {
		int size = 0;

		if (sectionHasEntries(sectionName)) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
			WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
			List<WebElement> sections = element.findElements(By.tagName("H3"));

			for (WebElement section : sections) {
				if (section.getText().equals(sectionName)) {
					WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));

					List<WebElement> rows = div.findElements(By.tagName("li"));
					size = rows.size();
				}
			}
		}

		return size;
	}

	public String getTextAtIndex(String sectionName, int index) {
		String name = "";

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().equals(sectionName)) {
				WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));

				List<WebElement> rows = div.findElements(By.tagName("li"));
				name = rows.get(index - 1).getText();
			}
		}

		return name;
	}

	public String getTextAtIndexInShowHideTable(int index) {
		switchToIFrame();
		WebElement table = driver.findElement(By.xpath("//*[@id=\"dndOrderTable\"]"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		String line = rows.get(index - 1).getText();
		return line.substring(2, line.length() - 2);
	}

	public boolean sectionHasEntries(String sectionName) {
		boolean hasEntries = true;

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]")));
		WebElement element = driver.findElement(By.xpath("//*[@id=\"main-content\"]"));
		List<WebElement> sections = element.findElements(By.tagName("H3"));

		for (WebElement section : sections) {
			if (section.getText().equals(sectionName)) {
				WebElement div = section.findElement(By.xpath("following-sibling::*[position()=1]"));
				if (div.getText().equals("No entries selected for display.")
						|| div.getText().equals("No data entered.")) {
					hasEntries = false;
				}
			}
		}

		return hasEntries;
	}

	public void sortElements(int sourceIndex, int destinationIndex) {
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@id='dndOrderTable']/tbody/tr[" + (sourceIndex) + "]/td[3]")));
		WebElement source = driver
				.findElement(By.xpath("//*[@id='dndOrderTable']/tbody/tr[" + (sourceIndex) + "]/td[3]"));

		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//*[@id='dndOrderTable']/tbody/tr[" + (destinationIndex) + "]/td[3]")));
		WebElement target = driver
				.findElement(By.xpath("//*[@id='dndOrderTable']/tbody/tr[" + (destinationIndex) + "]/td[3]"));

		MouseUtil.clickMoveRelease(driver, source, target);
	}

	public void switchToIFrame() {
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cboxIframe")));
		driver.switchTo().frame(driver.findElement(By.className("cboxIframe")));
		WaitUtil.pause(driver, 3);
	}
}
