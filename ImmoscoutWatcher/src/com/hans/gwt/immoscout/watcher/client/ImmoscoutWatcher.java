package com.hans.gwt.immoscout.watcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
		WatcherServiceAsync watcherService = null;
		// Initialize the service proxy.
		if (watcherService == null) {
			watcherService = GWT.create(WatcherService.class);
			// ((ServiceDefTarget) watcherService)
			// .setServiceEntryPoint("http://127.0.0.1:8888/ImmoscoutWatcher.html?gwt.codesvr=127.0.0.1:9997/myServices");
		}

		// Set up the callback object.
		final AsyncCallback<Immobilie[]> callback = new AsyncCallback<Immobilie[]>() {
			@Override
			public void onFailure(final Throwable caught) {
				// TODO: Do something with errors.
				System.out.println("Error in on Failure ...");
				System.out.println(caught);
				System.out.println(caught.getMessage());
				caught.printStackTrace();
				
				PopupPanel popupPanel = new PopupPanel(true);
				popupPanel.setWidget(new Label("Error: " + caught + caught.getMessage()));
				popupPanel.show();
			}

			@Override
			public void onSuccess(final Immobilie[] result) {
				// write results
				int i = 1;
				for (final Immobilie immo : result) {
					final StringBuilder titleStringBuilder = new StringBuilder(
							immo.titleString);
					titleStringBuilder.setLength(20);

					getFlexTable().setText(i, 0, i + "");
					getFlexTable().setText(i, 1, titleStringBuilder.toString());
					getFlexTable().setText(i, 2, immo.objectIDString);
					getFlexTable().setText(i, 3, immo.kaufpreisString);
					getFlexTable().setText(i, 4, immo.wohnflaecheString);
					getFlexTable().setText(i, 5, immo.zimmerString);
					i++;
				}
			}
		};

		// Make the call to the stock price service.
		final String[] someParams = new String[1];
		someParams[0] = "4711 Params";
		watcherService.getImmos(someParams, callback);
	}
}
