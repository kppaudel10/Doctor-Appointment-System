package com.oda.repo.admin;

import com.oda.model.admin.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportUploadRepo extends JpaRepository<Report,Integer> {
}
