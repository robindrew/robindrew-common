package com.robindrew.common.service.component;

import org.slf4j.Logger;

import com.google.common.util.concurrent.Service;

public interface IComponent extends Service {

	String getName();

	Logger getLog();

	Service startSync();

	Service stopSync();

}
