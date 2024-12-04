package com.example.projectsale;

import com.example.projectsale.user.controller.publicapi.UserController;
import com.example.projectsale.user.dto.request.LoginRequest;
import com.example.projectsale.user.service.UserService;
import com.example.projectsale.utils.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

public class testAll {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        // Khởi tạo các mock
        MockitoAnnotations.initMocks(this);

        // Khởi tạo request mẫu
        loginRequest = new LoginRequest("adminsupper@gmail.com", "123@bBbb");
    }

    @Test
    void testLogin_Success() {
        // Thiết lập mock trả về một response thành công
        Response mockResponse = Response.builder()
                .status(200)
                .message("Login successful")
                .code("200")
                .responseTime(System.currentTimeMillis())
                .build();

        // Mock phương thức login trong UserService
        when(userService.login(loginRequest)).thenReturn(ResponseEntity.ok(mockResponse));

        // Gọi phương thức controller
        ResponseEntity<Response> response = userController.login(loginRequest);

        // Kiểm tra kết quả trả về
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("Login successful");
        Assertions.assertThat(response.getBody().getStatus()).isEqualTo(200);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo("200");

        // Kiểm tra xem phương thức login đã được gọi đúng với tham số loginRequest
        verify(userService, times(1)).login(loginRequest);
    }

    @Test
    void testLogin_WrongPassword() {
        // Cập nhật loginRequest với sai mật khẩu
        LoginRequest wrongPasswordRequest = new LoginRequest("adminsupper@gmail.com", "wrongPassword");

        // Thiết lập mock trả về lỗi cho sai mật khẩu
        Response mockResponse = Response.builder()
                .status(401)
                .message("Invalid credentials")
                .code("401")
                .responseTime(System.currentTimeMillis())
                .build();

        when(userService.login(wrongPasswordRequest)).thenReturn(ResponseEntity.status(401).body(mockResponse));

        // Gọi phương thức controller
        ResponseEntity<Response> response = userController.login(wrongPasswordRequest);

        // Kiểm tra kết quả trả về
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("Invalid credentials");
        Assertions.assertThat(response.getBody().getStatus()).isEqualTo(401);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo("401");

        // Kiểm tra xem phương thức login đã được gọi đúng với tham số loginRequest
        verify(userService, times(1)).login(wrongPasswordRequest);
    }

    @Test
    void testLogin_WrongEmail() {
        // Cập nhật loginRequest với sai email
        LoginRequest wrongEmailRequest = new LoginRequest("wrongemail@gmail.com", "123@bBbb");

        // Thiết lập mock trả về lỗi cho sai email
        Response mockResponse = Response.builder()
                .status(404)
                .message("User not found")
                .code("404")
                .responseTime(System.currentTimeMillis())
                .build();

        when(userService.login(wrongEmailRequest)).thenReturn(ResponseEntity.status(404).body(mockResponse));

        // Gọi phương thức controller
        ResponseEntity<Response> response = userController.login(wrongEmailRequest);

        // Kiểm tra kết quả trả về
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("User not found");
        Assertions.assertThat(response.getBody().getStatus()).isEqualTo(404);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo("404");

        // Kiểm tra xem phương thức login đã được gọi đúng với tham số loginRequest
        verify(userService, times(1)).login(wrongEmailRequest);
    }

    @Test
    void testLogin_EmptyPassword() {
        // Cập nhật loginRequest với trống password
        LoginRequest emptyPasswordRequest = new LoginRequest("adminsupper@gmail.com", "");

        // Thiết lập mock trả về lỗi cho trường hợp trống mật khẩu
        Response mockResponse = Response.builder()
                .status(400)
                .message("Password cannot be empty")
                .code("400")
                .responseTime(System.currentTimeMillis())
                .build();

        when(userService.login(emptyPasswordRequest)).thenReturn(ResponseEntity.status(400).body(mockResponse));

        // Gọi phương thức controller
        ResponseEntity<Response> response = userController.login(emptyPasswordRequest);

        // Kiểm tra kết quả trả về
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("Password cannot be empty");
        Assertions.assertThat(response.getBody().getStatus()).isEqualTo(400);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo("400");

        // Kiểm tra xem phương thức login đã được gọi đúng với tham số loginRequest
        verify(userService, times(1)).login(emptyPasswordRequest);
    }

    @Test
    void testLogin_EmptyEmail() {
        // Cập nhật loginRequest với trống email
        LoginRequest emptyEmailRequest = new LoginRequest("", "123@bBbb");

        // Thiết lập mock trả về lỗi cho trường hợp trống email
        Response mockResponse = Response.builder()
                .status(400)
                .message("Email cannot be empty")
                .code("400")
                .responseTime(System.currentTimeMillis())
                .build();

        when(userService.login(emptyEmailRequest)).thenReturn(ResponseEntity.status(400).body(mockResponse));

        // Gọi phương thức controller
        ResponseEntity<Response> response = userController.login(emptyEmailRequest);

        // Kiểm tra kết quả trả về
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("Email cannot be empty");
        Assertions.assertThat(response.getBody().getStatus()).isEqualTo(400);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo("400");

        // Kiểm tra xem phương thức login đã được gọi đúng với tham số loginRequest
        verify(userService, times(1)).login(emptyEmailRequest);
    }
}
