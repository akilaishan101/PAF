package com;

import model.Testing;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Testing")
public class TestingManagement {
	Testing testObj = new Testing();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readTesting() {
		return testObj.readTesting();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertTesting(
			
			@FormParam("testName") String testName,
			@FormParam("tDescription") String tDescription,
			@FormParam("tDate") String tDate, 
			@FormParam("tTime") String tTime)
			{
		String output = testObj.insertTesting( testName,tDescription,tDate,tTime);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateTesting(String testData) {
		// Convert the input string to a JSON object
		JsonObject testObject = new JsonParser().parse(testData).getAsJsonObject();
		// Read the values from the JSON object
		Integer testId = testObject.get("testId").getAsInt();
		String testName = testObject.get("testName").getAsString();
		String tDescription = testObject.get("tDescription").getAsString();
		String tDate = testObject.get("tDate").getAsString();
		String tTime = testObject.get("tTime").getAsString();
		
		String output = testObj.updateTesting(testId, testName, tDescription, tDate, tTime);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAppoinment(String testData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(testData, "", Parser.xmlParser());

		// Read the value from the element <testName>
		String testId = doc.select("testId").text();
		String output = testObj.deleteTesting(testId);
		return output;
	}
	
}





