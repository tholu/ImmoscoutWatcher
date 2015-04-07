package com.hans.gwt.immoscout.watcher.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Immobilie implements IsSerializable {

	public String titleString;

	public String kaufpreisString;

	public String wohnflaecheString;

	public String zimmerString;

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
}
