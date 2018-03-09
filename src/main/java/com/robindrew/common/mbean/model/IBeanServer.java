package com.robindrew.common.mbean.model;

import java.util.Set;

public interface IBeanServer {

	Set<IBean> getBeans();

	Set<IBean> getBeans(boolean standard);

	IBean getBean(String domain, String type, String name);

	IBean getBean(int id);

}
