package com.hans.gwt.immoscout.watcher.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryService {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManagerFactoryService() {
	}

	public static EntityManagerFactory getInstance() {
		return EntityManagerFactoryService.emfInstance;
	}
}