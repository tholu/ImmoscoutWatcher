package com.hans.gwt.immoscout.watcher.client;

import com.hans.gwt.immoscout.watcher.server.Immobilie;

@RemoteServiceRelativePath("myServices")
public interface WatcherService extends RemoteService {

  Immobilie[] getImmos(String[] someParams);

}