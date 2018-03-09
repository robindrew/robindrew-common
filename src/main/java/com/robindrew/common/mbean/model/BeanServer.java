package com.robindrew.common.mbean.model;

import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.TreeSet;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;

public class BeanServer implements IBeanServer {

	private MBeanServer server;

	public BeanServer() {
		server = ManagementFactory.getPlatformMBeanServer();
	}

	@Override
	public Set<IBean> getBeans() {
		Set<ObjectInstance> instances = server.queryMBeans(null, null);
		Set<IBean> beans = new TreeSet<IBean>();
		for (ObjectInstance instance : instances) {
			beans.add(new Bean(server, instance));
		}
		return beans;
	}

	@Override
	public Set<IBean> getBeans(boolean standard) {
		Set<ObjectInstance> instances = server.queryMBeans(null, null);
		Set<IBean> beans = new TreeSet<IBean>();
		for (ObjectInstance instance : instances) {
			IBean bean = new Bean(server, instance);
			if (bean.isStandard() == standard) {
				beans.add(bean);
			}
		}
		return beans;
	}

	@Override
	public IBean getBean(String domain, String type, String name) {
		for (IBean bean : getBeans()) {
			if (!bean.getDomain().equals(domain)) {
				continue;
			}
			if (!bean.getType().equals(type)) {
				continue;
			}
			if (!bean.hasName() && (name == null || name.isEmpty())) {
				return bean;
			}
			if (name == bean.getName()) {
				return bean;
			}
			if (name != null && name.equals(bean.getName())) {
				return bean;
			}
		}
		return null;
	}

	@Override
	public IBean getBean(int id) {
		for (IBean bean : getBeans()) {
			if (bean.getId() == id) {
				return bean;
			}
		}
		return null;
	}

}
