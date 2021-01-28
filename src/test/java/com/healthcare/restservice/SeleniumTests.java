package com.healthcare.restservice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class SeleniumTests {
    private WebDriver webDriver;
    private final String URL = "localhost:4200";
    // /media/unknown/data/healthCare/chromedriver
    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "/var/lib/jenkins/workspace/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        webDriver = new ChromeDriver(options);
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

    @Test
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
    @Test
    public void ProductDetailsTest() throws InterruptedException {
        mylogin();
        Thread.sleep(2000);
        WebElement element = webDriver.findElement(By.xpath("//*[@id=\"prodName\"]"));
        element.click();
        Thread.sleep(5000);
        Assert.assertEquals(webDriver.getTitle(), "NetMeds | Product");
    }

    private void mylogin() throws InterruptedException {
        webDriver.get(this.URL + "/login");
        WebElement username = webDriver.findElement(By.name("username"));
        username.sendKeys("san1@gmail.com");
        WebElement password = webDriver.findElement(By.name("password"));
        password.sendKeys("12345");
        username.submit();
        Thread.sleep(10000);
    }

    @Test
    public void addToCart() throws InterruptedException {
        mylogin();
        WebElement element = webDriver.findElement(By.xpath("//*[@id=\"prodName\"]"));
        element.click();
        Thread.sleep(5000);
        WebElement element1 = webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-product-description/div[2]/section/div/div[2]/nav/ul/li[3]/button"));
        element1.click();
        element1.click();
        webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-product-description/div[2]/section/div/div[2]/button")).click();
        Thread.sleep(2000);
        webDriver.findElement(By.tagName("svg")).click();
        Thread.sleep(5000);
        Assert.assertEquals(webDriver.getTitle(), "NetMeds | cart");

    }
        @Test
        public void checkOut() throws InterruptedException {
            mylogin();
            WebElement element = webDriver.findElement(By.xpath("//*[@id=\"prodName\"]"));
            element.click();
            Thread.sleep(5000);
            WebElement element1 = webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-product-description/div[2]/section/div/div[2]/nav/ul/li[3]/button"));
            element1.click();
            element1.click();
            webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-product-description/div[2]/section/div/div[2]/button")).click();
            Thread.sleep(2000);
            webDriver.findElement(By.tagName("svg")).click();
            Thread.sleep(5000);
            webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-cart/div/div/div[2]/table/tr[5]/td[2]/button")).click();
            Thread.sleep(2000);
        }
        @Test
        public void makePayment() throws InterruptedException {
            mylogin();
            WebElement element = webDriver.findElement(By.xpath("//*[@id=\"prodName\"]"));
            element.click();
            Thread.sleep(5000);
            WebElement element1 = webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-product-description/div[2]/section/div/div[2]/nav/ul/li[3]/button"));
            element1.click();
            element1.click();
            webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-product-description/div[2]/section/div/div[2]/button")).click();
            Thread.sleep(2000);
            webDriver.findElement(By.tagName("svg")).click();
            Thread.sleep(5000);
            webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-cart/div/div/div[2]/table/tr[5]/td[2]/button")).click();
            Thread.sleep(2000);
            webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-payment/div/div[2]/div/div[2]/div/div[4]/button")).click();
            Thread.sleep(5000);
            WebElement element2 = webDriver.findElement(By.xpath("/html/body/app-root/app-user/app-payment/div/div[2]/h4"));
            Assert.assertEquals(element2.getText(), "payment failure");
        }


    @AfterMethod
    public void afterMethod() {
        if(webDriver != null) {
            webDriver.close();
            webDriver.quit();
        }
    }
    /*@AfterMethod(groups = "req1uireLogin")
    public void afterLoginMethod() {
        if(webDriver != null) {
            webDriver.close();
            webDriver.quit();
        }
    }*/
}
