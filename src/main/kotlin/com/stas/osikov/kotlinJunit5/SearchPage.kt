package com.stas.osikov.kotlinJunit5

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step
import org.openqa.selenium.By

class SearchPage : BasePage(){

    private val inputSearch : SelenideElement = `$`(By.xpath("//div[@role = 'search']/descendant-or-self::input[@name = 'search']"))
    private val suggestProductList : ElementsCollection = `$$`(By.xpath("//div[contains(@class, 'search-suggest')]/descendant-or-self::ul[@class = 'suggest-list']/li[@data-name]//span[@class = 'caption']"))

    @Step
    fun openSearchPage(): SearchPage {
        open("https://rozetka.com.ua")
        return this
    }

    @Step
    fun typeText(text: String): SearchPage {
        writeText(inputSearch, text)
        return this
    }

    @Step
    fun waitForSuggestions() = suggestProductList.shouldHave(CollectionCondition.sizeNotEqual(0), wait.toLong())!!

    @Step
    fun selectDesirableProduct(desirableProduct: String): ProductPage {
        for (it in suggestProductList){
            if (readText(it).contentEquals(desirableProduct))
                it.click()
        }
        return page(ProductPage::class.java)
    }
}

