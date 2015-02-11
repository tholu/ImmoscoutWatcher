package com.hans.gwt.immoscout.watcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WatcherServiceAsync {

	void getImmos(String[] someParams, AsyncCallback<Immobilie[]> callback);

}