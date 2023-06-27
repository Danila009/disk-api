package ru.disk.Disk.features.file;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.disk.Disk.features.file.dto.FileDto;
import ru.disk.Disk.features.user.UserService;
import ru.disk.Disk.features.user.dto.JwtAuthentication;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("files")
@Tag(name = "File")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<FileDto>> getAll(
            @RequestParam(name = "folder_id", required = false) Long folderId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(fileService.getAll(folderId, pageNumber, pageSize));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('BASE_USER')")
    public ResponseEntity<FileDto> add(
            @RequestParam(name = "folder_id", required = false) Long folderId,
            @RequestParam(name = "file") MultipartFile file,
            HttpServletRequest request
    ) {
        JwtAuthentication user = userService.getAuthInfo(request);

        return ResponseEntity.ok(fileService.add(folderId, user.getId(), file));
    }

    @GetMapping("resource")
    public ResponseEntity<Resource> getFileResource(
            @RequestParam(name = "patch") String filePatch
    ) {
        return fileService.getFileResource(filePatch);
    }
}
