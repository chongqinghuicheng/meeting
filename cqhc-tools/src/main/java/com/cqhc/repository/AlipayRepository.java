package com.cqhc.repository;

import com.cqhc.domain.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jie
 * @date 20com.cqhc8-12-31
 */
public interface AlipayRepository extends JpaRepository<AlipayConfig,Long> {
}
