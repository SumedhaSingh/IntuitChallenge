package com.intuitChallenge.execute;
 
/**
 * @author Sumedha Singh
 */
 
import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuitChallenege.user.Main;
import com.intuitChallenege.user.UserInterests;
 
@Path("/process")
public class Execute {
	@GET
	@Produces("application/xml")
	public String execute() throws JsonGenerationException, JsonMappingException, IOException {
		Main driverProg = new Main();
		
		List<UserInterests> interests = driverProg.execute();
		ObjectMapper mapper = new ObjectMapper();
		
		// Convert object to JSON string
		String jsonInString = mapper.writeValueAsString(interests);		
		return jsonInString;
		}
 
}