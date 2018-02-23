package ufis.far.beans.profile.service;

import java.util.List;

public class Affiliations {

	List<Affiliation> affiliations;

	public Affiliations(List<Affiliation> affiliations) {
		this.affiliations = affiliations;
	}

	public List<Affiliation> getAffiliations() {
		return affiliations;
	}

	public void setAffiliations(List<Affiliation> affiliations) {
		this.affiliations = affiliations;
	}

	public void addAffiliation(Affiliation affiliation) {
		affiliations.add(affiliation);
	}

	public static class Affiliation {
		String name;
		String abbreviation;
		Boolean isOrganization;
		String role;
		String startDate;
		String endDate;
		boolean publishToMyWebpage;
		
		public Affiliation(String name, String abbreviation, Boolean isOrganization, String role, String startDate,
				String endDate, boolean publishToMyWebpage) {
			this.name = name;
			this.abbreviation = abbreviation;
			this.isOrganization = isOrganization;
			this.role = role;
			this.startDate = startDate;
			this.endDate = endDate;
			this.publishToMyWebpage = publishToMyWebpage;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAbbreviation() {
			return abbreviation;
		}

		public void setAbbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
		}

		public Boolean isOrganization() {
			return isOrganization;
		}

		public void setOrganization(Boolean isOrganization) {
			this.isOrganization = isOrganization;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public boolean isPublishToMyWebpage() {
			return publishToMyWebpage;
		}

		public void setPublishToMyWebpage(boolean publishToMyWebpage) {
			this.publishToMyWebpage = publishToMyWebpage;
		}
	}
}
