package ru.appline.framework;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"ru.appline.framework.utils.Listener"},
        glue = {"ru/appline/framework/steps"},
        features = {"src/test/resources/"},
        tags = "@firstTest"
)
public class CucumberRunner {
}
