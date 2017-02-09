package com.boeing.zuul.sample.filters;

import static org.assertj.core.api.Assertions.*;


import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.netflix.zuul.context.RequestContext;



public class BemsHeaderFilterTests {
	private BemsHeaderFilter filter = new BemsHeaderFilter();
	private MockHttpServletRequest testHttpServletRequest = new MockHttpServletRequest();

	@Test
	public void testShouldNotFilterACssLocation() {
		RequestContext requestContext = new RequestContext();
		testHttpServletRequest.setRequestURI("/css/my.css");
		requestContext.setRequest(testHttpServletRequest);
		RequestContext.testSetCurrentContext(requestContext);
		assertThat(filter.shouldFilter()).isEqualTo(false);
	}
	
	@Test
	public void testShouldNotFilterAImageLocation() {
		RequestContext requestContext = new RequestContext();
		testHttpServletRequest.setRequestURI("/images/my.jpeg");
		requestContext.setRequest(testHttpServletRequest);
		RequestContext.testSetCurrentContext(requestContext);
		assertThat(filter.shouldFilter()).isEqualTo(false);
	}
	
	@Test
	public void testShouldNotFilterAScriptsLocation() {
		RequestContext requestContext = new RequestContext();
		testHttpServletRequest.setRequestURI("/scripts/my.jpeg");
		requestContext.setRequest(testHttpServletRequest);
		RequestContext.testSetCurrentContext(requestContext);
		assertThat(filter.shouldFilter()).isEqualTo(false);
	}
	
	@Test
	public void testShouldFilterAPage() {
		RequestContext requestContext = new RequestContext();
		testHttpServletRequest.setRequestURI("/myhomepage/");
		requestContext.setRequest(testHttpServletRequest);
		RequestContext.testSetCurrentContext(requestContext);
		assertThat(filter.shouldFilter()).isEqualTo(true);
	}	
}
