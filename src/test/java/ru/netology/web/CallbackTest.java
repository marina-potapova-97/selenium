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
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldNegativeFamily() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        WebElement errorMessageElement = driver.findElement(By.cssSelector("span[data-test-id='name'].input_invalid span.input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Поле обязательно для заполнения", actualErrorMessage.trim());
    }

    @Test
    void shouldNegativePhone() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("1");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        WebElement errorMessageElement = driver.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid span.input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualErrorMessage.trim());
    }

    @Test
    void shouldNegativeFamily2() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("LA");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        WebElement errorMessageElement = driver.findElement(By.cssSelector("span[data-test-id='name'].input_invalid span.input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualErrorMessage.trim());
    }

    @Test
    void shouldNegativePhone2() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        WebElement errorMessageElement = driver.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid span.input__sub"));
        String actualErrorMessage = errorMessageElement.getText();
        assertEquals("Поле обязательно для заполнения", actualErrorMessage.trim());
    }
    @Test
    void shouldNegativeCheckBox() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79220000000");
        driver.findElement(By.className("button")).click();
        WebElement updatedAgreementLabel = driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        assertTrue(updatedAgreementLabel.getAttribute("class").contains("input_invalid"));
        assertTrue(updatedAgreementLabel.isDisplayed());
    }




}

