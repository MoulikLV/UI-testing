package com.testing;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChromeResolutionTesting {

    public static void main(String[] args) {
     
        String[] resolutions = {"1920x1080", "1366x768", "1536x864"};
        String[] devices = {"Desktop"};

    
        String url = "https://www.getcalley.com/";

        
        WebDriverManager.chromedriver().setup();

      
        WebDriver driver = new ChromeDriver();

        for (String device : devices) {
            for (String resolution : resolutions) {
                try {
                  
                    String[] dimension = resolution.split("x");
                    Dimension screenSize = new Dimension(Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1]));
                    driver.manage().window().setSize(screenSize);

                  
                    driver.get(url);
                    TimeUnit.SECONDS.sleep(3); 

                 
                    takeScreenshot(driver, device, resolution);

                } catch (Exception e) {
                    System.out.println("Error occurred: " + e.getMessage());
                }
            }
        }

    
        driver.quit();

        System.out.println("Chrome resolution testing completed.");
    }

   
    public static void takeScreenshot(WebDriver driver, String device, String resolution) throws IOException {
       
        String directoryPath = "D:/Chrome /Chrome Screenshots/" + device + "/" + resolution;

      
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); 
            if (created) {
                System.out.println("Directory created successfully: " + directoryPath);
            } else {
                System.out.println("Failed to create directory: " + directoryPath);
                return; 
            }
        }

        
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String screenshotPath = directoryPath + "/Screenshot-" + timestamp + ".png";
        Files.move(screenshotFile.toPath(), new File(screenshotPath).toPath());
        System.out.println("Screenshot saved: " + screenshotPath);
    }
}
