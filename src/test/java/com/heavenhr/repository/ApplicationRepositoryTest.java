package com.heavenhr.repository;

import com.heavenhr.domain.Application;
import com.heavenhr.domain.Offer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ApplicationRepositoryTest {

    @Autowired
    private
    ApplicationRepository applicationRepository;

    @Autowired
    private
    OfferRepository offerRepository;

    private Offer offer;

    @Before
    public void init() {
        offer = new Offer();
        offer.setJobTitle("test offer");
        offerRepository.save(offer);
    }


    @Test
    public void testOfferSave() throws Exception {
        Application application = new Application();
        application.setEmail("test@gmail.com");
        application.setOffer("test offer");
        Application save = applicationRepository.save(application);

        List<Application> all = applicationRepository.findAll();
        assertTrue(all.contains(save));
    }


    @Test
    public void testFindByOffer() throws Exception {
        Application application = new Application();
        application.setEmail("test2@gmail.com");
        offer.addApplication(application);
        offerRepository.save(offer);
        List<Application> all = applicationRepository.findByOffer(offer.getJobTitle());
        Optional<Application> applicationOptional = all.stream().filter(application1 -> application.getEmail().equals(application1.getEmail())).findAny();
        assertTrue(applicationOptional.isPresent());
        assertEquals(application.getEmail(), applicationOptional.get().getEmail());
    }


    @Test(expected = Exception.class)
    public void testInvalidEmail() throws Exception {
        Application application = new Application();
        application.setEmail("test2");
        offer.addApplication(application);
        offerRepository.save(offer);
        fail("Invalid email passed");
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testApplyTwiceToOneOffer() throws Exception {
        Application application = new Application();
        application.setEmail("test2@gmail.com");
        offer.addApplication(application);
        offerRepository.save(offer);

        Application application2 = new Application();
        application2.setEmail("test2@gmail.com");
        offer.addApplication(application2);
        offerRepository.save(offer);

        fail("Added application with same email");
    }
}