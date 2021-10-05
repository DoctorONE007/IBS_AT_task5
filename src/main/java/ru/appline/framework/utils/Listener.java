package ru.appline.framework.utils;

import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;
import io.qameta.allure.Allure;
import io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.appline.framework.managers.DriverManager;

import java.io.ByteArrayInputStream;

public class Listener extends AllureCucumber6Jvm {

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        EventHandler<TestStepFinished> eventHandler = new EventHandler<TestStepFinished>() {
            @Override
            public void receive(TestStepFinished event) {
                if (event.getResult().getStatus().is(Status.FAILED)) {
                    byte[] byteImage = ((TakesScreenshot) DriverManager.getDriverManager().getDriver()).getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(byteImage), null);
                }
            }
        };
        publisher.registerHandlerFor(TestStepFinished.class, eventHandler);
        super.setEventPublisher(publisher);

    }
}
