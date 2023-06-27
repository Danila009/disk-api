package ru.disk.Disk.features.folder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.disk.Disk.features.file.FileService;
import ru.disk.Disk.features.file.dto.FileDto;
import ru.disk.Disk.features.folder.dto.FolderDto;
import ru.disk.Disk.features.user.UserService;
import ru.disk.Disk.features.user.dto.JwtAuthentication;
import ru.disk.Disk.features.user.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("folders")
@Tag(name = "Folder")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @GetMapping("content/folders")
    public ResponseEntity<Page<FolderDto>> getAllContentFolders(
            @RequestParam(name = "folder_id", required = false) Long folderId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(folderService.getAll(folderId, pageNumber, pageSize));
    }

    @GetMapping("content/files")
    public ResponseEntity<Page<FileDto>> getAllContentFiles(
            @RequestParam(name = "folder_id", required = false) Long folderId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(fileService.getAll(folderId, pageNumber, pageSize));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BASE_USER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<FolderDto> add(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "folder_id", required = false) Long folderId,
            HttpServletRequest request
    ){
        JwtAuthentication user = userService.getAuthInfo(request);

        return ResponseEntity.ok(folderService.add(name, folderId, user.getId()));
    }
}
