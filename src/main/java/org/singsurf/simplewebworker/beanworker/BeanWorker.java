package org.singsurf.simplewebworker.beanworker;

import java.util.List;

import org.singsurf.simplewebworker.common.BeanTools;
import org.singsurf.simplewebworker.common.DataBean;
import org.singsurf.simplewebworker.common.ResultBean;

import com.google.gwt.webworker.client.DedicatedWorkerEntryPoint;
import com.google.gwt.webworker.client.MessageEvent;
import com.google.gwt.webworker.client.MessageHandler;

public class BeanWorker extends DedicatedWorkerEntryPoint implements MessageHandler {
	final BeanTools beanTools = new BeanTools();

	@Override
	public void onWorkerLoad() {
		setOnMessage(this);
	}

	@Override
	public void onMessage(MessageEvent event) {
		String str = event.getDataAsString();
		DataBean db = beanTools.deserializeDataBean(str);
		double sum=0;
		List<Double> list = db.getData();
		for(double d:list) {
			sum+=d;
		}
		double mean = sum/list.size();
		ResultBean rb = beanTools.makeResultBean();
		rb.setMean(mean);
		rb.setSum(sum);
		String result = beanTools.serializeResultBean(rb);
		postMessage(result);
	}

}
