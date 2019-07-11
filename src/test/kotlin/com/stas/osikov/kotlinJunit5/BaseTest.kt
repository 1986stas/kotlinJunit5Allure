package com.stas.osikov.kotlinJunit5

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.io.FileInputStream
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTest {

    val searchPage  = SearchPage()
    val productPage = ProductPage()

    @BeforeAll
    fun setUpDriver(){
        SelenideLogger.addListener("AllureSelenide", AllureSelenide().
            screenshots(true).savePageSource(false))
        val properties = Properties()
        properties.load(FileInputStream("./src/test/resources/seleniumSettings.properties"))
        Configuration.browser = properties.getProperty("browser")
        Configuration.browserSize = properties.getProperty("browser_size")
        Configuration.headless = true
    }

    @AfterAll
    fun tearDown(){
        Selenide.clearBrowserCookies()
        Selenide.clearBrowserLocalStorage()
    }
}