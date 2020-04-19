package com;

import model.Pay;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Pay")
public class Payment {
	Pay payObj = new Pay();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPay() {
		return payObj.readPay();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPay(@FormParam("payName") String payName, @FormParam("payAmount") String payAmount,
			@FormParam("payDate") String payDate, @FormParam("payCardType") String payCardType,
			@FormParam("payCardNo") String payCardNo) {
		String output = payObj.insertPay(payName, payAmount, payDate, payCardType, payCardNo);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePay(String PayData) {
		// Convert the input string to a JSON object
		JsonObject payObject = new JsonParser().parse(PayData).getAsJsonObject();

		// Read the values from the JSON object
		String payID = payObject.get("payID").getAsString();
		String payName = payObject.get("payName").getAsString();
		String payAmount = payObject.get("payAmount").getAsString();
		String payDate = payObject.get("payDate").getAsString();
		String payCardType = payObject.get("payCardType").getAsString();
		String payCardNo = payObject.get("payCardNo").getAsString();
		String output = payObj.updatePay(payID, payName, payAmount, payDate, payCardType, payCardNo);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePay(String PayData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(PayData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String payID = doc.select("payID").text();
		String output = payObj.deletePay(payID);
		return output;
	}

}
