package org.singsurf.simplewebworker.worker;

import com.google.gwt.webworker.client.DedicatedWorkerEntryPoint;
import com.google.gwt.webworker.client.MessageEvent;
import com.google.gwt.webworker.client.MessageHandler;

public class SimpleWorker extends DedicatedWorkerEntryPoint implements MessageHandler {

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
