package br.edu.utfpr.exemplomaven;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author andreendo
 */
public class ExemploTest {

    /**
     * Vc precisa identificar onde estah o chromedriver. Baixar de:
     * https://sites.google.com/a/chromium.org/chromedriver/downloads
     * Versão do Chrome: 67.0.3396.87
     * Versão utilizada do chromedriver: 2.40
     */
    private static String CHROMEDRIVER_LOCATION = "C:\\Users\\julia\\OneDrive\\Documents\\GitProjects\\Divers\\chromedriver.exe";

    private static int scId = 0;

    WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_LOCATION);
    }

    @Before
    public void before() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("headless");
        chromeOptions.addArguments("window-size=1200x600");
        chromeOptions.addArguments("start-maximized");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @After
    public void after() {
       driver.close();
    }

    @Ignore
    @Test
    public void testGoogleSearch() {
        driver.get("https://www.google.com.br/");
        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys("teste de software");

        takeScreenShot();

        searchInput.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until((ExpectedCondition<Boolean>) (WebDriver d) -> d.getTitle().toLowerCase().startsWith("teste"));

        takeScreenShot();

        assertTrue(driver.getTitle().startsWith("teste de software"));
    }

    private void takeScreenShot() {
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            scId++;
            FileUtils.copyFile(sourceFile, new File("./res/" + scId + ".png"));
        } catch (IOException e) {
        }
    }
    
    @Ignore
    @Test
    public void testAcessRationLinkFAC() {

        driver.get("https://ration.io");
        driver.manage().window().maximize();

        WebElement fac = driver.findElement(By.cssSelector("#page-header > div > a:nth-child(1)"));
        fac.click();

        takeScreenShot();

        WebElement body = driver.findElement(By.xpath("/html/head/title"));

        assertTrue(driver.getPageSource().contains("Where can I go for more help"));
    }

    @Ignore
    @Test
    public void testCreateanAccountthereisalready() {
        driver.get("https://ration.io/signup");
        driver.manage().window().maximize();

        WebElement fullname = driver.findElement(By.id("full-name"));
        fullname.clear();
        fullname.sendKeys("Usuario teste8");

        WebElement email = driver.findElement(By.id("email-address"));
        email.clear();
        email.sendKeys("usuarioteste8@gmail.com");

        WebElement psw = driver.findElement(By.id("password"));
        psw.clear();
        psw.sendKeys("ration@1234");

        WebElement cpsw = driver.findElement(By.id("confirm-password"));
        cpsw.clear();
        cpsw.sendKeys("ration@1234");
        
        WebElement terms = driver.findElement(By.id("terms-agreement"));
        terms.click();
        
        WebElement botao = driver.findElement(By.cssSelector("#signup > div > div > form > div:nth-child(6) > button"));
        botao.click();
        
        WebElement critica = driver.findElement(By.cssSelector("#signup > div > div > form > p > small"));
        assertTrue( critica.getText().contains("It looks like there's already an account with your email address.") );
       
    }

    @Test
    public void testLoginanAccountthereisnotalready() {
        driver.get("https://ration.io/login");
        driver.manage().window().maximize();

        WebElement login = driver.findElement(By.cssSelector("#login > div > div > form > div:nth-child(1) > input"));
        login.clear();
        login.sendKeys("juliano@teste.com");

        WebElement psw = driver.findElement(By.cssSelector("#login > div > div > form > div:nth-child(2) > input"));
        psw.clear();
        psw.sendKeys("testeration01");
        
        WebElement botao = driver.findElement(By.cssSelector("#login > div > div > form > div:nth-child(4) > button"));
        botao.click();
        
        WebElement critica = driver.findElement(By.cssSelector("#login > div > div > form > p > small"));
        assertTrue( critica.getText().contains("The credentials you entered are not associated with an account. Please check your email and/or password and try again.") );
       
    }
}
