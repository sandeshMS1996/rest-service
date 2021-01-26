package com.healthcare.restservice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class SeleniumTests {
    private WebDriver webDriver;
    private final String URL = "localhost:4200";
    // /media/unknown/data/healthCare/chromedriver
    @BeforeMethod
    public void beforeMethod() {
        System.setProperty("webdriver.chrome.driver",
                "/var/lib/jenkins/workspace/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-dev-shm-usage");
        webDriver = new ChromeDriver(options);
    }
    @BeforeGroups("requireLogin")
    public void login() throws InterruptedException {
        beforeMethod();
       /* webDriver.get(URL + "/login");
        WebElement username = webDriver.findElement(By.name("username"));
        username.sendKeys("san1@gmail.com");
        WebElement password = webDriver.findElement(By.name("password"));
        password.sendKeys("12345");
        WebElement btn = webDriver.findElement(By.className("btn"));
        username.submit();
        Thread.sleep(5);*/
    }
    @Test
    public void loginFailTest() throws InterruptedException {
        // navigate to the web site
        webDriver.get(this.URL + "/login");
        WebElement username = webDriver.findElement(By.name("username"));
        username.sendKeys("san123@gmail.com");
        WebElement password = webDriver.findElement(By.name("password"));
        password.sendKeys("12345");
        username.submit();
        Thread.sleep(5000);
        Assert.assertEquals(webDriver.getTitle(), "NetMeds");
    }

    public void loginSuccessTest() throws InterruptedException {
        // navigate to the web site
        webDriver.get(this.URL + "/login");
        WebElement username = webDriver.findElement(By.name("username"));
        username.sendKeys("san1@gmail.com");
        WebElement password = webDriver.findElement(By.name("password"));
        password.sendKeys("12345");
        WebElement btn = webDriver.findElement(By.className("btn"));
        username.submit();
        Thread.sleep(5000);
        Assert.assertEquals(webDriver.getTitle(), "NetMeds | Product List");
    }
   /* @Test(groups = {"requireLogin"})
    public void ProductDetailsTest() throws InterruptedException {
        webDriver.get(this.URL + "/user/product/3");
        Thread.sleep(3000);
        Assert.assertEquals(webDriver.getTitle(), "NetMeds | Product");
    }*/

    @AfterMethod
    public void afterMethod() {
        if(webDriver != null) {
            webDriver.close();
            webDriver.quit();
        }
    }
}
