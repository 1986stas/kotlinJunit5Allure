package com.stas.osikov.kotlinJunit5

import io.qameta.allure.Description
import io.qameta.allure.Epic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@Epic("Smoke test")
class ProductTest : BaseTest() {

    @ParameterizedTest
    @CsvSource("iPhone , iphone xs, Space Gray, 'rgb(255, 204, 153)'")
    @Description("Product should be successfully added to card")
    fun productShouldBeAdded(searchForProduct: String, desirableProduct: String, defaultColor: String, desirableColor: String){
        searchPage.openSearchPage()
            .typeText(searchForProduct)
            .waitForSuggestions()
        searchPage.selectDesirableProduct(desirableProduct)
        Assertions.assertEquals(productPage.pageTitleShouldHaveText(), desirableProduct, "Titles are not matched")
        productPage.selectProductFromList(defaultColor).selectColor(desirableColor)
        Assertions.assertEquals(productPage.getSelectedProductColorText(), "Gold", "Color is not matched")
        productPage.addProduct()
        Assertions.assertTrue(productPage.getCartItemsCount() == 1, "Items amount is wrong")
        Assertions.assertTrue(productPage.confirmationModalShouldBeDisplayed(), "Confirmation window is not displayed")
    }
}