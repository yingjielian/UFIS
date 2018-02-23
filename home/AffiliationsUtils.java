package ufis.far.common.profile.service;

import java.util.ArrayList;
import java.util.List;

import ufis.far.beans.profile.service.Affiliations;
import ufis.far.beans.profile.service.Affiliations.Affiliation;

public class AffiliationsUtils {

	static List<Affiliation> _affiliations = new ArrayList<Affiliation>();

	static public Affiliations getAffiliationsObject() {
		_affiliations.clear();

		String name = "Coca Cola Ambassadors";
		String abbreviation = "CCA";
		boolean isOrganization = true;
		String role = "Consultant";
		String startDate = "06/2017";
		String endDate = "";
		boolean publishToMyWebpage = true;

		Affiliation affiliation = new Affiliation(name, abbreviation, isOrganization, role, startDate, endDate,
				publishToMyWebpage);
		_affiliations.add(affiliation);

		String name2 = "Games for Kids";
		String abbreviation2 = "GfK LLC.";
		boolean isOrganization2 = false;
		String role2 = "Chairman";
		String startDate2 = "04/20/2012";
		String endDate2 = "05/05/2014";
		boolean publishToMyWebpage2 = true;

		Affiliation affiliation2 = new Affiliation(name2, abbreviation2, isOrganization2, role2, startDate2, endDate2,
				publishToMyWebpage2);
		_affiliations.add(affiliation2);

		String name3 = "Cub Scouts";
		String abbreviation3 = "N/A";
		boolean isOrganization3 = true;
		String role3 = "Volunteer";
		String startDate3 = "09/09/2009";
		String endDate3 = "";
		boolean publishToMyWebpage3 = true;

		Affiliation affiliation3 = new Affiliation(name3, abbreviation3, isOrganization3, role3, startDate3, endDate3,
				publishToMyWebpage3);
		_affiliations.add(affiliation3);

		Affiliations affiliations = new Affiliations(_affiliations);
		return affiliations;
	}

	public static Affiliation getInvalidAffiliation() {
		Affiliation affiliation = _affiliations.get(0);
		affiliation.setName("");
		affiliation.setAbbreviation("");
		affiliation.setOrganization(null);
		affiliation.setRole("");
		affiliation.setStartDate("");
		return affiliation;
	}
}
