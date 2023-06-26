package ru.disk.Disk.features.user;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.disk.Disk.features.user.dto.RegisterDto;
import ru.disk.Disk.features.user.entity.UserEntity;
import ru.disk.Disk.features.user.entity.UserRole;
import ru.disk.Disk.utils.exceptions.AlreadyExistException;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    public void register(RegisterDto dto) {

        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new AlreadyExistException("user.email.already_exist");
        }

        UserEntity entity = new UserEntity(dto);

        Set<UserRole> roles = new HashSet<>();

        roles.add(UserRole.BASE_USER);

        entity.setRoles(roles);

        userRepository.save(entity);
    }
}
