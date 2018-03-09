package com.robindrew.common.http.servlet.executor;

import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;

public interface IHttpExecutor {

	void execute(IHttpRequest request, IHttpResponse response);

}
