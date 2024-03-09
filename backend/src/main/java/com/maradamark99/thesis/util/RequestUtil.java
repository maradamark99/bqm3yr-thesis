package com.maradamark99.thesis.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

	private RequestUtil() {
		
	}
	
	public static String getFullServerAddress(HttpServletRequest request) {
		final var protocol = request.isSecure() ? "https" : "http";
		final var url = request.getServerName();
		final var port = request.getServerPort();
		return "%s://%s:%d".formatted(protocol, url, port);
	}
	
}

