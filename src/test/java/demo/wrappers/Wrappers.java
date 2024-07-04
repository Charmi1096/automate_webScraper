package demo.wrappers;



import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static boolean clickOnElement(WebElement element, WebDriver driver){
        if(element.isDisplayed()){
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true)", element);
                element.click();
                Thread.sleep(3000);
                return  true;
                
            } catch (InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean enterText(WebElement element, WebDriver driver, String text){
        try {
            element.click();
            element.clear();
            element.sendKeys(text);
            Thread.sleep(3000);
            return  true;
        } catch (InterruptedException e) {
            return  false;
        }
    }
}
