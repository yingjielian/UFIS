package ufis.far.common.profile.home;

import ufis.far.beans.profile.home.ResearchSummary;

public class ResearchSummaryUtils {
	public static ResearchSummary getBiographyObject() {
		String summary = "This is a test researchSummary.";

		ResearchSummary researchSummary = new ResearchSummary(summary);

		return researchSummary;
	}
}
