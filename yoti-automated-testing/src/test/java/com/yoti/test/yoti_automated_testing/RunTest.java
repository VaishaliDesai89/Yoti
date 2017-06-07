package com.yoti.test.yoti_automated_testing;


import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class RunTest 
{
	protected WebDriver driver;
	protected TestHelper testHelper;
	protected Actions action;
	protected Random random = new Random();
	protected int randomNum;
	protected JavascriptExecutor js;
	protected String checkStock;
	
	@BeforeClass
	public void setUp()
	{
		initialisation();
		testHelper.getPage("http://automationpractice.com/");

	}
	
	
	@Test
	protected void browseProducts()
	{
		testHelper.getElementAfterAppearsByCss("#block_top_menu>ul>li:nth-child(1)>a").click();
		testHelper.getElementAfterAppearsByCss("#subcategories>ul>li:nth-child(2)>div.subcategory-image>a").click();
		js.executeScript("javascript:window.scrollBy(650,650)");
	}
	
	
	protected boolean checkStock()
	{
		testHelper.getElementAfterAppearsByXPath("//*[@id='center_column']/ul/li[1]/div/div[2]/span/span").click();
		checkStock = testHelper.getElementAfterAppearsByXPath("//*[@id='center_column']/ul/li[1]/div/div[2]/span/span").getText();
		if(checkStock.toString().contains("In stock"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Test(priority=2)
	public void addToCart()
	{
		if(checkStock()==true)
		{
		testHelper.getElementAfterAppearsByXPath("//*[@id='center_column']/ul/li[1]/div/div[2]/div[2]/a[2]/span").click();
		testHelper.getElementAfterAppearsById("add_to_cart").click();
		}
	}
	
	@Test(priority=3)
	protected void proceedToCheckOut()
	{
		testHelper.getElementAfterAppearsByXPath("//*[@id='layer_cart']/div[1]/div[2]/div[4]/a/span").click();
		testHelper.getElementAfterAppearsByXPath("//*[@id='center_column']/p[2]/a[1]/span").click();		
	}
	
	@Test(priority=4)
	protected void signUp()
	{
		testHelper.getElementAfterAppearsById("email_create").sendKeys("Vaishali"+randomNum+"@test.yoti.com");
		testHelper.getElementAfterAppearsById("SubmitCreate").click();
		testHelper.waitImplicitly();
		testHelper.getElementAfterAppearsById("id_gender2").click();
		testHelper.getElementAfterAppearsById("customer_firstname").sendKeys("Vaishali");
		testHelper.getElementAfterAppearsById("customer_lastname").sendKeys("Desai");
		if (testHelper.getElementAfterAppearsById("email").getAttribute("value").toString().equals("Vaishali"+randomNum+"@test.yoti.com"))
		{
		testHelper.getElementAfterAppearsById("passwd").sendKeys("Abcd1234");
		testHelper.getElementAfterAppearsById("passwd").sendKeys(Keys.TAB);
       
		
		testHelper.getElementAfterAppearsById("address1").sendKeys("1 London");
		testHelper.getElementAfterAppearsById("city").sendKeys("London");
		driver.findElement(By.id("id_state")).sendKeys(Keys.chord("A" + Keys.ENTER));
		
		testHelper.getElementAfterAppearsById("postcode").sendKeys("00000");
		js.executeScript("javascript:window.scrollBy(250,350)");
		testHelper.getElementAfterAppearsById("phone_mobile").sendKeys("0"+randomNum);
		testHelper.getElementAfterAppearsById("alias").clear();
		testHelper.getElementAfterAppearsById("alias").sendKeys("My Address");
		testHelper.getElementAfterAppearsById("submitAccount").click();
		testHelper.waitForPageLoad();
		}
	}
	
	@Test(priority=5)
	protected void confirmOrderByWireTransfer()
	{
		testHelper.getElementAfterAppearsByXPath("//*[@id='center_column']/form/p/button/span").click();
		testHelper.getElementAfterAppearsById("uniform-cgv").click();
		testHelper.getElementAfterAppearsByXPath("//*[@id='form']/p/button/span").click();
		testHelper.getElementAfterAppearsByClassName("bankwire").click();
		testHelper.getElementAfterAppearsByXPath("//*[@id='cart_navigation']/button/span").click();
	}
	
	@Test(priority=6)
	protected void confirmOrderByCheque()
	{
		browseProducts();
		addToCart();
		proceedToCheckOut();
		testHelper.getElementAfterAppearsByXPath("//*[@id='center_column']/form/p/button/span").click();
		testHelper.getElementAfterAppearsById("uniform-cgv").click();
		testHelper.getElementAfterAppearsByXPath("//*[@id='form']/p/button/span").click();
		testHelper.getElementAfterAppearsByClassName("cheque").click();
		testHelper.getElementAfterAppearsByXPath("//*[@id='cart_navigation']/button/span").click();
	}
	
	protected void initialisation()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", "Chrome");
		
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		testHelper =  new TestHelper(driver);
		action = new Actions(driver);
		js = (JavascriptExecutor) driver;
		randomNum = random.nextInt(1000000);
	}
	
	@AfterClass
	public void closeDriver()
	{
		driver.close();
	}
	
}


