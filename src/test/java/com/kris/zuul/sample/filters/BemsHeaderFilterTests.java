package com.kris.zuul.sample.filters;

import com.netflix.zuul.context.RequestContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;



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
