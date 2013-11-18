package edu.upenn.cis.cis550.hw5.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.upenn.cis.cis550.hw5.shared.Relation;

/**
 * This is the main class for generating Web table results for CIS 550 HW5
 * 
 * @author zives
 *
 */
public class WebTables implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final QueryTablesAsync queryService = GWT
			.create(QueryTables.class);

	/**
	 * This is the entry point method.
	 */

	@Override
	public void onModuleLoad() {
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("CIS 550 HW5 - Query Results");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML schemaLabel = new HTML();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<br><b>Relation schema:</b>"));
		dialogVPanel.add(schemaLabel);
		dialogVPanel.add(new HTML("<br><b>Returned data:</b>"));
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogBox.setWidget(dialogVPanel);
		
		final ScrollPanel scroller = new ScrollPanel();
		scroller.setHeight("400px");
		dialogVPanel.add(scroller);
		
		dialogVPanel.add(closeButton);

		// Here we are creating a dialog box to return the query results
		queryService.getRelation(new AsyncCallback<Relation>() {

			@Override
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				dialogBox
						.setText("Remote Procedure Call - Failure");
				serverResponseLabel
						.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(SERVER_ERROR);
				dialogBox.show();
				dialogBox.center();
				
				closeButton.setFocus(true);
			}

			@Override
			public void onSuccess(Relation result) {
				// Add the relational schema to the output
				schemaLabel.setText(result.getSchema().toString());
				
				// Add the relation outputs to the output in a scrollable table
				scroller.add(result.getTableWidget());
				dialogBox.show();
				dialogBox.center();
				
			}
			
		});
		
		closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
			
		});
	}
}
