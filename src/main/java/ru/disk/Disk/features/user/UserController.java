package ru.disk.Disk.features.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.disk.Disk.features.user.dto.RegisterDto;

@RestController
@RequestMapping("users")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
        userService.register(dto);

        return ResponseEntity.ok("success");
    }
}
