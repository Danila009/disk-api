package ru.disk.Disk.features.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.disk.Disk.features.folder.dto.FolderDto;
import ru.disk.Disk.features.folder.entity.FolderEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    public Page<FolderDto> getAll(
            @Min(1) int folderId,
            @Min(0) int pageNumber,
            @Min(1) @Max(100) int pageSize
    ) {
        Page<FolderEntity> entities = folderRepository.findAll(
                PageRequest.of(pageNumber, pageSize)
        );

        return entities.map(FolderDto::new);
    }
}
