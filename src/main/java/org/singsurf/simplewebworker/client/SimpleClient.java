package org.singsurf.simplewebworker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.webworker.client.ErrorEvent;
import com.google.gwt.webworker.client.ErrorHandler;
import com.google.gwt.webworker.client.MessageEvent;
import com.google.gwt.webworker.client.MessageHandler;
import com.google.gwt.webworker.client.Worker;

public class SimpleClient implements EntryPoint {

	@Override
	public void onModuleLoad() {

		final TextBox nameField = new TextBox();
		nameField.setText("Test input");
		final Label errorLabel = new Label();
		errorLabel.setText("");

		final Worker worker = Worker.create("worker/worker.nocache.js");
		
		final MessageHandler webWorkerMessageHandler = new MessageHandler() {
			@Override
			public final void onMessage(final MessageEvent pEvent) {
				nameField.setText(pEvent.getDataAsString());
			}
		};

		final ErrorHandler webWorkerErrorHandler = new ErrorHandler() {
			@Override
			public void onError(final ErrorEvent pEvent) {
				errorLabel.setText(pEvent.getMessage());
			}
		};

		worker.setOnMessage(webWorkerMessageHandler);
		worker.setOnError(webWorkerErrorHandler);


		final Button sendButton = new Button("Reverse!", new ClickHandler() {
			public void onClick(ClickEvent event) {
				worker.postMessage(nameField.getText());
			}
		});

		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
	}
}
