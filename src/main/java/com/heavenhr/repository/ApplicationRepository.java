package com.heavenhr.repository;

import com.heavenhr.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by egor on 17.06.17.
 */

@SuppressWarnings("ALL")
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByOffer(String offer);

    Application findOneByOffer(String offer);

    Application findOneByEmailAndOffer(String email, String offer);

    long countByOffer(String offer);
}
