package ru.disk.Disk.utils.repository;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileRepositoryImpl implements FileRepository {

    @Override
    public ResponseEntity<Resource> get(String filePath) throws IOException {
        File file = new File(filePath);

        Path path = Paths.get(filePath);

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                .body(resource);
    }

    @Override
    public String upload(MultipartFile file, String folderName) throws IOException {
        return upload(List.of(file), folderName).get(0);
    }

    @Override
    public List<String> upload(List<MultipartFile> files, String folderName) throws IOException {
        File folder = new File(folderName);
        ArrayList<String> filesPath = new ArrayList<>();

        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (MultipartFile file: files) {
            if (file.isEmpty()) {
                continue;
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + file.getOriginalFilename());
            Files.write(path, bytes);

            filesPath.add(folderName + file.getOriginalFilename());
        }

        return filesPath;
    }

    @Override
    public Boolean delete(String filePath) {
        try {
            File file = new File(filePath);

            if(!file.exists()) return false;

            return file.delete();
        }catch (Exception ex){
            return false;
        }
    }
}
