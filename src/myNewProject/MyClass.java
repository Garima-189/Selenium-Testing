package myNewProject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.chrome.ChromeDriver;


import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebElement;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//comment the above line and uncomment below line to use Chrome
//import org.openqa.selenium.chrome.ChromeDriver;
public class MyClass {
	public void writeExcel(WebDriver driver,String filePath, String fileName, String sheetName) throws IOException {
		File file = new File(filePath+"//"+fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook myWorkBook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        myWorkBook = new XSSFWorkbook(inputStream);
        Sheet sheet = myWorkBook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
        String arr[]= {"fname","lname","dob","patient_street","patient_plotNo","patient_city","patient_state","patient_country","patient_pincode","source"
        		,"street","plotNo","city","state","pincode","country","director_fname","director_lname",
        		"rapidAntibodiesTest","rt_pcr","specimen","performed_date",
        		"positive","negative","uid"};
        for(int i=1;i<rowCount+1;i++) {
        	
        	Row row = sheet.getRow(i);
        	
        	
        	for(int j=0;j<arr.length;j++) {
        		if(row.getCell(j)==null)
        			continue;
        		CellType type = row.getCell(j).getCellType();
        		System.out.print(j+1+": "+type+"\n");
        		Cell cell=row.getCell(j);
        		if(cell==null) {
        			continue;
        		}
        		else if(type==cell.getCellType().STRING) {
        			driver.findElement(By.id(arr[j])).sendKeys(cell.getStringCellValue());
        		}
        		else if(type==cell.getCellType().FORMULA) {
        			WebElement element = driver.findElement(By.id(arr[j]));
//        			System.out.print("Test"+cell.getBooleanCellValue()+"\n");
    				if(row.getCell(j).getBooleanCellValue()==true){
    				element.click();
    				}
        		}
        		else if(type==cell.getCellType().NUMERIC){
        			driver.findElement(By.id(arr[j])).sendKeys(cell.getNumericCellValue()+"");
        			
      			}
        		
        		}
        	String expectedOutput = row.getCell(arr.length).getStringCellValue();
        	
        	driver.findElement(By.name("submit")).click();
        	String output = driver.findElement(By.xpath("/html/body")).getText();
        	String receivedOutput = "";
        	//System.out.print("Test"+output+"\n");
        	Cell cell= row.createCell(arr.length+1);
        	if(output.equals("Information saved to database."))
        			receivedOutput = "Successful";
        	else
        		receivedOutput = "Not Successful";
        	if(expectedOutput.contentEquals(receivedOutput)) {
        		cell.setCellValue("Passed");
        	}
        	else {
        		cell.setCellValue("Failed");
        	}
        	
        	driver.get("http://localhost:3000");
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        myWorkBook.write(outputStream);
        outputStream.close();
		
	}


    public static void main(String[] args) throws IOException {
        // declaration and instantiation of objects/variables
//    	System.setProperty("webdriver.gecko.driver","/home/garima/geckodriver");
//		WebDriver driver = new FirefoxDriver();
		//comment the above 2 lines and uncomment below 2 lines to use Chrome
		System.setProperty("webdriver.chrome.driver","/home/garima/Downloads/driver/chromedriver");
		
    	
        String baseUrl = "http://localhost:3000";
        String expectedTitle = "http://localhost:3000/addinfo";
        String actualTitle = "";
        String tagName = "";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        WebDriver driver = new ChromeDriver(options);
        driver.get(baseUrl);
//        WebElement dateBox = driver.findElement(By.xpath("//form//input[@name='dob']"));
//
//        //Fill date as mm/dd/yyyy as 09/25/2013
//
//        dateBox.click();
//        dateBox.sendKeys("12-12-2020");
        MyClass excelFile = new MyClass();
        String filePath = System.getProperty("user.dir")+"//src";
      excelFile.writeExcel(driver,filePath,"data.xlsx","Sheet1");
       // driver.findElement(By.id("rapidAntibodiesTest")).click();
        
       

        
        
        


        // launch Fire fox and direct it to the Base URL
       
        
        
        
        
        System.out.println(tagName);

        // get the actual value of the title
        actualTitle = driver.getTitle();

        /*
         * compare the actual title of the page with the expected one and print
         * the result as "Passed" or "Failed"
         */
        
       
        //close Fire fox
        driver.close();
       
    }

}