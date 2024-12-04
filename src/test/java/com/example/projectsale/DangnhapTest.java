package com.example.projectsale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DangnhapTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Map<String, Object> vars;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        vars = new HashMap<>();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void dangnhap() {
        driver.get("http://localhost:5173/dang-nhap");
        driver.manage().window().setSize(new Dimension(1066, 837));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-2:nth-child(3) > .relative > .block")))
                .sendKeys("adminsupper@gmail.com");

        driver.findElement(By.cssSelector(".p-2:nth-child(4) > .relative > .block"))
                .sendKeys("123@bBbb");

        driver.findElement(By.cssSelector(".hover\\3A bg-yellow-100"))
                .click();

        System.out.println("Current URL: " + driver.getCurrentUrl());

        boolean isLoginSuccessful = wait.until(ExpectedConditions.urlToBe("http://localhost:5173/"));
        assertTrue(isLoginSuccessful, "Login không thành công!");
    }

    @Test
    public void emailInvalidFormat() {
        driver.get("http://localhost:5173/dang-nhap");
        driver.manage().window().setSize(new Dimension(1066, 837));

        driver.findElement(By.cssSelector(".p-2:nth-child(3) > .relative > .block"))
                .sendKeys("admin.gmail.com");

        driver.findElement(By.cssSelector(".p-2:nth-child(4) > .relative > .block"))
                .sendKeys("123@bBbb");

        driver.findElement(By.cssSelector(".hover\\3A bg-yellow-100")).click();

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        assertTrue(alertText.contains("Email không đúng định dạng"), "Cảnh báo không xuất hiện đúng!");
        alert.accept();
    }

    @Test
    public void emailEmpty() {
        driver.get("http://localhost:5173/dang-nhap");
        driver.manage().window().setSize(new Dimension(1066, 837));

        driver.findElement(By.cssSelector(".p-2:nth-child(4) > .relative > .block"))
                .sendKeys("123@bBbb");

        driver.findElement(By.cssSelector(".hover\\3A bg-yellow-100")).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent()); // Chờ alert xuất hiện
            String alertText = alert.getText(); // Lấy nội dung cảnh báo
            assertTrue(alertText.contains("Email không đúng định dạng"), "Thông báo cảnh báo không đúng!");
            alert.accept(); // Đóng cảnh báo
        } catch (Exception e) {
            fail("Không tìm thấy cảnh báo hoặc xử lý cảnh báo thất bại: " + e.getMessage());
        }
    }


    @Test
    public void passwordEmpty() {
        driver.get("http://localhost:5173/dang-nhap");
        driver.manage().window().setSize(new Dimension(1066, 837));

        driver.findElement(By.cssSelector("input[type='email']"))
                .sendKeys("adminsupper@gmail.com");

        driver.findElement(By.cssSelector("button span")).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertMessage = alert.getText();
            System.out.println("Thông báo alert nhận được: " + alertMessage);

            alert.accept();

            String expectedAlert = "Mật khẩu phải hơn 8 ký tự";
            assertTrue(alertMessage.contains(expectedAlert), "Thông báo cảnh báo không đúng!");
        } catch (TimeoutException e) {
            fail("Không tìm thấy alert hoặc xử lý alert thất bại.");
        }
    }






    @Test
    public void emailAndPasswordEmpty() {
        driver.get("http://localhost:5173/dang-nhap");
        driver.manage().window().setSize(new Dimension(1066, 837));

        driver.findElement(By.cssSelector(".hover\\3A bg-yellow-100")).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Cảnh báo nhận được: " + alertText);
            assertTrue(alertText.contains("Email không đúng định dạng"), "Thông báo cảnh báo không đúng!");
            alert.accept(); // Đóng cảnh báo
        } catch (TimeoutException e) {
            System.err.println("Không tìm thấy cảnh báo trong thời gian chờ.");
            fail("Không tìm thấy cảnh báo hoặc xử lý cảnh báo thất bại: " + e.getMessage());
        }
    }




    @Test
    public void wrongPassword() {
        driver.get("http://localhost:5173/dang-nhap");
        driver.manage().window().setSize(new Dimension(1066, 837));

        driver.findElement(By.cssSelector(".p-2:nth-child(3) > .relative > .block"))
                .sendKeys("adminsupper@gmail.com");

        driver.findElement(By.cssSelector(".p-2:nth-child(4) > .relative > .block"))
                .sendKeys("wrongpassword");

        driver.findElement(By.cssSelector(".hover\\3A bg-yellow-100")).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Cảnh báo nhận được: " + alertText);
            assertTrue(alertText.contains("Mật khẩu không chính xác"), "Thông báo cảnh báo không đúng!");
            alert.accept(); // Đóng cảnh báo
        } catch (TimeoutException e) {
            System.err.println("Không tìm thấy cảnh báo trong thời gian chờ.");
            fail("Không tìm thấy cảnh báo hoặc xử lý cảnh báo thất bại: " + e.getMessage());
        }
    }




    @Test
    public void wrongEmail() {
        driver.get("http://localhost:5173/dang-nhap");
        driver.manage().window().setSize(new Dimension(1066, 837));

        // Nhập email sai
        driver.findElement(By.cssSelector(".p-2:nth-child(3) > .relative > .block"))
                .sendKeys("nonexistent@gmail.com");

        // Nhập mật khẩu hợp lệ
        driver.findElement(By.cssSelector(".p-2:nth-child(4) > .relative > .block"))
                .sendKeys("123@bBbb");

        // Nhấn nút đăng nhập
        driver.findElement(By.cssSelector(".hover\\3A bg-yellow-100")).click();

        // Kiểm tra thông báo lỗi trên giao diện
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Tăng thời gian chờ
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".error-message") // Kiểm tra selector chính xác
            ));
            String errorText = errorMessage.getText();
            System.out.println("Thông báo lỗi nhận được: " + errorText);
            assertTrue(errorText.contains("Email không tồn tại"), "Thông báo lỗi không đúng!");
        } catch (TimeoutException e) {
            System.err.println("Không tìm thấy thông báo lỗi trong thời gian chờ.");
            System.out.println("DOM hiện tại: " + driver.getPageSource());
            fail("Không tìm thấy thông báo lỗi hoặc xử lý lỗi thất bại: " + e.getMessage());
        }
    }






}

