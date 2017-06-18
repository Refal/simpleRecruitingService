package com.heavenhr.repository;

import com.heavenhr.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by egor on 17.06.17.
 */

@SuppressWarnings("ALL")
@Transactional
public interface OfferRepository extends JpaRepository<Offer, String> {

    long countBy(String jobTitle);
}
