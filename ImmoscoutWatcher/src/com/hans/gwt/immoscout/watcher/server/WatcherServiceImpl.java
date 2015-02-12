package com.hans.gwt.immoscout.watcher.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hans.gwt.immoscout.watcher.client.Immobilie;
import com.hans.gwt.immoscout.watcher.client.WatcherService;

public class WatcherServiceImpl extends RemoteServiceServlet implements
		WatcherService {

	@Override
	public Immobilie[] getImmos(final String[] someParams) {

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

		final Immobilie[] resultsArray = resultsList
				.toArray(new Immobilie[resultsList.size()]);

		// final Immobilie[] immos = new Immobilie[1];
		// final Immobilie immo = new Immobilie();
		// immo.kaufpreisString = "4711";
		// immo.objectIDString = "4711+1";
		// immo.titleString = "4711+2";
		// immo.wohnflaecheString = "4711+3";
		// immo.zimmerString = "4711+4";
		// immos[0] = immo;
		//
		// return immos;

		return resultsArray;
	}

}