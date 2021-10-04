package ru.appline.framework.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.И;
import ru.appline.framework.managers.PageManager;

public class MortgagePageSteps {

    private final PageManager pageManager = PageManager.getPageManager();

    @И("^Проверяем что открылась страница 'Ипотека на готовые квартиры'$")
    public void checkOpenInsurancePage() {
        pageManager.getMortgagePage().checkOpenMortgagePage();
    }

    @И("^Заполняем поля формы:$")
    public void fillFields(DataTable mapFieldsAndValue) {
        mapFieldsAndValue.asMap(String.class, String.class).forEach((key, value) ->
                pageManager.getMortgagePage().fillField((String) key, (String) value));
    }

    @И("^(Поставить|Убрать) галочку \"(.+)\"$")
    public void checkBox(String operation, String checkBoxName) {
        pageManager.getMortgagePage().checkOrUncheckBox(operation,checkBoxName);
    }

    @И("^Подождать загрузку данных")
    public void sleep() {
        pageManager.getMortgagePage().sleep();
    }

    @И("^Проверить рассчет")
    public void checkInfo(DataTable mapFieldsAndValue) {
        mapFieldsAndValue.asMap(String.class, String.class).forEach((key, value) ->
                pageManager.getMortgagePage().checkInfo((String) key, (String) value));
    }
}
