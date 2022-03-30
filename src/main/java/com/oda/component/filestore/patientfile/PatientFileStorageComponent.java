package com.oda.component.filestore.patientfile;

import com.oda.dto.response.ResponseDto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

public class PatientFileStorageComponent {
    public static ResponseDto storeFile(MultipartFile multipartFile) throws IOException {
        String fileDirectory =System.getProperty("user.name")+
                File.separator+"ODA"+File.separator+"patient";

        File directoryPath = new File(fileDirectory);
        if (!directoryPath.exists()){
            boolean mkdirs = directoryPath.mkdirs();
        }else {

        }
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        assert ext != null;
        if (ext.equalsIgnoreCase("jpg") ||
                ext.equalsIgnoreCase("png") ||
                ext.equalsIgnoreCase("jpeg")) {
            UUID uuid = UUID.randomUUID();
            String filePath = fileDirectory + File.separator +uuid+"-"+multipartFile.getOriginalFilename();
            File newFile = new File(filePath);
            multipartFile.transferTo(newFile);
            return ResponseDto.builder()
                    .status(true)
                    .message(filePath)
                    .build();
        } else {
            return ResponseDto.builder()
                    .status(false)
                    .message("Invalid extension")
                    .build();
        }
    }


    public String base64Encoded(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }
}
