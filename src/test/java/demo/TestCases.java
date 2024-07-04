package demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.utilitie.Utils;
import demo.wrappers.Wrappers;

public class TestCases {
    static  ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
    @Test
    public static void testCase01() throws InterruptedException, IOException{
        System.out.println("Start testcase : testCase01");
        driver.get("https://www.scrapethissite.com/pages/");
        //verify current link with Assert
        Assert.assertTrue(driver.getCurrentUrl().equals("https://www.scrapethissite.com/pages/"), "Unverified URL");


        //click on "Hockey Teams: Forms, Searching and Pagination"
        WebElement hockeyButton = driver.findElement(By.xpath("//a[text()='Hockey Teams: Forms, Searching and Pagination']"));
        Wrappers.clickOnElement(hockeyButton, driver);

        //initialize hashmap arraylist as datalist
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<>();


        //click on page 1
        WebElement page1 = driver.findElement(By.xpath("//ul[@class='pagination']/li[1]/a"));
        Wrappers.clickOnElement(page1, driver);

        //iterate through 4 pages
        for(int page = 1; page <= 4; page++){
            List<WebElement> rows = driver.findElements(By.xpath("//tr[@class='team']"));

            for(WebElement row : rows){
                //get text from rows
                String teamName = row.findElement(By.xpath("./td[@class='name']")).getText();
                int year = Integer.parseInt(row.findElement(By.xpath("./td[@class='year']")).getText());
                double winPercentage = Double.parseDouble(row.findElement(By.xpath("./td[contains(@class, 'pct')]")).getText());

                 //declare epoch time
                long epoch = System.currentTimeMillis() / 1000;

                //convert epoch time to string
                String epochTime = String.valueOf(epoch);

                //Win % less than 40% (0.40)
                if(winPercentage < 0.4){
                    //create hashmap to store data
                    HashMap<String, Object> datMap = new HashMap<>();
                    datMap.put("epochTime", epochTime);
                    datMap.put("teamName", teamName);
                    datMap.put("year", year);
                    datMap.put("winPercentage", winPercentage);
                    //add hashmap to arrayList
                    dataList.add(datMap);
                }
            }
            //nevigate to next page
            if(page < 4){
                WebElement nextElement = driver.findElement(By.xpath("//a[@aria-label='Next']"));
                Wrappers.clickOnElement(nextElement, driver);
            }
           
        }
         
        //print the collected data
        for(HashMap<String, Object> data : dataList){
            System.out.println("epochTime : "+data.get("epochTime")+", teamName : "+data.get("teamName")+", year : "+data.get("year")+", winPercentage : "+data.get("winPercentage"));
        }

        //store hashmap data into jsonfile
        ObjectMapper objectMapper = new ObjectMapper();
        File jasonFile = new File("src/test/output/hockey-team-data.json");
        objectMapper.writeValue(jasonFile, dataList);
        System.out.println("json data written to : "+jasonFile.getAbsolutePath());
        Assert.assertTrue(jasonFile.length() != 0);
    
        System.out.println("End testcase : testCase01");
    }
    @Test
    public static void testCase02() throws InterruptedException, IOException{
        System.out.println("Start testcase : testCase02");
        driver.get("https://www.scrapethissite.com/pages/");

        //click on "Oscar Winning Films"
        WebElement oscarWinningFilmBtn = driver.findElement(By.xpath("//a[text()='Oscar Winning Films: AJAX and Javascript']"));
        Wrappers.clickOnElement(oscarWinningFilmBtn, driver);

        Utils.scrape("2015", driver);
        Utils.scrape("2014", driver);
        Utils.scrape("2013", driver);
        Utils.scrape("2012", driver);
        Utils.scrape("2011", driver);
        Utils.scrape("2010", driver);
        System.out.println("End testcase : testCase02");
    }
}