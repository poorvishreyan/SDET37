package com.vtiger.createAssets;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

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
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAssetsWithInvoiceTest {

	public static void main(String[] args) throws InterruptedException, EncryptedDocumentException, IOException {

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
		FileInputStream fileinputstream1 = new FileInputStream("./src/test/resources/ExcelData.xlsx");
		Workbook workbook = WorkbookFactory.create(fileinputstream1);
		//Sheet sheet = workbook.getSheet("assets");
		//Row row = sheet.getRow(1);
		//Cell cell = row.getCell(3);
		//String value = cell.toString();
		String value1 = workbook.getSheet("assets").getRow(1).getCell(3).toString();
		String orgName = value1+randomNum;
		

		//Fetch the organisation name from excel
		String value2 = workbook.getSheet("organisation").getRow(1).getCell(2).toString();
		String orgName1 = value2+randomNum;
		
		//Launch the application
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		//maximize the window
		driver.manage().window().maximize();
		//enter the url
		driver.get(url);
		//enter username in the username textbox
		driver.findElement(By.name("user_name")).sendKeys(username);
		//enter password in the passwsord textbox
		driver.findElement(By.name("user_password")).sendKeys(password);
		//click on signin
		driver.findElement(By.id("submitButton")).click();
		Thread.sleep(2000);
		
		//Click on More
		driver.findElement(By.xpath("(//a[@href=\"javascript:;\"])[1]")).click();
		
		//click on assets
		driver.findElement(By.name("Assets")).click();
		
		//click on create assets
		driver.findElement(By.xpath("//img[@src=\"themes/softed/images/btnL3Add.gif\"]")).click();
		
		//enter serial number
		driver.findElement(By.name("serialnumber")).sendKeys(orgName);
		
		//click on Data sold calender
		driver.findElement(By.id("jscal_trigger_datesold")).click();
		
		//click on date 19
		driver.findElement(By.xpath("(//tr[4]/td[@class='day'])[1]")).click();
		
		//click on customer name lookup
		driver.findElement(By.xpath("(//img[@src=\"themes/softed/images/select.gif\"])[3]")).click();
		
		Set<String> childwindow = driver.getWindowHandles();
		
		for(String string1:childwindow)
		{
			driver.switchTo().window(string1);
			String title = driver.getTitle();
			if(title.contains("Accounts&action"))
			{
				break;
			}
		}
		
		// search organisation name by entering values
		driver.findElement(By.name("search_text")).sendKeys(orgName1);
		
		//click on search button
		driver.findElement(By.name("search")).click();
		
		//click on the organisation link
		driver.findElement(By.xpath("(//a[@href=\"javascript:window.close();\"])[4]")).click();
		
		//switch back to main window
		Set<String> mainwindow = driver.getWindowHandles();
		for(String string2:mainwindow)
		{
			driver.switchTo().window(string2);
			String title2 = driver.getTitle();
			if(title2.contains("Assets&action"))
			{
				break;
			}
			
			
		}
		
		//click on product name lookup
		driver.findElement(By.xpath("(//img[@src=\"themes/softed/images/select.gif\"])[1]")).click();
		
		//switch to child window
		Set<String> childwindow1 = driver.getWindowHandles();
		for(String string2:childwindow1)
		{
			driver.switchTo().window(string2);
			String title3 = driver.getTitle();
			if(title3.contains("Products&action"))
			{
				break;
			}
		}
		
		//search the product name
		driver.findElement(By.name("search_text")).sendKeys("laptop");
		
		//click on search
		driver.findElement(By.name("search")).click();
		
		//click on product link
		driver.findElement(By.xpath("(//a[@href=\"javascript:window.close();\"])[1]")).click();
		
		//switch back to main window
		Set<String> mainwindow1 = driver.getWindowHandles();
		for(String string3:mainwindow1)
		{
			driver.switchTo().window(string3);
			String title2 = driver.getTitle();
			if(title2.contains("Assets&action"))
			{
				break;
			}
	}
		//click on date in service calender
		driver.findElement(By.id("jscal_trigger_dateinservice")).click();
		
		
		
		//click on 20th date
		//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		//WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table/tbody/tr[5]/td[3]")));
		//element.click();
		
		//driver.findElement(By.xpath("//table/tbody/tr[5]/td[3]")).click();
		
		/* Set<String> windowId = driver.getWindowHandles();
		for(String string4:windowId) {
			driver.switchTo().window(string4);
			String title3 = driver.getTitle();
			if(title3.contains("Assets&action"))
			{
				break;
			}
		}
		*/
		
		/*for(String handle:driver.getWindowHandles()) {
			try {
				driver.switchTo().window(handle);
				
			}catch(NoSuchWindowException e) {
				System.out.println("an exceptional case");
			}
		}*/
		
		//to select calendar date
		driver.findElement(By.xpath("(//tr[4]/td[@class='day'])[10]")).click();
		
		//click on Asset name
		driver.findElement(By.xpath("(//input[@type='text'])[11]")).sendKeys("property");
		
		
		
		//click on save
		driver.findElement(By.xpath("(//input[@title=\"Save [Alt+S]\"])[2]")).click();
		
		//verification
		String title = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(title.contains("123"))
		{
			System.out.println("invoice is created");
		}
		else {
			System.out.println("invoice is not created");
		}
		
		//mouse hover on Administration link
		WebElement element = driver.findElement(By.xpath("//img[@src=\"themes/softed/images/user.PNG\"]"));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		
		//click on signout button
		driver.findElement(By.xpath("//a[@href=\"index.php?module=Users&action=Logout\"]")).click();
		
	}
}
