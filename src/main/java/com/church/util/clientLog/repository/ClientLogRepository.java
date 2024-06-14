package com.church.util.clientLog.repository;

import com.church.util.clientLog.entity.ClientLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLogRepository extends JpaRepository<ClientLog, Long> {
}
