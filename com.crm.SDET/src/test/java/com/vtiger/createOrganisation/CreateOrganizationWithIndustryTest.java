package com.vtiger.createOrganisation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
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
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
/**
 * 
 * @author soumya
 *
 */
public class CreateOrganizationWithIndustryTest {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		//fetch data from property file
		FileInputStream fileinputstream1 = new FileInputStream("./src/test/resources/common.properties.txt");
		Properties properties = new Properties();
		properties.load(fileinputstream1);
		
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
		FileInputStream fileinputstream2 = new FileInputStream("./src/test/resources/ExcelData1.xlsx");
		Workbook workbook = WorkbookFactory.create(fileinputstream2);
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
		driver.get("http://localhost:8888");
		//enter username
		driver.findElement(By.name("user_name")).sendKeys("admin");
		//enter password
		driver.findElement(By.name("user_password")).sendKeys("admin");
		//click on signin
		driver.findElement(By.id("submitButton")).click();
		//click on organuztion link
		driver.findElement(By.xpath("//a[@href=\"index.php?module=Accounts&action=index\"]")).click();
		//click on create organization
		driver.findElement(By.xpath("//img[@src=\"themes/softed/images/btnL3Add.gif\"]")).click();
		//enter organization name
		driver.findElement(By.name("accountname")).sendKeys("jspider");
		//select industry dropdown
		WebElement ele = driver.findElement(By.name("industry"));
		Select s = new Select(ele);
		s.selectByVisibleText("Communications");
		
		//select Type dropdown
		WebElement ele1 = driver.findElement(By.name("accounttype"));
		Select s1 = new Select(ele1);
		s1.selectByVisibleText("Investor");
		
		//click on save
		driver.findElement(By.xpath("(//input[@title=\"Save [Alt+S]\"])[2]")).click();
		
		String title = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(title.contains("jspider"))
		{
			System.out.println("organization created");
		}
		else {
			System.out.println("organization not created");
		}
		
		
		

	}

}
