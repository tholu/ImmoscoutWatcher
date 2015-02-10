package com.hans.gwt.immoscout.watcher.server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImmoscoutParser {

	public static ArrayList<Immobilie> getImmoData(final String urlString) {
		// setup ..
		final ArrayList<Immobilie> resultsList = new ArrayList<Immobilie>();
		ArrayList<Immobilie> partialResultList = null;

		String processedUrlString = null;
		String nextUrlString = urlString;

		// iterate ...
		if (nextUrlString != null) {
			while (!nextUrlString.trim().equals("")) {
				System.out.println(nextUrlString);

				partialResultList = ImmoscoutParser
						.getImmoDataFromPage(nextUrlString);

				for (final Immobilie immobilie : partialResultList) {
					resultsList.add(immobilie);
				}

				processedUrlString = nextUrlString;
				nextUrlString = ImmoscoutParser
						.getLinkForNextPage(processedUrlString);
			}
		}

		return resultsList;
	}

	private static ArrayList<Immobilie> getImmoDataFromPage(
			final String urlString) {

		final ArrayList<Immobilie> immobilienSet = new ArrayList<Immobilie>();

		try {
			final Document jsoupHtmlDoc = Jsoup.connect(urlString).get();

			// select ALL immo-data
			final Elements elements_immoData = jsoupHtmlDoc
					.select("li[data-item=result]");

			// from each item ...titles & ids
			final Elements elements_a_withTitleAttr = elements_immoData
					.select("a[title]");
			for (final Element element : elements_a_withTitleAttr) {
				String titleString = null;
				String objectIdString = null;

				final String selString = element.attr("title");
				if (!selString.isEmpty()) {
					titleString = selString;
				}

				final String sel2String = element.attr("href");
				if (sel2String.contains("/expose/")) {
					String idString = sel2String.split("/")[2];
					if (idString.contains("#")) {
						idString = idString.split("#")[0];
					}
					objectIdString = idString;
				}

				final Immobilie immo = new Immobilie();
				immo.titleString = titleString;
				immo.objectIDString = objectIdString;
				immobilienSet.add(immo);
			}

			// from each item ...infos
			final Elements elements_div = elements_immoData
					.select("div[class=resultlist_criteria hideable resultlist_gt_2_criteria]");
			final ListIterator<Immobilie> immoIterator = immobilienSet
					.listIterator(0);
			for (final Element element : elements_div) {
				// query für *alle* dd-Tags:
				// element.getElementsByTag("dd").text();
				// wir wollen die Tags aber sauber einzeln reinbekommen...
				String kaufpreisString = null;
				String wohnflaecheString = null;
				String zimmerString = null;

				// select every <dl> element whose class-attribute value begins
				// with "criteria"
				final Elements dls = elements_div.select("dl[class^=criteria]");
				for (final Element dl : dls) {

					if (dl.getElementsByTag("dt").text().equals("Kaufpreis")) {
						// äquivalente query: = dl.select("dd").get(0).text();
						kaufpreisString = dl.getElementsByTag("dd").get(0)
								.text();
					}
					if (dl.getElementsByTag("dt").text().equals("Wohnfläche")) {
						wohnflaecheString = dl.getElementsByTag("dd").get(0)
								.text();
					}
					if (dl.getElementsByTag("dt").text().equals("Zimmer")) {
						zimmerString = dl.getElementsByTag("dd").get(0).text();
					}

					if (kaufpreisString != null && wohnflaecheString != null
							&& zimmerString != null && immoIterator.hasNext()) {

						final Immobilie immo = immoIterator.next();
						immo.kaufpreisString = kaufpreisString;
						immo.wohnflaecheString = wohnflaecheString;
						immo.zimmerString = zimmerString;
						immoIterator.set(immo);

						// reset Strings
						kaufpreisString = null;
						wohnflaecheString = null;
						zimmerString = null;
					}
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return immobilienSet;
	}

	private static String getLinkForNextPage(final String urlString) {
		// <div id="pager_next"
		// class="padding-horizontal-s border-right border-darkblue">
		//
		// <a
		// href="/Suche/S-T/P-2/Wohnung-Kauf/Baden-Wuerttemberg/Stuttgart?pagerReporting=true"
		// data-is24-qa="paging_bottom_next">
		// <span class="nextPageText">nächste Seite</span> <i
		// class="fa fa-caret-right"></i>
		// </a>
		//
		// </div>

		Document jsoupHtmlDoc;
		String nextUrlString = "";
		try {
			jsoupHtmlDoc = Jsoup.connect(urlString).get();

			final Elements elements_pagerNext = jsoupHtmlDoc
					.select("div[id=pager_next]");

			if (!elements_pagerNext.isEmpty()) {
				if (elements_pagerNext.select("span[class=nextPageText]")
						.text().equals("nächste Seite")) {
					final String[] splitArray = urlString.split("/");
					final String domainString = splitArray[0] + "//"
							+ splitArray[2];

					final String nextString = elements_pagerNext.select(
							"a[href]").attr("href");
					nextUrlString = domainString + nextString;
				} else {
					nextUrlString = "";
				}
			} else {
				nextUrlString = "";
			}

		} catch (final IOException e) {
			e.printStackTrace();
		}
		return nextUrlString;

	}
}
