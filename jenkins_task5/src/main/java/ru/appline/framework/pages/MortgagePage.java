package ru.appline.framework.pages;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.framework.managers.DriverManager;

import java.io.ByteArrayInputStream;

public class MortgagePage extends BasePage {

    @FindBy(xpath = "//div[contains(@class,'teaser')]/div[@class='kit-grid kit-grid_fixed']//h1")
    private WebElement title;
    @FindBy(xpath = "//div[contains(text(),'Стоимость недвижимости')]//preceding-sibling::input")
    private WebElement priceInput;
    @FindBy(xpath = "//div[contains(text(),'Первоначальный взнос')]//preceding-sibling::input")
    private WebElement firstPaymentInput;
    @FindBy(xpath = "//div[contains(text(),'Срок кредита')]//preceding-sibling::input")
    private WebElement termInput;
    @FindBy(xpath = "//span[contains(text(),'Страхование жизни и здоровья')]/../following-sibling::span//input")
    private WebElement lifeInsuranceCheckBox;
    @FindBy(xpath = "//span[contains(text(),'Молодая семья')]/../following-sibling::span//input")
    private WebElement youngFamilyCheckBox;
    @FindBy(xpath = "//div[contains(@*,'result')]//span[contains(text(),'Ежемесячный платеж')]//following-sibling::span/span")
    private WebElement monthlyPayment;
    @FindBy(xpath = "//div[contains(@*,'result')]//span[contains(text(),'Сумма кредита')]//following-sibling::span/span")
    private WebElement allAmount;
    @FindBy(xpath = "//div[contains(@*,'result')]//span[contains(text(),'Необходимый доход')]//following-sibling::span/span")
    private WebElement income;
    @FindBy(xpath = "//div[contains(@*,'result')]//div[contains(@*,'hint')]//span[contains(text(),'Процентная ставка')]//following-sibling::span/span")
    private WebElement interestRate;


    /**
     * Проверка открытия страницы, путём проверки title страницы
     *
     * @return MortgagePage - т.е. остаемся на этой странице
     */
    public MortgagePage checkOpenMortgagePage() {
        Assertions.assertTrue(title.getText().contains("Ипотека от") && title.getText().contains("на готовые квартиры")
                , "Заголовок отсутствует/не соответствует требуемому");
        driverManager.getDriver().switchTo().frame(driverManager.getDriver().findElement(By.xpath("//iframe[@id='iFrameResizer0']")));
        return this;
    }

    /**
     * Метод заполнения полей
     *
     * @param nameField - имя веб элемента, поля ввода
     * @param value     - значение вводимое в поле
     * @return MortgagePage - т.е. остаемся на этой странице
     */
    public MortgagePage fillField(String nameField, String value) {
        WebElement element = null;
        switch (nameField) {
            case "Стоимость недвижимости":

                waitUtilElementToBeClickable(priceInput).click();
                priceInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                priceInput.sendKeys(value);
                element = priceInput;
                break;
            case "Первоначальный взнос":
                scrollWithOffset(firstPaymentInput, 0, 100);
                waitUtilElementToBeClickable(firstPaymentInput).click();
                firstPaymentInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                firstPaymentInput.sendKeys(value);
                element = firstPaymentInput;
                break;
            case "Срок кредита":
                waitUtilElementToBeClickable(termInput).click();
                termInput.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                termInput.sendKeys(value);
                element = termInput;
                break;
            default:
                Assertions.fail("Поле с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Ипотека на готовые квартиры'");

        }
        wait.until(ExpectedConditions.attributeToBe(element, "value", value));
        Assertions.assertEquals(value, element.getAttribute("value")
                , "Поле '" + nameField + "' было заполнено некорректно");
        return this;
    }

    /**
     * Устанавливаем checkBox
     *
     * @param nameField - имя чекБокса
     * @return MortgagePage
     */
    public MortgagePage checkOrUncheckBox(String operation, String nameField) {
        String booleanFlag = operation.equals("Поставить") ? "true" : "false";
        WebElement element = null;
        switch (nameField) {
            case "Молодая семья":
                element = youngFamilyCheckBox;
                break;
            case "Страхование жизни и здоровья":
                element = lifeInsuranceCheckBox;
                break;
            default:
                Assertions.fail("CheckBox с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Ипотека на готовые квартиры'");

        }
        scrollWithOffset(element, 0, 100);
        waitUtilElementToBeClickable(element.findElement(By.xpath("./..")));
        if (!element.getAttribute("aria-checked").equals(booleanFlag))
            element.click();
        if (!element.getAttribute("aria-checked").equals(booleanFlag))
            element.findElement(By.xpath("./..")).click();
        wait.until(ExpectedConditions.attributeToBe(element, "aria-checked", booleanFlag));
        Assertions.assertEquals(booleanFlag, element.getAttribute("aria-checked"), "CheckBox '" + nameField + "' не поставился");
        return this;
    }


    /**
     * Проверка заполненных значений
     *
     * @param nameField - имя элемента
     * @param target    - ожидаемое значение
     * @return MortgagePage
     */
    public MortgagePage checkInfo(String nameField, String target) {
        WebElement element = null;
        switch (nameField) {
            case "Ежемесячный платеж":
                element = monthlyPayment;
                break;
            case "Сумма кредита":
                element = allAmount;
                break;
            case "Необходимый доход":
                element = income;
                break;
            case "Процентная ставка":
                element = interestRate;
                break;
            default:
                Assertions.fail("Поле с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Ипотека на готовые квартиры'");

        }

        Assertions.assertEquals(Double.parseDouble(target.replaceAll("[^0-9]", "")), Double.parseDouble(element.getText().replaceAll("[^,0-9]", "").replace(",", "."))
                , "Проверка рассчета у поля '" + nameField + "' была не пройдена");
        return this;
    }

    public void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
    }
}
