package org.singsurf.simplewebworker.common;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

/**
 * Tools which use the AutoBean framework to create a factory,
 * create the different types of bean and serialize or deserialize these beans.
 */
public class BeanTools {

	BeanFactory factory = GWT.create(BeanFactory.class);

	public DataBean makeDataBean() {
	    // Construct the AutoBean
	    AutoBean<DataBean> bc = factory.makeDataBean();

	    // Return the interface shim
	    return bc.as();
	  }

	public ResultBean makeResultBean() {
	    // Construct the AutoBean
	    AutoBean<ResultBean> bc = factory.makeResultBean();

	    // Return the interface shim
	    return bc.as();
	  }

	  public String serializeDataBean(DataBean bean) {
		    AutoBean<DataBean> autobean = AutoBeanUtils.getAutoBean(bean);
		    return AutoBeanCodex.encode(autobean).getPayload();
	  }

	  public String serializeResultBean(ResultBean bean) {
		    AutoBean<ResultBean> autobean = AutoBeanUtils.getAutoBean(bean);
		    return AutoBeanCodex.encode(autobean).getPayload();
	  }

	  public DataBean deserializeDataBean(String json) {
		    AutoBean<DataBean> bean = AutoBeanCodex.decode(factory, DataBean.class, json);
		    return bean.as();
	  }

	  public ResultBean deserializeResultBean(String json) {
		    AutoBean<ResultBean> bean = AutoBeanCodex.decode(factory, ResultBean.class, json);
		    return bean.as();
	  }

}
