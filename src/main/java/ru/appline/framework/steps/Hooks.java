package ru.appline.framework.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.appline.framework.managers.DriverManager;
import ru.appline.framework.managers.InitManager;

import java.io.ByteArrayInputStream;



import static ru.appline.framework.managers.DriverManager.getDriver;
public class Hooks {

    @Before
    public void before() {
        InitManager.initFramework();
    }


    @After
    public void after(Scenario scenario) {
            byte[] byteImage = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot","image/png",new ByteArrayInputStream(byteImage),null);

        InitManager.quitFramework(); }
}
