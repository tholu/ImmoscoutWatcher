package com.hans.gwt.immoscout.watcher.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hans.gwt.immoscout.watcher.client.Immobilie;
import com.hans.gwt.immoscout.watcher.client.WatcherService;

public class WatcherServiceImpl extends RemoteServiceServlet implements
WatcherService {

	private List<Immobilie> dummyResultsList;
	private final Set<String> ipAdresses = new HashSet<String>();

	@Override
	public Immobilie[] getImmos(final String[] someParams) {
		return getDummyData();
	}

	public Immobilie[] getDummyData() {
		if (dummyResultsList == null) {
			dummyResultsList = new ArrayList<Immobilie>();
		}

		final String ip = getThreadLocalRequest().getRemoteAddr();
		if (!ipAdresses.contains(ip)) {
			ipAdresses.add(ip);

			final int max = dummyResultsList.size() + 10;
			for (int i = dummyResultsList.size(); i < max; i++) {
				dummyResultsList.add(new Immobilie(ip + " " + i, new Double(
						Math.random() * 100000).intValue() + " €", new Double(
						Math.random() * 10).intValue() + "", new Double(Math
										.random() * 10).intValue() + "Zi.", new Double(Math
												.random() * 1000000000).intValue() + "" + ""));
			}
			// sort array?
		}

		return dummyResultsList.toArray(new Immobilie[dummyResultsList.size()]);
	}

	private Immobilie[] getRealData() {
		// parse
		final String urlString = "http://www.immobilienscout24.de/Suche/S-T/Wohnung-Kauf/Baden-Wuerttemberg/Stuttgart?enteredFrom=one_step_search";

		final List<Immobilie> resultsList = ImmoscoutParser
				.getImmoData(urlString);

		// echo
		System.out.println("Found " + resultsList.size()
				+ " results. Echoing results on console:");
		int i = 0;
		for (final Immobilie immobilie : resultsList) {
			System.out.println(i++ + " " + immobilie);
		}

		if (resultsList.isEmpty()) {
			resultsList.add(new Immobilie("Error", "Kaufpreis", "Wohnflaeche",
					"Anzahl Zimmer", "ObjID"));
		}
		final Immobilie[] resultsArray = resultsList
				.toArray(new Immobilie[resultsList.size()]);

		return resultsArray;
	}
}