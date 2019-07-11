package com.stas.osikov.kotlinJunit5

import com.codeborne.selenide.Condition
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step
import org.openqa.selenium.By

class ProductPage : BasePage(){

    private val pageTitle : SelenideElement = `$`(By.xpath("//h1[contains(@itemprop, 'name')]"))
    private val buyButton : SelenideElement = `$`(By.xpath("//div[@class = 'toOrder']//button[@type = 'submit']"))

    private val totalProductsCount : ElementsCollection = `$$`(By.xpath("//div[@class = 'g-i-tile-l clearfix']/descendant-or-self::*[@class='g-i-tile-i-box']"))
    private val productTitles : ElementsCollection =
        `$$`(By.xpath("//div[@class = 'g-i-tile-l clearfix']/descendant-or-self::*[@class='g-i-tile-i-box']/descendant-or-self::*[contains(@class, 'g-i-tile-i-title clearfix')]/a"))

    private val activeProductColor = "//div[@class = 'cust-content-inner cust-content-indent']/descendant-or-self::div[contains (@class, 'variants')]//ul//span[contains (@style, '$1')]/ancestor::li//div"
    private val desirableProductColor = "//div[@class = 'cust-content-inner cust-content-indent']/descendant-or-self::div[contains (@class, 'variants')]//ul//span[contains (@style, '$1')]"

    @Step
    fun pageTitleShouldHaveText():String{
        return readText(pageTitle).replace("«","").replace("»","")
    }

    private fun getProducts():Int = totalProductsCount.size

    @Step
    fun selectProductFromList(productTitle: String): ProductPage {
        loop@ for (element in productTitles){
           if (readText(element).contains(productTitle))
               element.click()
            break@loop
        }
        return this
    }

    @Step
    fun selectColor(desirableColor: String) {
        if (isColorSelected(desirableColor)) {
            return
        }
        else `$`(By.xpath(desirableProductColor.replace("$1", desirableColor))).click()
    }

    private fun isColorSelected(productColor: String): Boolean{
        return `$`(By.xpath(activeProductColor.replace("$1", productColor))).has(Condition.exist)
    }

    @Step
    fun addProduct() = buyButton.scrollIntoView(true).click()

    @Step
    fun getSelectedProductColorText(): String{
        return readText(
            `$`(By.xpath("//div[@class = 'cust-content-inner cust-content-indent']/descendant-or-self::div[contains (@class, 'variants')]//ul//span[contains (@class, 'caption')]"))
        )
    }

    @Step
    fun confirmationModalShouldBeDisplayed(): Boolean{
        return `$`(By.xpath("//div[@class = 'cdk-overlay-pane cart-popup']")).isDisplayed
    }

    @Step
    fun getCartItemsCount():Int {
        return readText(`$`(By.xpath("//*[contains (@class, 'item_type_cart')]/descendant-or-self::*[contains (@class, 'button-counter')]")).scrollIntoView(true)).toInt()
    }

}
