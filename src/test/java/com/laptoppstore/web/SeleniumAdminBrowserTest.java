package com.laptoppstore.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumAdminBrowserTest {

    private static WebDriver driver;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void teardownClass() {
        if (driver != null) driver.quit();
    }

    @Test
    public void category_browser_crud() throws InterruptedException {
        String base = "http://localhost:" + port + "/admin/categories";
        driver.get(base);
        // Click add
        WebElement addLink = driver.findElement(By.linkText("Add"));
        addLink.click();
        Thread.sleep(200);
        // fill form
        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("SeleniumCat");
        WebElement submit = driver.findElement(By.cssSelector("button[type=submit]"));
        submit.click();
        Thread.sleep(300);
        // verify list contains
        assertThat(driver.getPageSource()).contains("SeleniumCat");
        // find edit link for our item
        WebElement edit = driver.findElement(By.linkText("Edit"));
        edit.click();
        Thread.sleep(200);
        WebElement nameEdit = driver.findElement(By.name("name"));
        nameEdit.clear();
        nameEdit.sendKeys("SeleniumCat2");
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        Thread.sleep(300);
        assertThat(driver.getPageSource()).contains("SeleniumCat2");
        // delete: click first Delete button
        WebElement delBtn = driver.findElement(By.xpath("//form/button[text()='Delete']"));
        delBtn.click();
        Thread.sleep(300);
        assertThat(driver.getPageSource()).doesNotContain("SeleniumCat2");
    }

    @Test
    public void user_browser_crud() throws InterruptedException {
        String base = "http://localhost:" + port + "/admin/users";
        driver.get(base);
        driver.findElement(By.linkText("Add")).click();
        Thread.sleep(200);
        driver.findElement(By.name("email")).sendKeys("selenium@example.com");
        driver.findElement(By.name("password")).sendKeys("pass");
        driver.findElement(By.name("fullName")).sendKeys("S User");
        driver.findElement(By.name("address")).sendKeys("Addr");
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        Thread.sleep(300);
        assertThat(driver.getPageSource()).contains("selenium@example.com");
        driver.findElement(By.linkText("Edit")).click();
        Thread.sleep(200);
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("selenium2@example.com");
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        Thread.sleep(300);
        assertThat(driver.getPageSource()).contains("selenium2@example.com");
        // delete first
        WebElement delBtn = driver.findElement(By.xpath("//form/button[text()='Delete']"));
        delBtn.click();
        Thread.sleep(300);
        assertThat(driver.getPageSource()).doesNotContain("selenium2@example.com");
    }
}
