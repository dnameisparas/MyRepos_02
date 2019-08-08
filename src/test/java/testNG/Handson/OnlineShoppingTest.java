package testNG.Handson;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import junit.framework.Assert;

public class OnlineShoppingTest {
	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	
	
@BeforeTest
public void startReportBeforeTest()
{
	htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/testReport.html");
	
	extent= new ExtentReports();
	extent.attachReporter(htmlReporter);
	htmlReporter.config().setDocumentTitle("Extent report demo");
	htmlReporter.config().setReportName("Test report");
	htmlReporter.config().setTheme(Theme.STANDARD);
	htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd,yyyy, hh:mm a '('zzzz')'");
	

	
	
}

/*@Test(priority=1)
public void testRegistration()
{
	test=extent.createTest("TC01", "application accessibility");
	
	Assert.assertEquals("paras", driver.getTitle());
	
}*/
@AfterMethod
public void getResultAfterMethod(ITestResult result) throws IOException 
{
	if(result.getStatus()== ITestResult.FAILURE) {

	test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+"FAILED", ExtentColor.RED));
	
	TakesScreenshot snapshot=(TakesScreenshot)driver;
	File src = snapshot.getScreenshotAs(OutputType.FILE);
	String Path= System.getProperty("user.dir")+"/test-output/screens/" +result.getName()+".png";
	FileUtils.copyFile(src, new File(Path));
	test.addScreenCaptureFromPath(Path,result.getName());
	test.fail(result.getThrowable());
	}
else if(result.getStatus()==ITestResult.SUCCESS)
{
	test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"PASSED", ExtentColor.GREEN));
}
else {
	test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"SKIPPED", ExtentColor.ORANGE));
	test.skip(result.getThrowable());
}}


@Test(priority=2)
public void testLogin() throws IOException 
{
	File src = new File("C:\\Users\\training_d5.01.02\\Desktop\\New folder (3)\\logindata.xlsx");
	 FileInputStream fis = new FileInputStream(src);
	 XSSFWorkbook wb = new XSSFWorkbook(fis);
	 XSSFSheet sheet1 = wb.getSheetAt(0);
	 int rowcount = sheet1.getLastRowNum();
	 System.out.println("Total no of Row = " + rowcount);
	 driver=Driver.getDriver("chrome");
		driver.get("http://10.232.237.143:443/TestMeApp");
	 for (int i = 0; i <= rowcount; i++) {
		  test = extent.createTest("LoginTest->"+i,"TestMeAPP");
		  String username = sheet1.getRow(i).getCell(0).getStringCellValue();
		  String password = sheet1.getRow(i).getCell(1).getStringCellValue();
	
      driver.manage().window().maximize();
	  driver.findElement(By.linkText("SignIn")).click();
	  driver.findElement(By.id("userName")).sendKeys(username);
	  driver.findElement(By.id("password")).sendKeys(password);
	  driver.findElement(By.name("Login")).click();
	  Assert.assertEquals(driver.getTitle(), "Home"); 
	  System.out.println("Login Success");
	 }
	 wb.close();
}
@Test(priority=3)
public void testCart() throws InterruptedException 
{
	test=extent.createTest("AddCartTest->", "application accessibility");
	WebElement catg = driver.findElement(By.xpath("//*[@id=\"menu3\"]/li[2]/a"));
	Actions act1 = new Actions(driver);
	act1.moveToElement(catg).build().perform();
	
	WebElement elec = driver.findElement(By.xpath("//*[@id=\"menu3\"]/li[2]/ul/li[1]/a/span"));
	Actions act2 = new Actions(driver);
	act2.moveToElement(elec).click().perform();
	elec.click();
	
	WebElement head = driver.findElement(By.xpath("//*[@id=\"submenuul11290\"]/li[1]/a/span"));
	Actions act3 = new Actions(driver);
	act3.moveToElement(head).click().perform();
	Thread.sleep(2000);
	
	driver.findElement(By.linkText("Add to cart")).click();
	
	
	 driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/div/div/div[2]/div/a[2]")).click();
	 Thread.sleep(3000);
	 
	 String a_title= driver.findElement(By.xpath("//h4[text()='Headphone']")).getText();
	 String e_title="Headphone";
	 Assert.assertEquals(a_title, e_title);
	 
	System.out.println("Headphones added to cart");
}
@Test(priority=4)
public void testPayment() throws IOException, InterruptedException
{
	test=extent.createTest("PaymentTest", "application accessibility");
	
	  driver.findElement(By.xpath("//*[@id=\"cart\"]/tfoot/tr[2]/td[5]/a")).click(); // checkout
	  Thread.sleep(2000);
	  
	  driver.findElement(By.xpath("/html/body/b/div/div/div[1]/div/div[2]/div[3]/div/form[2]/input")).click();  //proceed to pay
	  Thread.sleep(5000);
		
	  driver.findElement(By.xpath("//*[contains(text(),'Andhra Bank')]")).click(); // select andhra bank
	  driver.findElement(By.xpath("//*[@id=\"btn\"]")).click(); //continue
	  
	 /* File src = new File("C:\\Users\\training_d5.01.02\\Desktop\\New folder (3)\\loginpaymnt.xlsx");
		 FileInputStream fis = new FileInputStream(src);
		 XSSFWorkbook wb2 = new XSSFWorkbook(fis);
		 XSSFSheet sheet1 = wb2.getSheetAt(0);
		 int rowcount = sheet1.getLastRowNum();
		 System.out.println("Total no of Row = " + rowcount);
		 for (int i = 0; i <= rowcount; i++) {
			  test = extent.createTest("LoginTest->"+i,"TestMeAPP");
			  String username = sheet1.getRow(i).getCell(0).getStringCellValue();
			  String password = sheet1.getRow(i).getCell(1).getStringCellValue();
			  String transaction = sheet1.getRow(i).getCell(3).getStringCellValue();*/
		
		  driver.findElement(By.name("username")).sendKeys("123456");
		  driver.findElement(By.name("password")).sendKeys("Pass@456");
		  driver.findElement(By.xpath("//*[@id='horizontalTab']/div[2]/div/div/div/div/form/div/div[3]/input")).click();
		//  Assert.assertEquals(driver.getTitle(), "Home"); 
		  
		  driver.findElement(By.xpath("//input[@value='PASSWORD']")).sendKeys("Trans@456");
		  driver.findElement(By.xpath("//input[@value='PayNow']")).click();
		  String a_title= driver.getTitle();
			 String e_title="Order Details";
			 Assert.assertEquals(a_title, e_title);
			 
			System.out.println("Order Recived");
		 }
	


@AfterTest
public void endReportAfterTest()
{
	extent.flush();
}

  }
  
  

