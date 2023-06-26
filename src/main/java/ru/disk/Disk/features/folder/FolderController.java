package ru.disk.Disk.features.folder;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.disk.Disk.features.folder.dto.FolderDto;

@RestController
@RequestMapping("folders")
@Tag(name = "Folder")
public class FolderController {

    @Autowired
    private FolderService service;

    @GetMapping("content")
    public ResponseEntity<Page<FolderDto>> getAll(
            @RequestParam(name = "folder_id", required = false) int folderId,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(service.getAll(folderId, pageNumber, pageSize));
    }
}
