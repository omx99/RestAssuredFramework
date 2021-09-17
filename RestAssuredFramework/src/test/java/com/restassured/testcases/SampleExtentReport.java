package com.restassured.testcases;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

import org.apache.commons.io.output.WriterOutputStream;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;

/**
 * @author vamsir
 *
 */

public class SampleExtentReport {

    static StringWriter requestWriter;
    static PrintStream requestCapture;
    static ExtentTest test;
    static ExtentReports extentReports;

    @BeforeSuite
    public void init(ITestContext context) {


        extentReports = new ExtentReports(System.getProperty("user.dir") + File.separator + "reports" + File.separator
                +this.getClass().getSimpleName().toString() + ".html");

    }



    @Test
    public static void sampleTest() {

        test = extentReports.startTest("Get Sample Test");

        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));

        Response response = RestAssured.given().filter(new RequestLoggingFilter(requestCapture)).and().baseUri("https://jsonplaceholder.typicode.com").and().basePath("/todos/1").when().get();

        requestCapture.flush();
    System.out.println("Request: "+requestWriter.toString());
    System.out.println("Response: "+response.asString());
    test.log(LogStatus.INFO, "Request : "+ requestWriter.toString());
    test.log(LogStatus.INFO, "Response : " + response.asString());

        extentReports.endTest(test);

    }


    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(LogStatus.FAIL, result.getThrowable());
        }
    }

    @AfterSuite
    public void end() {

        extentReports.flush();
        extentReports.close();

    }

}
