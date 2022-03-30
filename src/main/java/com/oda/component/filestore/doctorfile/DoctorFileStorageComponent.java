package com.oda.component.filestore.doctorfile;

import com.oda.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class DoctorFileStorageComponent {
    public static ResponseDto storeFile(MultipartFile multipartFile) throws IOException {
        //first make file dir
        String fileDir = System.getProperty("user.name")
                + File.separator + "Desktop" + File.separator + "ODA" + File.separator + "doctor";

        File directoryPath = new File(fileDir);

        if (!directoryPath.exists()) {
            directoryPath.mkdirs();
        } else {
            log.info("File already exists.");
        }

        //get details from multipartFile
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        assert ext != null;
        if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpeg")) {

            UUID uuid = UUID.randomUUID();

            String filePath = fileDir + File.separator + uuid + "_" + multipartFile.getOriginalFilename();

            File newFile = new File(filePath);

            multipartFile.transferTo(newFile);
            return ResponseDto.builder()
                    .status(true)
                    .message(filePath).build();
        } else {
            return ResponseDto.builder()
                    .status(false)
                    .message("Invalid extension").build();
        }
    }
    public String base64Encoded(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return "data:image/png;base64,"+ Base64.getEncoder().encodeToString(bytes);
    }

}
