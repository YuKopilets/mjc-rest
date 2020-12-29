package com.epam.esm.persistence.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Gift certificate repository is expansion of
 * JpaRepository for additional operations with <i>gift_Certificate</i> table
 * and <i>gift_Certificate_has_tag</i> associated table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JpaRepository
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
}
