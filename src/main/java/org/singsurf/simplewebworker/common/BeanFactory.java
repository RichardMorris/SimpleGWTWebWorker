package org.singsurf.simplewebworker.common;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

/**
 * An interface defining a factory to create different types of AutoBeans.
 */
public interface BeanFactory extends AutoBeanFactory {
	  AutoBean<DataBean> makeDataBean();
	  AutoBean<ResultBean> makeResultBean();
}
