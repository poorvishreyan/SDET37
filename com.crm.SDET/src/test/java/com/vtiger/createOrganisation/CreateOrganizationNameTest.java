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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateOrganizationNameTest {

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
				int randomNum = random.nextInt();
				
				//Fetch the data from excel sheet
				FileInputStream fileinputstream1 = new FileInputStream("./src/test/resources/ExcelData1.xlsx");
				Workbook workbook = WorkbookFactory.create(fileinputstream1);
				Sheet sheet = workbook.getSheet("sheet1");
				Row row = sheet.getRow(1);
				Cell cell = row.getCell(2);
				String value = cell.toString();
				String orgName = value+randomNum;
				
		
		WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		//maximize the window
		driver.manage().window().maximize();
		//enter the url
		driver.get(url);
		//enter username
		driver.findElement(By.name("user_name")).sendKeys(username);
		//enter password
		driver.findElement(By.name("user_password")).sendKeys(password);
		//click on signin
		driver.findElement(By.id("submitButton")).click();
		//click on organuztion link
		driver.findElement(By.xpath("//a[@href=\"index.php?module=Accounts&action=index\"]")).click();
		//click on create organization
		driver.findElement(By.xpath("//img[@src=\"themes/softed/images/btnL3Add.gif\"]")).click();
		//enter organization name
		driver.findElement(By.name("accountname")).sendKeys("orgName");
		//click on save
		driver.findElement(By.xpath("(//input[@title=\"Save [Alt+S]\"])[2]")).click();
		//verify the title
		 String title = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(title.contains("orgName"))
		{
			System.out.println("organization is created");
		}
		else {
			System.out.println("organization is not created");
		}
	WebElement ele = driver.findElement(By.xpath("//img[@src=\"themes/softed/images/user.PNG\"]"));
	Actions a = new Actions(driver);
	a.moveToElement(ele).perform();
	driver.findElement(By.xpath("//a[@href=\"index.php?module=Users&action=Logout\"]")).click();
	}
	
	
}
