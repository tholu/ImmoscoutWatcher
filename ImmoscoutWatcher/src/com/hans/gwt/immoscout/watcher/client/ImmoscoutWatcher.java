/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.hans.gwt.immoscout.watcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		getFlexTable().setText(0, 0, "Hello");
		getFlexTable().setText(0, 1, "World!");
		getFlexTable().setText(0, 2, "<3");

		for (int row = 1; row < 99; row++) {
			for (int column = 0; column < 10; column++) {
				getFlexTable().setText(row, column, row + " , " + column);
			}
		}
	}
}
