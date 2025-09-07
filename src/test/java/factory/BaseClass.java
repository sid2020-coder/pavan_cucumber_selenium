package factory;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public static Properties properties;

    public static Faker faker = Faker.instance();

    public static WebDriver initBrowser() throws IOException {
        String executionEnv = getProperties().getProperty("execution_env");
        String browser = getProperties().getProperty("browser").toLowerCase();
        String  os = getProperties().getProperty("os").toLowerCase();
        if(executionEnv.equalsIgnoreCase("remote")){

            DesiredCapabilities capabilities = new DesiredCapabilities();

            switch (os){
                case "windows": capabilities.setPlatform(Platform.WINDOWS);break;
                case "mac" : capabilities.setPlatform(Platform.MAC);break;
                default:System.out.println("no matching os");return null;
            }
            switch (browser){
                case "chrome" :capabilities.setBrowserName("chrome");break;
                case "firefox":capabilities.setBrowserName("firefox");break;
                default:System.out.println("no matching browser");return null;
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
        }else if(executionEnv.equalsIgnoreCase("local")){

            switch (browser.toLowerCase()){
                case "chrome" : driver = new ChromeDriver();break;
                case "firefox":driver = new FirefoxDriver();break;
                default:System.out.println("no matching browser");driver=null;
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        return driver;

    }
    public static WebDriver getDriver(){
        return driver;
    }

    public static Properties getProperties() throws IOException {
        FileReader file = new FileReader(".//src/test/resources/config.properties");
        properties =new Properties();
        properties.load(file);
        return properties;
    }

    public static String getrandomNumber(){

        String generateString = faker.name().firstName();
        return  generateString;
    }

    public static String getrandomEmail(){
        String  str = faker.name().lastName();
        String num = String.valueOf(faker.number().randomNumber(10,true));
        return str+num;
    }


}
