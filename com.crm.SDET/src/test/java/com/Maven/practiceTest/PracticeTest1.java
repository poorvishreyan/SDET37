package com.Maven.practiceTest;

import org.testng.annotations.Test;

public class PracticeTest1 {
	
	@Test
	public void PracticeTest()
	{
		String url = System.getProperty("URL");
		String browser = System.getProperty("BROWSER");
		String username = System.getProperty("USERNAME");
		String password = System.getProperty("PASSWORD");
		
		//
		
		System.out.println("TYSS====>");
		System.out.println("url===> "+url);
		System.out.println("browser====>"+browser);
		System.out.println("username===>"+username);
		System.out.println("password===>"+password);
		
	}
	

}
