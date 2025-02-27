/*
 *
 *  * All Application code is Copyright 2016, The Department of Homeland Security (DHS), U.S. Customs and Border Protection (CBP).
 *  *
 *  * Please see LICENSE.txt for details.
 *
 */

package gov.gtas.controller;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {

	private Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

	@Value("${default.landing.page}")
	String defaultLandingPage;

	@Value("${neo4j.url}")
	String neo4jUrl;

	@Value("${kibana.url}")
	String kibanaUrl;

	@Value("${cypher.url}")
	String cypherUrl;

	@Value("${neo4jusername}")
	String neo4jusername;

	@Value("${neo4jpassword}")
	String neo4jpassword;

	@Value("${agency.name}")
	String agencyName;

	@GetMapping(value = "/api/config/dashboard", produces = "text/plain")
	public String getDashboard() {
		String landingPage;
		if (defaultLandingPage == null) {
			logger.warn("No default landing page set, setting to flight! Set default page in settings!");
			landingPage = "flight";
		} else {
			landingPage = defaultLandingPage;
		}
		return landingPage;
	}

	@GetMapping(value = "/api/config/kibanaUrl", produces = "text/plain")
	public String getDashboardUrl() {
		return kibanaUrl;
	}

	@GetMapping(value = "/api/config/neo4j", produces = "text/plain")
	public String getNeo4J() {
		return neo4jUrl;
	}

	@GetMapping(value = "/api/config/cypherUrl", produces = "text/plain")
	public String getCypherUrl() {
		return cypherUrl;
	}

	@GetMapping(value = "/api/config/cypherAuth", produces = "text/plain")
	public String getCypherAuth() {
		Encoder enc = Base64.getEncoder();
		String authString = neo4jusername + ":" + neo4jpassword;
		String authEncoded = enc.encodeToString(authString.getBytes());

		return "Basic " + authEncoded;
	}

	@GetMapping(value = "/api/config/agencyName", produces = "text/plain")
	public String getAgencyName() {
		return agencyName;
	}
}
