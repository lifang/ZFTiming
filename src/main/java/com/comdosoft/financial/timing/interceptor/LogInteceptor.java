package com.comdosoft.financial.timing.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class LogInteceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(LogInteceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Map<String,String[]> params = request.getParameterMap();
		Map<String,List<String>> convertParmas = Maps.transformValues(params, 
				new Function<String[], List<String>>() {
					@Override
					public List<String> apply(String[] input) {
						return Arrays.asList(input);
					}
			
		});
		LOG.info("remoteAddr:{},requestUri:{},method:{},queryString:{},params:{}",
				request.getRemoteAddr(),
				request.getRequestURI(),
				request.getMethod(),
				request.getQueryString(),
				convertParmas);
		return true;
	}

}
