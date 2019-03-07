package com.afkl.cases.df.oauth;

import com.afkl.cases.df.utils.PropertiesLoader;

import java.util.Properties;

/**
 * Source: IBM tutorial OAuth 2.0
 * Adapted to the KLM case
 */
public class OAuth2Client {
	public String getToken() throws Exception{
		String token = null;

		//Load the properties file
		Properties config = PropertiesLoader.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);
		
		//Generate the OAuthDetails bean from the config properties file
		OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(config);
		
		//Validate Input
		if(!OAuthUtils.isValidInput(oauthDetails)){
			System.out.println("Please provide valid config properties to continue.");
		}
		
		//Determine operation
		if(oauthDetails.isAccessTokenRequest()){
			//Generate new Access token
			String accessToken = OAuthUtils.getAccessToken(oauthDetails);

			if(OAuthUtils.isValid(accessToken)){
				System.out.println("Successfully generated Access token for client_credentials grant_type: "+accessToken);
				token = accessToken;
			}
			else{
				System.out.println("Could not generate Access token for client_credentials grant_type");
			}
		} else {
			//Access protected resource from server using OAuth2.0
			// Response from the resource server must be in Json or Urlencoded or xml
			System.out.println("Resource endpoint url: " + oauthDetails.getResourceServerUrl());
			System.out.println("Attempting to retrieve protected resource");
			OAuthUtils.getProtectedResource(oauthDetails);
		}
		return token;
	}
}
