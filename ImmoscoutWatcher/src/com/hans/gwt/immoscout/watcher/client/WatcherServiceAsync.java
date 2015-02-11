package com.hans.gwt.immoscout.watcher.client;

import com.hans.gwt.immoscout.watcher.server.Immobilie;

public interface WatcherServiceAsync {
  void getImmos(String[] someParams, AsyncCallback<Immobilie[]> callback);
}
