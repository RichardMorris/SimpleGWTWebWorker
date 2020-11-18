package org.singsurf.simplewebworker.common;

/**
 * Bean defining results returned by worker.
 */
public interface ResultBean {
	double getSum();
	void setSum(double x);
	double getMean();
	void setMean(double x);
}
