package com.hans.gwt.immoscout.watcher.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.hans.gwt.immoscout.watcher.client.Immobilie;

public enum DataAccessObjectImmo {
	INSTANCE;

	public List<Immobilie> getAllImmobilien() {
		final EntityManager em = EntityManagerFactoryService.getInstance()
				.createEntityManager();
		// read the existing entries
		final Query q = em.createQuery("select immo from Immobilie immo");
		final List<Immobilie> immos = q.getResultList();
		return immos;
	}

	public void add(final String titleString, final String kaufpreisString,
			final String wohnflaecheString, final String zimmerString,
			final String objectIDString) {
		synchronized (this) {
			// final EntityManager em = EntityManagerFactoryService
			// .getInstance().createEntityManager();

			final EntityManagerFactory emfInstance = Persistence
					.createEntityManagerFactory("transactions-optional");
			final EntityManager em = emfInstance.createEntityManager();

			final Immobilie immo = new Immobilie(titleString, kaufpreisString,
					wohnflaecheString, zimmerString, objectIDString);
			em.persist(immo);
			em.close();
		}
	}

	public List<Immobilie> getImmobilienBilligerAls(final String kaufpreisString) {
		final EntityManager em = EntityManagerFactoryService.getInstance()
				.createEntityManager();
		final Query q = em
				.createQuery("select immo from Immobilie immo where immo.objectIDString <= :kaufpreisString");
		q.setParameter("kaufpreisString", kaufpreisString);
		final List<Immobilie> immos = q.getResultList();
		return immos;
	}

	public void remove(final String objectIDString) {
		final EntityManager em = EntityManagerFactoryService.getInstance()
				.createEntityManager();
		try {
			final Immobilie immo = em.find(Immobilie.class, objectIDString);
			em.remove(immo);
		} finally {
			em.close();
		}
	}
}
