package com.oda.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportUploadDto {
    private Integer id;

    private Integer appointmentId;

    private MultipartFile multipartFileReport;

    private String reportUrl;

}
