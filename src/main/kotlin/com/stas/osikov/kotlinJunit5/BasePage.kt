package com.stas.osikov.kotlinJunit5

import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement

open class BasePage {

    val wait = 5

    fun readText(element: SelenideElement): String =
        element.waitUntil(Condition.visible, wait.toLong()).text()

    fun writeText(element: SelenideElement, text: String): SelenideElement? =
        element.waitUntil(Condition.visible, wait.toLong()).setValue(text)
}