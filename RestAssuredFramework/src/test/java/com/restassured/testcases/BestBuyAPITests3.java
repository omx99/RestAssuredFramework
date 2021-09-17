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

public class BestBuyAPITests3 extends BaseTest{

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
	public void postProductByReadingRequestFromFile() throws IOException {
		
		//reading the request body from a json file directly and passing to body as a string
		Response response=	given()
				.filter(new RequestLoggingFilter(captor)) //This line is mandatory to log the request details to extent report
				.header("Content-Type","application/json")
				.contentType(ContentType.JSON)
				.log()
				.all()
				.body(generateStringFromResource(Constants.REQUEST_JSON_FOLDER_PATH+"request_post_product.json"))
				.post(Constants.BASEURL_BESTBUY+Constants.BESTBUY_POSTPRODUCT_ENDPOINT);
		
		writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());
		
		//Assert status code
				response.then().statusCode(201);

				PostProductResponse resobj= response.as(PostProductResponse.class); 

				System.out.println(resobj.toString());
				//Assertion using pojo getter method
				Assert.assertEquals(resobj.getName(), "name");
	}


}
