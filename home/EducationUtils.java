package ufis.far.common.profile.home;

import java.util.ArrayList;
import java.util.List;

import ufis.far.beans.profile.home.Education;
import ufis.far.beans.profile.home.Education.EducationDetails;

public class EducationUtils {

	List<EducationDetails> _education = new ArrayList<EducationDetails>();

	public Education getEducationObject() {

		String degreeType = "Bachelor's Degree";
		String yearReceived = "1993";
		String department = "Computer Science";
		String institution = "Harvard";
		String projectTitle = "";
		boolean publishToMyWebpage = true;

		EducationDetails education1 = new EducationDetails(degreeType, yearReceived, department, institution, projectTitle, publishToMyWebpage);
		_education.add(education1);
		
		String degreeType2 = "Master's Degree";
		String yearReceived2 = "1995";
		String department2 = "Computer Science";
		String institution2 = "MIT";
		String projectTitle2 = "";
		boolean publishToMyWebpage2 = true;
		
		EducationDetails education2 = new EducationDetails(degreeType2, yearReceived2, department2, institution2, projectTitle2, publishToMyWebpage2);
		_education.add(education2);

		Education education = new Education(_education);
		return education;
	}
}
