package com.oda.service.admin;

import com.oda.dto.admin.AdminDto;
import com.oda.dto.admin.ReportUploadDto;
import com.oda.service.GenericCrudService;

public interface AdminService extends GenericCrudService<AdminDto,Integer> {

    ReportUploadDto uploadedReport(ReportUploadDto reportUploadDto);

}
