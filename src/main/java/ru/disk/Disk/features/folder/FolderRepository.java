package ru.disk.Disk.features.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.disk.Disk.features.folder.entity.FolderEntity;

public interface FolderRepository extends JpaRepository<FolderEntity, Long> {

}
