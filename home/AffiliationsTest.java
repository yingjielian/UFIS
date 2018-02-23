package ufis.testSuite.far.profile.service;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import ufis.common.TestSuiteUtils;
import ufis.far.beans.profile.service.Affiliations;
import ufis.far.beans.profile.service.Affiliations.Affiliation;
import ufis.far.common.profile.service.AffiliationsUtils;
import ufis.far.profile.IndexPage;
import ufis.far.profile.ProfileAPI;
import ufis.far.profile.ProfilePage;
import ufis.far.profile.ProxySelectionPage;
import ufis.far.profile.service.AffiliationsAPI;
import ufis.far.survey.SurveyAPI.ActionType;

/**
 * @author Michelle Nguyen
 */
public class AffiliationsTest {

	private static WebDriver driver;
	AffiliationsAPI affiliationsAPI;
	static ProfilePage profilePage;
	static ProfileAPI profileAPI;
	static Affiliations affiliationsObject;
	public static final String SECTION = "Affiliations";
	public static final String MODULE = "philanthropy-module";
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public AffiliationsTest() {
		affiliationsAPI = new AffiliationsAPI(driver);
		profileAPI = new ProfileAPI(driver);
	}

	@BeforeClass
	public static void setUpClass() {
		driver = TestSuiteUtils.getDriver();

		try {
			new IndexPage(driver).go().clickEditYourProfile().promptCreds().login()
					.enterAProxy(ProxySelectionPage.promptProxy());

		} catch (Exception ex) {
			driver.quit();
			driver = null;
			throw ex;
		}
		profilePage = new ProfilePage(driver);
	}

	@AfterClass
	public static void tearDownClass() {
		cleanUp();
	}

	@Before
	public void setUp() {
		profileAPI.clickTab(MODULE);
		affiliationsObject = AffiliationsUtils.getAffiliationsObject();
	}

	@After
	public void breakDown() {
	}

	private static void cleanUp() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public String addAffiliationAndAward(Affiliation affiliation) {
		profileAPI.clickAddItem(SECTION);
		profileAPI.switchToIFrame();
		Date date = new Date();
		affiliation.setName(affiliation.getName() + " - " + dateFormat.format(date));
		affiliationsAPI.enterDetails(affiliation);
		profileAPI.clickAction(ActionType.SAVE);
		return affiliation.getName();
	}

	@Test
	public void testUserCanAddOneAffiliationAndAward() {
		addAffiliationAndAward(affiliationsObject.getAffiliations().get(0));

		// Check success of add
		assertTrue(
				affiliationsObject.getAffiliations().get(0).getName()
						+ " was not found in the list of Current Affiliations!",
				profileAPI.checkForEntryInTable(SECTION, affiliationsObject.getAffiliations().get(0).getName()));
	}

	@Test
	public void testUserCanAddMultipleAffiliations() {
		profileAPI.clickAddItem(SECTION);
		int count = affiliationsObject.getAffiliations().size();
		for (Affiliation affiliation : affiliationsObject.getAffiliations()) {
			profileAPI.switchToIFrame();
			Date date = new Date();
			affiliation.setName(affiliation.getName() + " - " + dateFormat.format(date));
			affiliationsAPI.enterDetails(affiliation);

			// Click save
			if (count == 1) {
				profileAPI.clickAction(ActionType.SAVE);
			} else if (count > 1) {
				profileAPI.clickAction(ActionType.SAVEANDADDANOTHER);
			}

			count--;
		}

		for (Affiliation affiliation : affiliationsObject.getAffiliations()) {
			// Check success of adds
			assertTrue(affiliation.getName() + " was not found in the list of Current Affiliations!",
					profileAPI.checkForEntryInTable(SECTION, affiliation.getName()));
		}
	}

	@Test
	public void testUserCanDeleteAffiliationAndAward() {
		// Add an entry that we will be deleting
		Affiliation affiliationToDelete = affiliationsObject.getAffiliations().get(0);
		affiliationToDelete.setName("Affiliation to Delete");
		String name = addAffiliationAndAward(affiliationToDelete);

		// Get index of affiliationToDelete
		int index = profileAPI.getIndexOfEntryToEditOrDelete(SECTION, name);

		// Delete entry at that index
		profileAPI.clickDeleteEntry(SECTION, index);
		profileAPI.clickAction(ActionType.DELETE);

		// Check that the affiliation is no longer in the table
		assertFalse(profileAPI.checkForEntryInTable(SECTION, name));
	}

	@Test
	public void testUserCanEditAffiliationAndAward() {
		// Add an entry that we will be updating
		Affiliation affiliationToUpdate = affiliationsObject.getAffiliations().get(0);
		affiliationToUpdate.setName("Affiliation to Update");
		String oldName = addAffiliationAndAward(affiliationToUpdate);

		// Get index of affiliationToUpdate
		int index = profileAPI.getIndexOfEntryToEditOrDelete(SECTION, oldName);

		// Edit entry at that index
		profileAPI.clickEditEntry(SECTION, index);
		profileAPI.switchToIFrame();
		Date date = new Date();
		String newName = "Affiliation with Edited Name - " + dateFormat.format(date);
		affiliationsAPI.updateName(newName);
		profileAPI.clickAction(ActionType.SAVE);

		// Check that the updated affiliation is in the table
		assertFalse(profileAPI.checkForEntryInTable(SECTION, oldName));
		assertTrue(profileAPI.checkForEntryInTable(SECTION, newName));
	}

	@Test
	public void testUserCanSortAffiliationAndAward() {
		// If there are no entries in the table, add some
		if (!profileAPI.sectionHasEntries(SECTION)) {
			testUserCanAddMultipleAffiliations();
		}

		// Click on the sort items icon
		profileAPI.clickSortItems(SECTION);

		// Get names of entries at positions 1 and 2
		String name1 = profileAPI.getTextAtIndexInShowHideTable(1);
		String name2 = profileAPI.getTextAtIndexInShowHideTable(2);

		// Swap entries at position 1 and 2
		profileAPI.sortElements(1, 2);

		// Save the sort
		profileAPI.clickAction(ActionType.SAVE);

		// Check that the entries were actually swapped
		assertEquals(name2, profileAPI.getTextAtIndex(SECTION, 1));
		assertEquals(name1, profileAPI.getTextAtIndex(SECTION, 2));
	}

	@Test
	public void testUserCanShowAndHideOneItem() {
		// Add entry to table
		Affiliation affiliation = affiliationsObject.getAffiliations().get(0);
		affiliation.setName("Affiliation to Make (In)Visible");
		String name = addAffiliationAndAward(affiliation);

		// Hide the entry
		profileAPI.clickShowAndHideItems(SECTION);
		int index = profileAPI.getIndexOfEntryToShowOrHide(SECTION, name);
		profileAPI.clickCheckBoxToHideShowItem(index, false);
		profileAPI.clickAction(ActionType.SAVE);

		// Check that the entry is no longer in the table
		assertFalse(profileAPI.checkForEntryInTable(SECTION, name));

		// Show the entry
		profileAPI.clickShowAndHideItems(SECTION);
		index = profileAPI.getIndexOfEntryToShowOrHide(SECTION, name);
		profileAPI.clickCheckBoxToHideShowItem(index, true);
		profileAPI.clickAction(ActionType.SAVE);

		// Check that the entry is in the table
		assertTrue(profileAPI.checkForEntryInTable(SECTION, name));
	}

	@Test
	public void testUserCanShowAndHideAllItems() {
		// Add multiple entries if the table is empty
		if (!profileAPI.sectionHasEntries(SECTION)) {
			testUserCanAddMultipleAffiliations();
		}

		// Hide all entries
		profileAPI.clickShowAndHideItems(SECTION);
		profileAPI.clickSelectNone();
		profileAPI.clickAction(ActionType.SAVE);

		// Check that the table has no visible entries
		assertFalse(profileAPI.sectionHasEntries(SECTION));

		// Show all entries
		profileAPI.clickShowAndHideItems(SECTION);
		profileAPI.clickSelectAll();
		profileAPI.clickAction(ActionType.SAVE);

		// Check that the table now has visible entries
		assertTrue(profileAPI.sectionHasEntries(SECTION));
	}

	@Test
	public void testUserCanCancelAdd() {
		// Pretend to add an entry
		profileAPI.clickAddItem(SECTION);
		profileAPI.switchToIFrame();
		Date date = new Date();
		Affiliation affiliation = affiliationsObject.getAffiliations().get(0);
		String name = "Affiliation to Pretend Add - " + dateFormat.format(date);
		affiliation.setName(name);
		affiliationsAPI.enterDetails(affiliation);

		// Cancel the add
		profileAPI.clickAction(ActionType.CANCEL);

		// Check that the entry is not in the table
		assertFalse(profileAPI.checkForEntryInTable(SECTION, name));
	}

	@Test
	public void testUserCanCancelEdit() {
		// Add an entry that we will pretend to update
		Affiliation affiliationToUpdate = affiliationsObject.getAffiliations().get(0);
		affiliationToUpdate.setName("Affiliation to Pretend Update");
		String name = addAffiliationAndAward(affiliationToUpdate);

		// Get index of affiliationToEdit
		int index = profileAPI.getIndexOfEntryToEditOrDelete(SECTION, name);

		// Act like we will edit entry at that index, but then cancel
		profileAPI.clickEditEntry(SECTION, index);
		profileAPI.switchToIFrame();
		Date date = new Date();
		String name2 = "Affiliation with Edited Name - " + dateFormat.format(date);
		affiliationsAPI.updateName(name2);
		profileAPI.clickAction(ActionType.CANCEL);

		// Check that the updated entry is not in the table, but that the old entry is
		assertTrue(profileAPI.checkForEntryInTable(SECTION, name));
		assertFalse(profileAPI.checkForEntryInTable(SECTION, name2));
	}

	@Test
	public void testUserCanCancelDelete() {
		// Add an entry that we will pretend to delete
		Affiliation affiliationToDelete = affiliationsObject.getAffiliations().get(0);
		affiliationToDelete.setName("Affiliation to Pretend Delete");
		String name = addAffiliationAndAward(affiliationToDelete);

		// Get index of affiliationToDelete
		int index = profileAPI.getIndexOfEntryToEditOrDelete(SECTION, name);

		// Act like we will delete entry at that index, but then cancel
		profileAPI.clickDeleteEntry(SECTION, index);
		profileAPI.clickAction(ActionType.CANCEL);

		// Check that the affiliation is still in the table
		assertTrue(profileAPI.checkForEntryInTable(SECTION, name));
	}

	@Test
	public void testUserCanCancelSort() {
		// If there are no entries in the table, add some
		if (!profileAPI.sectionHasEntries(SECTION)) {
			testUserCanAddMultipleAffiliations();
		}

		// Click on the sort items icon
		profileAPI.clickSortItems(SECTION);

		// Get names of entries at positions 1 and 2
		String name1 = profileAPI.getTextAtIndexInShowHideTable(1);
		String name2 = profileAPI.getTextAtIndexInShowHideTable(2);

		// Act as if we are trying to swap entries at position 1 and 2
		profileAPI.sortElements(1, 2);

		// Cancel the sort
		profileAPI.clickAction(ActionType.CANCEL);

		// Check that the entries weren't swapped and stayed in the same positions
		assertEquals(name1, profileAPI.getTextAtIndex(SECTION, 1));
		assertEquals(name2, profileAPI.getTextAtIndex(SECTION, 2));
	}

	@Test
	public void testUserCanCancelShowAndHideItems() {
		// Add an entry if table is empty
		if (!profileAPI.sectionHasEntries(SECTION)) {
			addAffiliationAndAward(affiliationsObject.getAffiliations().get(0));
		}

		// Pretend to deselect all entries
		profileAPI.clickShowAndHideItems(SECTION);
		profileAPI.clickSelectNone();
		profileAPI.clickAction(ActionType.CANCEL);

		// Check that entries in table are still visible
		assertTrue("There should be at least one visible affiliation!", profileAPI.sectionHasEntries(SECTION));
	}

	/***********************************
	 * VALIDATION TESTS
	 ***********************************/
	@Test
	public void testUserDoesntFillOutRequiredFields() {
		profileAPI.clickAddItem(SECTION);
		profileAPI.switchToIFrame();
		Affiliation affiliation = AffiliationsUtils.getInvalidAffiliation();
		affiliationsAPI.enterDetails(affiliation);
		profileAPI.clickAction(ActionType.SAVE);
		profileAPI.switchToIFrame();
		assertTrue("Error should be thrown if user does not fill out all the required fields!",
				profileAPI.getErrorMessage().contains("Please enter a name, OR\n" + "Please enter an abbreviation.\n"
						+ "Please select a type.\n" + "Please enter your role.\n" + "Please enter a start date."));
		profileAPI.clickAction(ActionType.CANCEL);
	}

	@Test
	public void testUserInputsInvalidStartAndEndDates() {
		profileAPI.clickAddItem(SECTION);
		profileAPI.switchToIFrame();
		Affiliation affiliation = affiliationsObject.getAffiliations().get(0);
		affiliation.setStartDate("11/11/1111");
		affiliation.setEndDate("11/11/1111");
		affiliationsAPI.enterDetails(affiliation);
		profileAPI.clickAction(ActionType.SAVE);
		profileAPI.switchToIFrame();
		assertTrue("Error should be thrown if user inputs invalid formats for the start date!",
				profileAPI.getErrorMessage().contains(
						"Please provide a valid start date."));
		assertTrue("Error should be thrown if user inputs invalid formats for the end date!",
				profileAPI.getErrorMessage().contains(
						"Please provide a valid end date."));
		profileAPI.clickAction(ActionType.CANCEL);
	}
}