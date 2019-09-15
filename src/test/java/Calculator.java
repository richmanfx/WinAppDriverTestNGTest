import io.appium.java_client.windows.WindowsDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Calculator {

    private static WindowsDriver CalculatorSession = null;
    private static WebElement CalculatorResult = null;
    private static final Logger log = LogManager.getLogger();

    @BeforeSuite
    public static void setup() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
        try {
            CalculatorSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
        } catch (MalformedURLException ex) {
            log.error("Calculator session create error: {}", ex.toString());
        }
        CalculatorSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        CalculatorResult = CalculatorSession.findElementByAccessibilityId("CalculatorResults");
        Assert.assertNotNull(CalculatorResult);
    }

    @BeforeClass
    public static void Clear() {
        CalculatorSession.findElementByName("Очистить").click();
        Assert.assertEquals("0", getCalculatorResultText());
    }

    private static String getCalculatorResultText()
    {
        return CalculatorResult.getText().replace("На экране показано", "").trim();
    }

    @AfterSuite
    public static void TearDown() {
        CalculatorResult = null;
        if (CalculatorSession != null) {
            CalculatorSession.quit();
        }
        CalculatorSession = null;
    }

    @Test
    public void Multiplication()
    {
        CalculatorSession.findElementByName("Два").click();
        CalculatorSession.findElementByName("Умножить на").click();
        CalculatorSession.findElementByName("Два").click();
        CalculatorSession.findElementByName("Равно").click();
        Assert.assertEquals("4", getCalculatorResultText());
    }

    @Test
    public void Addition()
    {
        CalculatorSession.findElementByName("Три").click();
        CalculatorSession.findElementByName("Плюс").click();
        CalculatorSession.findElementByName("Восемь").click();
        CalculatorSession.findElementByName("Равно").click();
        Assert.assertEquals("11", getCalculatorResultText());
    }

}
