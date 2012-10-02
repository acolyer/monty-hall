package org.springsource.samples.montyhall.web;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsInterceptor extends HandlerInterceptorAdapter {

	private static final String ORIGIN = "Origin";
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
	private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
	                       ModelAndView modelAndView) {
		// Our REST API is accessible from anywhere
		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// For PUT requests we need an extra round-trip
		// See e.g. http://www.html5rocks.com/en/tutorials/cors/

		String origin = request.getHeader(ORIGIN);
		String acRequestMethod = request.getHeader(ACCESS_CONTROL_REQUEST_METHOD);
		String acRequestHeaders = request.getHeader(ACCESS_CONTROL_REQUEST_HEADERS);

		if (hasValue(origin) && hasValue(acRequestMethod)) {
			// this is a preflight check
			// our API only needs this for PUT requests, anything we can PUT we can also GET
			response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN,origin);
			response.setHeader(ACCESS_CONTROL_ALLOW_METHODS,"GET, PUT");
			response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS,acRequestHeaders);

			return false; // Don't continue processing, return to browser immediately
		}

		return true; // Not a preflight check, continue as normal
	}

	private boolean hasValue(String s) {
		return ((s != null) && !s.isEmpty());
	}

}