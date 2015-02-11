package com.hans.gwt.immoscout.watcher.server;

import java.util.ArrayList;

import com.hans.gwt.immoscout.watcher.client.WatcherService;

public class WatcherServiceImpl extends RemoteServiceServlet implements WatcherService {

  @Override
  public Immobilie[] getImmos(String[] someParams) {

    // parse
    final String urlString = "http://www.immobilienscout24.de/Suche/S-T/Wohnung-Kauf/Baden-Wuerttemberg/Stuttgart?enteredFrom=one_step_search";

    final ArrayList<Immobilie> resultsList = ImmoscoutParser.getImmoData(urlString);

    // echo
    int i = 0;
    for (final Immobilie immobilie : resultsList) {
      System.out.println(i++ + " Title = " + immobilie.titleString + " Kaufpreis = " + immobilie.kaufpreisString
                         + " Wohnfläche = " + immobilie.wohnflaecheString + " Zimmer = " + immobilie.zimmerString
                         + "ObjectID = " + immobilie.objectIDString);
    }

    final Immobilie[] immosR = (Immobilie[])resultsList.toArray();

    final Immobilie[] immos = new Immobilie[1];
    final Immobilie immo = new Immobilie();
    immo.kaufpreisString = "4711";
    immo.objectIDString = "4711+1";
    immo.titleString = "4711+2";
    immo.wohnflaecheString = "4711+3";
    immo.zimmerString = "4711+4";
    immos[0] = immo;

    return immos;
  }

}

/*
 * Include the server-side code in the GWT module
 * The embedded servlet container (Jetty) can host the servlets that contain your service implementation. This means you
 * can take advantage of running your application in development mode while testing and debugging the server-side Java
 * code.
 * To set this up, add <servlet> and <servlet-mapping> elements to the web application deployment descriptor (web.xml)
 * and point to the implementation class (StockPriceServiceImpl).
 * Starting with GWT 1.6, servlets should be defined in the web application deployment descriptor (web.xml) instead of
 * the GWT module (StockWatcher.gwt.xml).
 * In the <servlet-mapping> element, the url-pattern can be in the form of an absolute directory path (for example,
 * /spellcheck or /common/login). If you specify a default service path with a @RemoteServiceRelativePath annotation on
 * the service interface (as you did with StockPriceService), then make sure the url-pattern matches the annotation
 * value.
 * Because you’ve mapped the StockPriceService to “stockPrices” and the module rename-to attribute in
 * StockWatcher.gwt.xml is “stockwatcher”, the full URL will be:
 * http://localhost:8888/stockwatcher/stockPrices
 * Edit the web application deployment descriptor (StockWatcher/war/WEB-INF/web.xml).
 * Since the greetServlet is no longer needed, its definition can be removed.
 * <?xml version="1.0" encoding="UTF-8"?>
 * <!DOCTYPE web-app
 * PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 * "http://java.sun.com/dtd/web-app_2_3.dtd">
 * <web-app>
 * <!-- Default page to serve -->
 * <welcome-file-list>
 * <welcome-file>StockWatcher.html</welcome-file>
 * </welcome-file-list>
 * <!-- Servlets -->
 * <servlet>
 * <servlet-name>stockPriceServiceImpl</servlet-name>
 * <servlet-class>com.google.gwt.sample.stockwatcher.server.StockPriceServiceImpl</servlet-class>
 * </servlet>
 * <servlet-mapping>
 * <servlet-name>stockPriceServiceImpl</servlet-name>
 * <url-pattern>/stockwatcher/stockPrices</url-pattern>
 * </servlet-mapping>
 * </web-app>
 */
