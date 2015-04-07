package com.hans.gwt.immoscout.watcher.client;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
public class Immobilie implements IsSerializable {

	public String titleString;

	public String kaufpreisString;

	public String wohnflaecheString;

	public String zimmerString;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	public String objectIDString;

	public Immobilie() {
	}

	public Immobilie(final String titleString, final String kaufpreisString,
			final String wohnflaecheString, final String zimmerString,
			final String objectIDString) {
		this.titleString = titleString;
		this.kaufpreisString = kaufpreisString;
		this.wohnflaecheString = wohnflaecheString;
		this.zimmerString = zimmerString;
		this.objectIDString = objectIDString;
	}

	@Override
	public String toString() {
		return "Immobilie ["
				+ (titleString != null ? "titleString=" + titleString + ", "
						: "")
						+ (kaufpreisString != null ? "kaufpreisString="
								+ kaufpreisString + ", " : "")
								+ (wohnflaecheString != null ? "wohnflaecheString="
										+ wohnflaecheString + ", " : "")
										+ (zimmerString != null ? "zimmerString=" + zimmerString + ", "
												: "")
												+ (objectIDString != null ? "objectIDString=" + objectIDString
														: "") + "]";
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(final String titleString) {
		this.titleString = titleString;
	}

	public String getKaufpreisString() {
		return kaufpreisString;
	}

	public void setKaufpreisString(final String kaufpreisString) {
		this.kaufpreisString = kaufpreisString;
	}

	public String getWohnflaecheString() {
		return wohnflaecheString;
	}

	public void setWohnflaecheString(final String wohnflaecheString) {
		this.wohnflaecheString = wohnflaecheString;
	}

	public String getZimmerString() {
		return zimmerString;
	}

	public void setZimmerString(final String zimmerString) {
		this.zimmerString = zimmerString;
	}

	public String getObjectIDString() {
		return objectIDString;
	}

	public void setObjectIDString(final String objectIDString) {
		this.objectIDString = objectIDString;
	}

}
