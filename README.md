# SimpleGWTWebWorker
A simple example of a WebWorker coded using the Google Web Toolkit

It uses the [gwt-webworker](https://gitlab.com/ManfredTremmel/gwt-webworker/) library, from 
ManfredTremmel which in turn has been taken from [Google speedtracer](https://code.google.com/archive/p/speedtracer/).

The Worker (org.singsurf.simplewebworker.worker.SimpleWorker) is simply:

```
public class SimpleWorker extends DedicatedWorkerEntryPoint 
        implements MessageHandler {

   @Override
   public void onWorkerLoad() {
       setOnMessage(this);
   }

    @Override
    public void onMessage(MessageEvent event) {
	    String str = event.getDataAsString();
	    StringBuilder sb = new StringBuilder();
	    for(int i=str.length()-1;i>=0;--i) {
	        sb.append(str.charAt(i));
	    }
	    String res = sb.toString();		
	    postMessage(res);
    }
}
```

and the client (org.singsurf.simplewebworker.client.SimpleClient) is:

```
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
```

The worker code needs to be compiles as a separate GWT module using the "dedicatedworker" linker which adds some necessary javascript to the code. The client code can be compiled in
as a normal module which also inherits the com.google.gwt.webworker.WebWorker module. 

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.8.1//EN"
  "http://gwtproject.org/doctype/2.8.1/gwt-module.dtd">

<module rename-to='worker'>
	<inherits name='com.google.gwt.core.Core'/>
	<inherits name="com.google.gwt.http.HTTP" />  
	<inherits name='com.google.gwt.webworker.WebWorker' />

	<set-property name="user.agent" value="safari" />
	<set-configuration-property name="user.agent.runtimeWarning" value="false" />

	<!-- Use the WebWorker linker for a Dedicated worker -->
	<add-linker name="dedicatedworker" />

	<!-- Specify the app entry point classes.                         -->
  	<entry-point class='org.singsurf.simplewebworker.worker.SimpleWorker'/>
    <source path='worker' />
</module>
```

A sample webpage can be found in the war directory.
