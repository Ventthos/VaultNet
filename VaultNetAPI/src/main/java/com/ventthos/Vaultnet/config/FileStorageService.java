package com.ventthos.Vaultnet.config;

import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path folder = Paths.get("uploads");

    public FileStorageService() throws IOException {
        if(Files.notExists(folder)){
            Files.createDirectory(folder);
        }
    }

    public String save(MultipartFile file, FileRoutes fileRoute) {
        try {
            //Un nombre único
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path targetPath = folder.resolve(fileRoute.getRoute() + filename);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return "uploads/"+fileRoute.getRoute() + filename;

        }
        catch(Exception e){
            throw new RuntimeException("Error al guardar archivo: " + e.getMessage());
        }
    }
}
