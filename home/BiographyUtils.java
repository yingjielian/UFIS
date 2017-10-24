package ufis.far.common.profile.home;

import ufis.far.beans.profile.home.Biography;

public class BiographyUtils {
	public static Biography getBiographyObject() {

		String biographyText = "This is a test biography.";

		Biography biography = new Biography(biographyText);

		return biography;
	}
}
