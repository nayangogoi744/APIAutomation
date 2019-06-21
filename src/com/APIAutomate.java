package com;

import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class APIAutomate {
	
	@BeforeTest
	public void preConf(){
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";	
	}
	
	
	@Test(priority=3)
	public void ValidateJSONData(){
		
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/Guwahati");
		ResponseBody body=response.getBody();
		String bodyAsString = body.asString();
		Assert.assertEquals(bodyAsString.contains("Guwahati"),true);
	}
	
	@Test(priority=2)
	public void GetAPIReturnValue()
	{   
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/Guwahati");
		ResponseBody body=response.getBody();
		System.out.println("Response body is "+body.asString());
		String connection = response.header("connection");
		System.out.println("Connection " +connection);
		String contentType = response.header("content=type");
		System.out.println("Content Type "+contentType);
		String server = response.header("server");
		System.out.println("Server Type "+server);
		
 
	}
	@Test(priority=1)
	public void ValidateAPIResponse(){
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/Guwahati");
		int statuscode = response.getStatusCode();
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
		Assert.assertEquals(statuscode, 200);
	}
	
}