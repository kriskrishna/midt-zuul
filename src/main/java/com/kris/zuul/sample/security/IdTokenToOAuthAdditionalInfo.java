package com.kris.zuul.sample.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class IdTokenToOAuthAdditionalInfo {

	/**
	 * <pre>
	 * {
	 * "sub":"6e67819a-fd42-48a3-a5dd-c5783b6bbc78",
	 * "user_name":"james.e.yaeger@kris.com",
	 * "origin":"kris-pingfed",
	 * "amr":["ext"],
	 * "iss":"https://boeing.uaa.system.pcfpre-phx.cloud.boeing.com/oauth/token",
	 * "user_attributes":{"BEMS_ID":["99635"]},
	 * "client_id":"a2c0740c-5b76-48e2-a53a-d79e1061c38f",
	 * "aud":["a2c0740c-5b76-48e2-a53a-d79e1061c38f"],
	 * "acr":{"values":["urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"]},
	 * "zid":"9de90aa1-a2bd-4caf-b51a-6d62e70134c8","grant_type":"authorization_code","user_id":"6e67819a-fd42-48a3-a5dd-c5783b6bbc78","azp":"a2c0740c-5b76-48e2-a53a-d79e1061c38f",
	 * "scope":["openid"],"auth_time":1486673014,"exp":1486716214,"iat":1486673014,"jti":"bd7d626c170141b8bf9634097d161261",
	 * "email":"james.e.yaeger@kris.com","rev_sig":"46453850","cid":"a2c0740c-5b76-48e2-a53a-d79e1061c38f"}
	 * </pre>
	 */
	
	private ObjectMapper objectMapper = new ObjectMapper() ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdTokenToOAuthAdditionalInfo.class);
	
	
	public OAuthAdditionalInfo translateToken(String idToken) {
		
		try {
			Map<String, ?> idTokenMap = parseToken(idToken);
			Map<String, List<String>> attributesMap = (Map)idTokenMap.get("user_attributes");
			
			String bemsId = attributesMap.get("BEMS_ID").get(0);
			OAuthAdditionalInfo oauthAdditionalInfo = new OAuthAdditionalInfo();
			oauthAdditionalInfo.setBemsId(bemsId);
			return oauthAdditionalInfo;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return null;		
	}
	
	private Map<String, ?> parseToken(String base64Token) throws IOException {
        String token = base64Token.split("\\.")[1];
        return objectMapper.readValue(Base64Utils.decodeFromString(token), new TypeReference<Map<String, ?>>() {
        });
    }

}
