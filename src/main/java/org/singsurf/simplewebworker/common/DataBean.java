package org.singsurf.simplewebworker.common;

import java.util.List;

/**
 * Bean defining data to send to worker
 */
public interface DataBean {
	String getName();
	void setName(String s);
	List<Double> getData();
	void setData(List<Double> l);
}
