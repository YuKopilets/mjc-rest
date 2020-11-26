package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The interface Tag repository is expansion of CrudDao for additional
 * operations with <i>tag</i> table and <i>gift_Certificate_has_tag</i>
 * associated table.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see JpaRepository
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Find most widely used tag of a user with the highest cost of all orders.
     *
     * @return the most widely used tag
     */
    @Query(
            value = "SELECT t.id, t.name FROM user_order o " +
                    "INNER JOIN order_has_gift_certificate ogc ON o.id = ogc.order_id " +
                    "AND o.user_account_id = (SELECT user_account_id FROM user_order o " +
                    "GROUP BY o.user_account_id " +
                    "            ORDER BY sum(cost) DESC " +
                    "            LIMIT 1) " +
                    "INNER JOIN gift_certificate gc ON ogc.gift_certificate_id = gc.id " +
                    "INNER JOIN gift_certificate_has_tag gct ON gc.id = gct.gift_certificate_id " +
                    "INNER JOIN tag t ON gct.tag_id = t.id " +
                    "GROUP BY t.id " +
                    "    ORDER BY count(t.id) DESC " +
                    "LIMIT 1",
            nativeQuery = true
    )
    Tag findMostWidelyUsedTag();

    /**
     * Save tag to gift certificate. Insert single record to
     * <i>gift_Certificate_has_tag</i> table.
     * Create operation (CRUD).
     *
     * @param giftCertificateId the gift certificate id
     * @param tagId             the tag id
     */
    @Query(
            value = "INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (?,?)",
            nativeQuery = true
    )
    void saveToGiftCertificate(Long giftCertificateId, Long tagId);

    /**
     * Delete records in <i>gift_certificate_has_tag</i> by tag id.
     *
     * @param id the id
     */
    @Query(value = "DELETE FROM gift_certificate_has_tag WHERE tag_id = ?", nativeQuery = true)
    void deleteGiftCertificatesTag(Long id);
}
