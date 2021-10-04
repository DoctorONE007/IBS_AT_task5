package ru.appline.framework.steps;

import io.cucumber.java.ru.И;
import ru.appline.framework.managers.PageManager;

public class HomePageSteps {
    private final PageManager pageManager = PageManager.getPageManager();

    @И("^Закрытия сообщения cookies$")
    public void closeCookiesDialog() {
        pageManager.getHomePage().closeCookiesDialog();
    }

    @И("^Выбираем \"(.+)\" в главном меню$")
    public void selectBaseMenu(String nameMenu) {
        pageManager.getHomePage().selectBaseMenu(nameMenu);
    }

    @И("^Выбираем \"(.+)\" в подменю главного меню$")
    public void selectSubMenu(String nameSubMenu) {
        pageManager.getHomePage().selectSubMenu(nameSubMenu);
    }

}
