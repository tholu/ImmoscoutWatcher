package com.hans.gwt.immoscout.watcher.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("myServices")
public interface WatcherService extends RemoteService {

	Immobilie[] getImmos(String[] someParams);

}