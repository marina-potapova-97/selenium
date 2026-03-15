package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Василий");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldNegativeFamily() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        WebElement nameFieldContainer = driver.findElement(By.cssSelector("span[data-test-id='name'].input.input_invalid"));
        WebElement errorMessageElement = nameFieldContainer.findElement(By.className("input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Поле обязательно для заполнения", actualErrorMessage.trim());

    }

    @Test
    void shouldNegativePhone() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Петров Николай");
        elements.get(1).sendKeys("1");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        WebElement nameFieldContainer = driver.findElement(By.cssSelector("span[data-test-id='phone'].input.input_invalid"));
        WebElement errorMessageElement = nameFieldContainer.findElement(By.className("input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualErrorMessage.trim());
    }

    @Test
    void shouldNegativeFamily2() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("LA");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        WebElement nameFieldContainer = driver.findElement(By.cssSelector("span[data-test-id='name'].input.input_invalid"));
        WebElement errorMessageElement = nameFieldContainer.findElement(By.className("input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualErrorMessage.trim());
    }

    @Test
    void shouldNegativePhone2() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Петров Николай");
        elements.get(1).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        WebElement nameFieldContainer = driver.findElement(By.cssSelector("span[data-test-id='phone'].input.input_invalid"));
        WebElement errorMessageElement = nameFieldContainer.findElement(By.className("input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Поле обязательно для заполнения", actualErrorMessage.trim());
    }


}

