package com.hans.gwt.immoscout.watcher.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WatcherServiceAsync {

	void getImmos(String[] someParams, AsyncCallback<List<Immobilie>> callback);

}