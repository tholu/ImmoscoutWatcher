package com.hans.gwt.immoscout.watcher.server;
import java.util.ArrayList;

public class GWTScoutMain {

	public static void main(final String[] args) {
		// parse
		final String urlString = "http://www.immobilienscout24.de/Suche/S-T/Wohnung-Kauf/Baden-Wuerttemberg/Stuttgart?enteredFrom=one_step_search";

		final ArrayList<Immobilie> resultsList = ImmoscoutParser
				.getImmoData(urlString);

		// echo
		int i = 0;
		for (final Immobilie immobilie : resultsList) {
			System.out.println(i++ + " Title = " + immobilie.titleString
					+ " Kaufpreis = " + immobilie.kaufpreisString
					+ " Wohnfläche = " + immobilie.wohnflaecheString
					+ " Zimmer = " + immobilie.zimmerString + "ObjectID = "
					+ immobilie.objectIDString);
		}
	}
}
