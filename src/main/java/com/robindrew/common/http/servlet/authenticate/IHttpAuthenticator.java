package com.robindrew.common.http.servlet.authenticate;

import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;

public interface IHttpAuthenticator {

	boolean authenticate(IHttpRequest request, IHttpResponse response);

}
