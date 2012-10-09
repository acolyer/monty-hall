package org.springsource.samples.montyhall.web;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsInterceptor extends HandlerInterceptorAdapter {

	private static final String LOCATION = "Location";
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
	private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	private static final String CACHE_SECONDS = "300";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// For PUT requests we need an extra round-trip
		// See e.g. http://www.html5rocks.com/en/tutorials/cors/

		String acRequestMethod = request.getHeader(ACCESS_CONTROL_REQUEST_METHOD);
		String acRequestHeaders = request.getHeader(ACCESS_CONTROL_REQUEST_HEADERS);

		// Our REST API is accessible from anywhere
		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");

		if (HttpMethod.OPTIONS.toString().equals(request.getMethod()) && hasValue(acRequestMethod)) {
			// this is a preflight check
			// our API only needs this for PUT requests, anything we can PUT we can also GET
			response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.toString());
			response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.POST.toString());
			response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.PUT.toString());
			response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, acRequestHeaders);
			response.setHeader(ACCESS_CONTROL_MAX_AGE, CACHE_SECONDS);

			return false; // Don't continue processing, return to browser immediately
		}
		else {
			response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, LOCATION);
		}

		return true; // Not a preflight check, continue as normal
	}

	private boolean hasValue(String s) {
		return ((s != null) && !s.isEmpty());
	}
}
