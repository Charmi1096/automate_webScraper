package demo.utilitie;


import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.wrappers.Wrappers;


public class Utils {

   public static void scrape(String year, WebDriver driver){
        WebElement yearLink = driver.findElement(By.id(year));
        String yearLinkText = yearLink.getText();

        Wrappers.clickOnElement(yearLink, driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table']")));

        ArrayList<HashMap<String, String>> movieList = new ArrayList<>();
        List<WebElement> filmRows = driver.findElements(By.xpath("//tr[@class='film']"));
        int count = 1;
        for(WebElement filmrow : filmRows){
            String filmTitle  = filmrow.findElement(By.xpath("./td[contains(@class, 'title')]")).getText();
            String nomination = filmrow.findElement(By.xpath("./td[contains(@class, 'nominations')]")).getText();
            String awards  = filmrow.findElement(By.xpath("./td[contains(@class, 'awards')]")).getText();
            boolean isWinnerFlag = count == 1;
            String isWinner = String.valueOf(isWinnerFlag);

            long epoch = System.currentTimeMillis() / 1000;
            String epochTime = String.valueOf(epoch);

            HashMap<String, String> movieMap = new HashMap<>();
            movieMap.put("epochTime", epochTime);
            movieMap.put("year", yearLinkText);
            movieMap.put("title", filmTitle);
            movieMap.put("nomination", nomination);
            movieMap.put("awards", awards);
            movieMap.put("isWinner", isWinner);

            movieList.add(movieMap);
            count++;
        }
        //print collected data
        for(HashMap<String, String> movieData : movieList){
            System.out.println("Epoch Time : "+movieData.get("epochTime")+", Year : "+movieData.get("year")+", Film Title : "+movieData.get("title")+", Nomination : "+movieData.get("nomination")+", Awards : "+movieData.get("awards")+", Best Picture : "+movieData.get("isWinner"));
        }

        //store data into json file
        ObjectMapper mapper = new ObjectMapper();

        try {

            File jasonFile = new File("src/test/output/"+year+"oscar-winner-data.json");
            mapper.writeValue(jasonFile, movieList);
            System.out.println("json data written to : "+jasonFile.getAbsolutePath());
            Assert.assertTrue(jasonFile.length() != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
   }
  
}
