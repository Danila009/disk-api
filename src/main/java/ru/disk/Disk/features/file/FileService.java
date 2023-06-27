package ru.disk.Disk.features.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.disk.Disk.features.file.dto.FileDto;
import ru.disk.Disk.features.file.entity.FileEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

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
}
