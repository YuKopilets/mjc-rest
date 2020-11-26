package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateFilterRepository;
import com.epam.esm.util.GiftCertificateQuery;
import com.epam.esm.util.QueryConditionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type implementation of Gift certificate filter repository.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see GiftCertificateFilterRepository
 */
@Repository
public class GiftCertificateFilterRepositoryImpl implements GiftCertificateFilterRepository {
    private static final String SELECT_ALL_GIFT_CERTIFICATE_IDS = "SELECT gc.id FROM GiftCertificate gc JOIN gc.tags t";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<GiftCertificate> findAllByQueryParams(GiftCertificateQuery giftCertificateQuery, Pageable pageable) {
        String condition = QueryConditionUtils.generateConditionByQueryParams(giftCertificateQuery);
        String query = SELECT_ALL_GIFT_CERTIFICATE_IDS + condition;
        int firstResult = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        // This cast is correct, because the list we're creating is of the same
        // type as the one passed after operation with entity manager.
        // We're selecting the list of gift certificate identifiers.
        @SuppressWarnings("unchecked") List<Long> certificateIds  = entityManager.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<GiftCertificate> giftCertificates = certificateIds.stream()
                .distinct()
                .map(id -> entityManager.find(GiftCertificate.class, id))
                .collect(Collectors.toList());
        return new PageImpl<>(giftCertificates);
    }
}
