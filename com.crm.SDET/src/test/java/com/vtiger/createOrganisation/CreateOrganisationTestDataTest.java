package com.vtiger.createOrganisation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateOrganisationTestDataTest {

	public static void main(String[] args) throws IOException {
		
		//fetch data from property file
		
		FileInputStream fileinputstream= new FileInputStream("./src/test/resources/common.properties.txt");
		Properties properties = new Properties();
		properties.load(fileinputstream);
		
		//get the property from the PropertyFile by using getProperty
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		String browser = properties.getProperty("browser");
		
		
		WebDriver driver = null;
		if(browser.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			WebDriver driver1 = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else {
			driver = new ChromeDriver();
		}
		
		//To get the Random Number
		Random random= new Random();
		int randomNum = random.nextInt(100);
		
		//Fetch the data from excel sheet
		FileInputStream fileinputstream1 = new FileInputStream("./src/test/resources/ExcelData1.xlsx");
		Workbook workbook = WorkbookFactory.create(fileinputstream1);
		Sheet sheet = workbook.getSheet("sheet1");
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(2);
		String value = cell.toString();
		String orgName = value+randomNum;
		
		
		
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver1 = new ChromeDriver();
		driver1.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		//maximize the window
		driver1.manage().window().maximize();
		//enter the url
		driver1.get(url);
		//enter username
		driver1.findElement(By.name("user_name")).sendKeys(username);
		//enter password
		driver1.findElement(By.name("user_password")).sendKeys(password);
		//click on signin
		driver1.findElement(By.id("submitButton")).click();
		
		
		}
		
		

	}


