package ru.disk.Disk.features.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.disk.Disk.features.user.dto.JwtRequestDto;
import ru.disk.Disk.features.user.dto.JwtResponseDto;
import ru.disk.Disk.features.user.dto.RefreshJwtRequestDto;
import ru.disk.Disk.features.user.dto.RegisterDto;

@RestController
@RequestMapping("users")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    @Operation(summary = "register user")
    public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
        userService.register(dto);

        return ResponseEntity.ok("success");
    }

    @PostMapping("login")
    @Operation(summary = "login user")
    public JwtResponseDto login(@RequestBody JwtRequestDto dto) {
        return userService.login(dto);
    }

    @PostMapping("token")
    @Operation(summary = "get new access token")
    public JwtResponseDto getNewAccessToken(@RequestBody RefreshJwtRequestDto request) {
        return userService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("refresh")
    @Operation(summary = "get new refresh token")
    public JwtResponseDto getNewRefreshToken(@RequestBody RefreshJwtRequestDto request) {
        return userService.refresh(request.getRefreshToken());
    }
}
