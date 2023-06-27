package ru.disk.Disk.features.file.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.disk.Disk.features.folder.entity.FolderEntity;
import ru.disk.Disk.features.user.entity.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Schema
@Entity(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String path;

    @Column(nullable = false)
    public String expansion;

    @Column(nullable = false)
    public Integer size;

    @Column(nullable = false)
    public Date dateUpdate;

    @Column(nullable = false)
    public Date dateCreate;

    @Column(nullable = false)
    public Boolean isPublic;

    @ManyToOne(fetch = FetchType.EAGER)
    public UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    public FolderEntity folder = null;
}
