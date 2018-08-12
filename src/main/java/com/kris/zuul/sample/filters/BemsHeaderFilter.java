package com.kris.zuul.sample.filters;

import com.kris.zuul.sample.security.OAuthAdditionalInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Service;

@Service 
public class BemsHeaderFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BemsHeaderFilter.class);

	@Qualifier("oauth2ClientContext")
	@Autowired
	private OAuth2ClientContext oauth2Context ;
	
	@Override
	public Object run() {
		LOGGER.info("BemsHeaderFilter executed, intercepting: {}", RequestContext.getCurrentContext().getRequest().getRequestURI());
		OAuthAdditionalInfo oauthInfo = (OAuthAdditionalInfo)oauth2Context.getAccessToken().getAdditionalInformation().get("boeing_info");
		LOGGER.info("Additional Information about the logged in user: {}", oauthInfo);
		RequestContext.getCurrentContext().addZuulRequestHeader("boeingbemsid", oauthInfo.getBemsId());
		return null;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext currentContext = RequestContext.getCurrentContext();
		String uri = currentContext.getRequest().getRequestURI();
		return !(uri.startsWith("/css") || uri.startsWith("/images") || uri.startsWith("/scripts")); 
	}

	@Override
	public int filterOrder() {
		return 10;
	}

	@Override
	public String filterType() {
		return "pre";
	}
	

}
