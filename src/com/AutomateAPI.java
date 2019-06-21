package com;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class AutomateAPI {

	@BeforeTest
	public void beforeTest(){
		System.out.println("****Before Executing the Test methods****");
	}
	
	@Test(priority=1)
	public void validateAPIResponse(){
		
		RestAssured.baseURI="https://reqres.in/api/login";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET);		//Sending a GET request
		long timems=response.time();
		long times=response.timeIn(TimeUnit.SECONDS);
		System.out.println(times);
		assertEquals(times, timems/1000);							//Checking the response time
		Assert.assertEquals(response.getStatusCode(), 220);			// Checking the response code
		
	}
	
	@Test(priority=2)
	public void validateGET(){
		Long i = (long) 1;
		Long j = (long) 3;
		RestAssured.baseURI="https://reqres.in/api/users";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET,"?page=1"); //Accessing the page 1
		ResponseBody body = response.getBody();
		System.out.println("JSON response for the page1");
		System.out.print(body.asString());
		String s = body.asString();
		JSONParser jsonParser = new JSONParser();
		Object object;

		try {

			object = jsonParser.parse(s);
			JSONObject jsonObject = (JSONObject) object; 
			Long pagenumber = (Long) jsonObject.get("page");
			Assert.assertEquals(pagenumber, i);             //Checking the current page number
			Long perpage = (Long) jsonObject.get("per_page");
			Assert.assertEquals(perpage, j);

			JSONArray users = (JSONArray) jsonObject.get("data");
			JSONObject obj1=(JSONObject) users.get(0);		// Getting first array object
			String first_name = (String) obj1.get("first_name");
			Assert.assertEquals(first_name, "George");
			String last_name = (String) obj1.get("last_name");
			Assert.assertEquals(last_name, "Bluth");
			
		}catch(Exception e){
				System.out.println(e);
		}
		
	}
	@Test(priority=3)
	public void doPOST(){
		
		RestAssured.baseURI ="https://reqres.in/api/users";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Virender"); // Cast
		requestParams.put("job", "Teacher");
		request.header("content-type","application/json");
		request.body(requestParams.toJSONString());
		Response response = request.post();							//Sending a POST request
		int statusCode = response.getStatusCode();					//Getting the StatusCode from the API
		System.out.println("Response code after POST :"+statusCode);
		Assert.assertEquals(statusCode, 201);						//Checking the StatusCode
		System.out.println("Newly Created User With: "+response.body().asString());			//Printing the response object

	}
	
	@Test(priority=4)
	public void doPUT(){
		
		RestAssured.baseURI ="https://reqres.in/api/users";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Virender"); // Cast
		requestParams.put("job", "Cricketer");
		request.header("content-type","application/json");
		request.body(requestParams.toJSONString());
		Response response = request.put();							//Sending a PUT request
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		System.out.println("After Updating the Job name : "+response.body().asString());

	}
	
	@Test(priority=5)
	public void doDELETE(){
		RestAssured.baseURI ="https://reqres.in/api/users/1";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		request.header("content-type","application/json");
		request.body(requestParams.toJSONString());
		Response response = request.delete();						//Sending a DELETE request
		int statusCode = response.getStatusCode();
		System.out.println("Response Code After Deleting a User "+statusCode);
		Assert.assertEquals(statusCode, 204);
	}
	
	@AfterTest
	public void afterTest(){
		System.out.println("****After executing all the Test methods****");
		
	}
	
	public static void main(String[] args) {
		

	}

}
