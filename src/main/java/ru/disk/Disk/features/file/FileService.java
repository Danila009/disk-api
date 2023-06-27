package ru.disk.Disk.features.file;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.disk.Disk.features.file.dto.FileDto;
import ru.disk.Disk.features.file.entity.FileEntity;
import ru.disk.Disk.features.folder.FolderRepository;
import ru.disk.Disk.features.folder.entity.FolderEntity;
import ru.disk.Disk.features.user.UserRepository;
import ru.disk.Disk.features.user.entity.UserEntity;
import ru.disk.Disk.utils.exceptions.NotFoundException;
import ru.disk.Disk.utils.repository.FileManager;

import javax.annotation.Resources;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileManager fileManager;

    public Page<FileDto> getAll(
            Long folderId,
            @Min(0) int pageNumber,
            @Min(1) @Max(100) int pageSize
    ) {
        Page<FileEntity> entities = fileRepository.findByFolderId(
                folderId,
                PageRequest.of(pageNumber, pageSize)
        );

        return entities.map(FileDto::new);
    }

    @SneakyThrows
    public FileDto add(
            Long folderId,
            Long userId,
            MultipartFile file
    ){
        FolderEntity folder = null;

        if(folderId != null){
            Optional<FolderEntity> optionalFolder = folderRepository.findById(folderId);

            if(optionalFolder.isPresent())
                folder = optionalFolder.get();
            else
                throw new NotFoundException("folder not found");
        }

        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) throw new NotFoundException("user not found");

        UserEntity userEntity = userOptional.get();

        String patch = fileManager.upload(file, "/resources/users/" + userEntity.getEmail() + "/files/");

        FileEntity fileEntity = new FileEntity(patch, userEntity, folder);

        return new FileDto(fileRepository.save(fileEntity));
    }

    @SneakyThrows
    public ResponseEntity<Resource> getFileResource(String filePatch) {
        return fileManager.get(filePatch);
    }
}
