package org.singsurf.simplewebworker.beanclient;

import java.util.Arrays;
import java.util.List;

import org.singsurf.simplewebworker.common.BeanTools;
import org.singsurf.simplewebworker.common.DataBean;
import org.singsurf.simplewebworker.common.ResultBean;

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

public class BeanClient implements EntryPoint {

	@Override
	public void onModuleLoad() {

		final TextBox nameField = new TextBox();
		nameField.setText("Test input");
		final Label errorLabel = new Label();
		errorLabel.setText("");
		final TextBox data1Field = new TextBox();
		final TextBox data2Field = new TextBox();
		final TextBox data3Field = new TextBox();
		final TextBox data4Field = new TextBox();
		final TextBox meanField = new TextBox();
		final TextBox sumField = new TextBox(); 
		data1Field.setText("1");
		data2Field.setText("2");
		data3Field.setText("3");
		data4Field.setText("4");
		
		final BeanTools beanTools = new BeanTools();
		
		final Worker worker = Worker.create("beanworker/beanworker.nocache.js");
		
		final MessageHandler webWorkerMessageHandler = new MessageHandler() {
			@Override
			public final void onMessage(final MessageEvent pEvent) {
				ResultBean rb = beanTools.deserializeResultBean(pEvent.getDataAsString());
				sumField.setText(Double.toString(rb.getSum()));
				meanField.setText(Double.toString(rb.getMean()));
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


		final Button sendButton = new Button("Send", new ClickHandler() {
			public void onClick(ClickEvent event) {
				double x1 = Double.parseDouble(data1Field.getText());
				double x2 = Double.parseDouble(data2Field.getText());
				double x3 = Double.parseDouble(data3Field.getText());
				double x4 = Double.parseDouble(data4Field.getText());
				String name = nameField.getText();
				DataBean db = beanTools.makeDataBean();
				db.setName(name);
				List<Double> list = Arrays.asList(x1,x2,x3,x4);
				db.setData(list);
				String json = beanTools.serializeDataBean(db);
				worker.postMessage(json);
			}
		});

		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("data1Container").add(data1Field);
		RootPanel.get("data2Container").add(data2Field);
		RootPanel.get("data3Container").add(data3Field);
		RootPanel.get("data4Container").add(data4Field);
		RootPanel.get("sumContainer").add(sumField);
		RootPanel.get("meanContainer").add(meanField);
	}
}
