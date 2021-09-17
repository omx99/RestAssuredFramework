package com.restassured.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.restassured.constants.Constants;
import com.restassured.requests.pojo.PostProductRequest;
import com.restassured.responses.pojo.PostProductResponse;
import com.restassured.utils.RandomUtils;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BestBuyAPITests4 extends BaseTest{

	/*
	 * Please make sure you have followed these instruction to run the server on your localhost to execute these test
	 * https://github.com/BestBuy/api-playground
	 * ******************************************
	 * 	git clone https://github.com/bestbuy/api-playground/
	 *	cd api-playground
	 *	npm install
	 *	npm start
	 *  *****************************************
	 *  Load https://localhost:3030 on your browser
	 *  
	 */


	/*
	 * There should be a test case matching this test name in RUNMANAGER and TESTDATA sheet
	 * If there are multiple data lines in TESTDATA sheet, it will treated as iteration
	 * Mark Yes in RUNMANAGER and TESTDATA to run this test case
	 * @author Amuthan Sakthivel
	 */




	@Test
	public void postProductWithoutPOJO() {
		/*	sample request body
		 *   {
		 *   "name": "string",
			  "type": "string",
			  "price": 0,
			  "shipping": 0,
			  "upc": "string",
			  "description": "string",
			  "manufacturer": "string",
			  "model": "string",
			  "url": "string",
			  "image": "string",
			  "manufacturer":"string"
			}
		 */

		Map<String, Object> mapobj = new HashMap<String,Object>();
		mapobj.put("name","name"); //getting name value from excel as user input
		mapobj.put("type","type");
		mapobj.put("price",Integer.parseInt(RandomUtils.generateRandomNumericString(2))); //Generate a random 2 digit number on fly
		mapobj.put("shipping",Integer.parseInt(RandomUtils.generateRandomNumericString(2)));
		mapobj.put("upc",RandomUtils.generateRandomString(3));
		mapobj.put("description",RandomUtils.generateRandomString(10));
		mapobj.put("model",RandomUtils.generateRandomString(3));
		mapobj.put("url",RandomUtils.generateRandomString(6));
		mapobj.put("image",RandomUtils.generateRandomString(3));
		mapobj.put("manufacturer",RandomUtils.generateRandomString(4));

		Response response=	given()
				.filter(new RequestLoggingFilter(captor)) //This line is mandatory to log the request details to extent report
				.header("Content-Type","application/json")
				.contentType(ContentType.JSON)
				.log()
				.all()
				.body(mapobj) //passing mapobj in request body
				.post(Constants.BASEURL_BESTBUY+Constants.BESTBUY_POSTPRODUCT_ENDPOINT); //posting request

		writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

		//Assert status code
		response.then().statusCode(201);

		
		Assert.assertEquals(response.jsonPath().get("name"),"name");


	}


}
