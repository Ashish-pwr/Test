package KDD.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReusableFunction {
	WebDriver driver;
	public String[][] fetchDataFromExcel() {

		Workbook wb = null;
		String[][] data = null;
		try {
			String path = fetchprop("KEYWORD_PATH");
			File excel = new File(path);
			FileInputStream file = new FileInputStream(excel);

			System.out.println(path);
			String extn = path.substring(path.indexOf(".") + 1);

			System.out.println(extn);
			if (extn.equals("xlsx")) {
				wb = new XSSFWorkbook(file);
			} else {
				wb = new HSSFWorkbook(file);
			}
			Sheet sheet = wb.getSheet("Expenses MF(EUCAN)");
			int rownum = sheet.getLastRowNum();
			System.out.println("Rows: " + rownum);
			int column = sheet.getRow(0).getLastCellNum();

			data = new String[rownum][column];

			for (int i = 0; i < rownum; i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < column; j++) {
					Cell cell = row.getCell(j);
					String value = cell.toString();
					data[i][j] = value;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return data;
	}

	public String fetchprop(String text) {
		Properties prop = new Properties();
		FileInputStream input;
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "\\src\\objects.properties");
			prop.load(input);
		} catch (Exception ex) {

		}

		return prop.getProperty(text);
	}

	public void LaunchAppl() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\LENEVO\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(fetchprop("URL"));
		driver.manage().window().maximize();
		Thread.sleep(2000);
	}
	@SuppressWarnings("deprecation")
	public void FillText(String locatorBy, String locatorValue, String text) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		switch (locatorBy) {
		case "xpath":
			driver.findElement(By.xpath(locatorValue)).sendKeys(text);
			break;
		case "name":
			driver.findElement(By.name(locatorValue)).sendKeys(text);
			break;
		case "id":
			driver.findElement(By.id(locatorValue)).sendKeys(text);
			break;

		}
	}
	
	@SuppressWarnings("deprecation")
	public void FillNumber(String locatorBy, String locatorValue, String text) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		double d = Double.parseDouble(text);
		int i =(int) d;
		String str = Integer.toString(i);
		
		switch (locatorBy) {
		case "xpath":
			driver.findElement(By.xpath(locatorValue)).sendKeys(str);
			break;
		case "name":
			driver.findElement(By.name(locatorValue)).sendKeys(str);
			break;
		case "id":
			driver.findElement(By.id(locatorValue)).sendKeys(str);
			break;

		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void click(String locatorBy, String locatorElement) throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		switch (locatorBy) {
		case "xpath":
			driver.findElement(By.xpath(locatorElement)).click();
			break;
		case "name":
			driver.findElement(By.name(locatorElement)).click();
			break;
		case "id":
			driver.findElement(By.id(locatorElement)).click();
			break;

		}
	}

	public void isPresent(String locatorBy, String locatorElement) {
		switch (locatorBy) {
		case "xpath":
			driver.findElement(By.xpath(locatorElement)).isDisplayed();
			break;
		case "name":
			driver.findElement(By.name(locatorElement)).isDisplayed();
			break;
		case "id":
			driver.findElement(By.id(locatorElement)).isDisplayed();
			break;
		}
	}

	public void Wait() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void scroll() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,87)");
	}



	public void Scroll_Ele_click(String locatorBy, String locatorElement) {
		Actions act = new Actions(driver);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		switch (locatorBy) {
		case "xpath":
			WebElement c = driver.findElement(By.xpath(locatorElement));
			act.scrollToElement(c).click().build().perform();
			break;
		case "name":
			WebElement c1 = driver.findElement(By.name(locatorElement));
			act.scrollToElement(c1).click().build().perform();
			break;
		case "id":
			WebElement c2 = driver.findElement(By.id(locatorElement));
			act.scrollToElement(c2).click().build().perform();
			break;

		}
	}
	@Test
	public void validateValue(String locatorBy, String locatorElement, String content_param) {
		switch (locatorBy) {
		case "xpath":
			String Tmq=driver.findElement(By.xpath(locatorElement)).getAttribute("value");
			String A=content_param;
//			String  a=A;
//			String s=String.valueOf(a);  
//
//			s=s.replace(".", "");
//			s=s.replace(s.substring(s.length()-1), "");
			
			double d = Double.parseDouble(A);
			int i =(int) d;
			String str = Integer.toString(i);
			System.out.println( "Expected Number of Ques: "+ str);
			System.out.println( "Actual Number of Ques: "+ Tmq);

			Assert.assertEquals(str, Tmq);
			break;
		}
	}
}
