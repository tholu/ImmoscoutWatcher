/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.hans.gwt.immoscout.watcher.client;

import com.hans.gwt.immoscout.watcher.server.Immobilie;

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
    if (this.mainPanel == null) {
      this.mainPanel = new VerticalPanel();
      this.mainPanel.setStyleName("gwt-DisclosurePanel");
      this.mainPanel.setSize("900px", "600px");
      this.mainPanel.add(getScrollPanel());
      this.mainPanel.add(getStartButton());
      this.mainPanel.setCellHorizontalAlignment(getStartButton(), HasHorizontalAlignment.ALIGN_CENTER);
    }
    return this.mainPanel;
  }

  private FlexTable getFlexTable() {
    if (this.flexTable == null) {
      this.flexTable = new FlexTable();
      this.flexTable.setStyleName("gwt-RichTextToolbar .gwt-ToggleButton-up");
      this.flexTable.setSize("100%", "100%");
    }
    return this.flexTable;
  }

  private ScrollPanel getScrollPanel() {
    if (this.scrollPanel == null) {
      this.scrollPanel = new ScrollPanel();
      this.scrollPanel.setSize("100%", "600px");
      this.scrollPanel.setWidget(getFlexTable());
    }
    return this.scrollPanel;
  }

  private Button getStartButton() {
    if (this.startButton == null) {
      this.startButton = new Button("Start");
      this.startButton.addClickHandler(new ClickHandler(){
        @Override
        public void onClick(final ClickEvent event) {
          getAndDisplayResults();
        }
      });
      this.startButton.setStyleName("gwt-PushButton-up");
      this.startButton.setFocus(true);
    }
    return this.startButton;
  }

  private void getAndDisplayResults() {
    StockPriceServiceAsync stockPriceSvc;
    // Initialize the service proxy.
    if (stockPriceSvc == null) {
      stockPriceSvc = GWT.create(StockPriceService.class);
    }

    // Set up the callback object.
    final AsyncCallback<Immobilie[][]> callback = new AsyncCallback<Immobilie[]>(){
      public void onFailure(Throwable caught) {
        // TODO: Do something with errors.
        System.out.println("Error in on Failure ...");
      }

      public void onSuccess(Immobilie[] result) {
        // write results
        int i = 1;
        for (final Immobilie immo : result) {
          getFlexTable().setText(i, 0, i);
          getFlexTable().setText(i, 1, immo.titleString);
          getFlexTable().setText(i, 1, immo.objectIDString);
          getFlexTable().setText(i, 1, immo.kaufpreisString);
          getFlexTable().setText(i, 1, immo.wohnflaecheString);
          getFlexTable().setText(i, 1, immo.zimmerString);
          i++;
        }
      }
    };

    // Make the call to the stock price service.
    final String[] someParams = new String[1];
    someParams[0] = "4711 Params";
    stockPriceSvc.getPrices(someParams.toArray(new String[0]), callback);
  }
}
