package com.hans.gwt.immoscout.watcher.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ImmoscoutWatcher implements EntryPoint {
	private VerticalPanel mainPanel;

	private FlexTable flexTable;

	private ScrollPanel scrollPanel;

	private Button startButton;

	@Override
	public void onModuleLoad() {
		initUI();
		getAndDisplayResults();
	}

	private void initUI() {
		final RootPanel rootPanel = RootPanel.get();
		rootPanel.add(getMainPanel(), 0, 0);
	}

	private VerticalPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new VerticalPanel();
			mainPanel.setStyleName("gwt-DisclosurePanel");
			mainPanel.setSize("900px", "600px");
			mainPanel.add(getScrollPanel());
			mainPanel.add(getStartButton());
			mainPanel.setCellHorizontalAlignment(getStartButton(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return mainPanel;
	}

	private FlexTable getFlexTable() {
		if (flexTable == null) {
			flexTable = new FlexTable();
			flexTable.setStyleName("gwt-RichTextToolbar .gwt-ToggleButton-up");
			flexTable.setSize("100%", "100%");
		}
		return flexTable;
	}

	private ScrollPanel getScrollPanel() {
		if (scrollPanel == null) {
			scrollPanel = new ScrollPanel();
			scrollPanel.setSize("100%", "600px");
			scrollPanel.setWidget(getFlexTable());
		}
		return scrollPanel;
	}

	private Button getStartButton() {
		if (startButton == null) {
			startButton = new Button("Start");
			startButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					getAndDisplayResults();
				}
			});
			startButton.setStyleName("gwt-PushButton-up");
			startButton.setFocus(true);
		}
		return startButton;
	}

	private void getAndDisplayResults() {
		getFlexTable().setText(0, 0, "Talking to server ...");
		WatcherServiceAsync watcherService = null;
		// Initialize the service proxy.
		if (watcherService == null) {
			watcherService = GWT.create(WatcherService.class);
			// ((ServiceDefTarget) watcherService)
			// .setServiceEntryPoint("http://127.0.0.1:8888/ImmoscoutWatcher.html?gwt.codesvr=127.0.0.1:9997/myServices");
		}

		// Set up the callback object.
		final AsyncCallback<List<Immobilie>> callback = new AsyncCallback<List<Immobilie>>() {
			@Override
			public void onFailure(final Throwable caught) {
				// TODO: Do something with errors.
				System.out
						.println("Request returned with an Error, executing onFailure() method.");
				System.out.println(caught);
				System.out.println(caught.getMessage());
				caught.printStackTrace();

				final PopupPanel popupPanel = new PopupPanel(true);
				popupPanel.setWidget(new Label(caught.getMessage()));
				popupPanel.show();
			}

			@Override
			public void onSuccess(final List<Immobilie> result) {
				// write results
				int i = 1;
				for (final Immobilie immo : result) {
					getFlexTable().setText(i, 0, i + "");
					getFlexTable().setText(i, 1,
							extractTitleWithMaxLength(immo, 30));
					getFlexTable().setText(i, 2, immo.objectIDString);
					getFlexTable().setText(i, 3, immo.kaufpreisString);
					getFlexTable().setText(i, 4, immo.wohnflaecheString);
					getFlexTable().setText(i, 5, immo.zimmerString);
					i++;
				}
			}

			private String extractTitleWithMaxLength(final Immobilie immo,
					final int maxLength) {
				StringBuilder titleStringBuilder;
				if (immo.titleString != null) {
					titleStringBuilder = new StringBuilder(immo.titleString);
				} else {
					titleStringBuilder = new StringBuilder("default Value");
				}
				titleStringBuilder.setLength(30);
				return titleStringBuilder.toString();
			}
		};

		// Make the call to the stock price service.
		final String[] someParams = new String[1];
		// http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/Window.Navigator.html
		someParams[0] = Navigator.getUserAgent();
		getFlexTable().setText(1, 0, Navigator.getUserAgent());
		watcherService.getImmos(someParams, callback);
	}
}
