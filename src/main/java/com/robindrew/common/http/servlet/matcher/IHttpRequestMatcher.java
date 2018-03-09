package com.robindrew.common.http.servlet.matcher;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public interface IHttpRequestMatcher {

	boolean matches(IHttpRequest request);

}
