package com.heavenhr.controllers;

import com.heavenhr.domain.Application;
import com.heavenhr.domain.Offer;
import com.heavenhr.services.ApplicationStatusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ApplicationControllerTest {
    @Autowired
    private ApplicationController applicationController;

    @SpyBean
    private ApplicationStatusService statusService;

    @Autowired
    private OfferController offerController;

    private Offer offer;

    @Before
    public void init() {
        offer = new Offer();
        offer.setJobTitle("First job");
        offer.setStartDate(LocalDate.now());
        offerController.create(offer);
    }

    @Test
    public void getAll() throws Exception {
        assertTrue(applicationController.getAll().isEmpty());
    }

    @Test
    public void testCreate() throws Exception {
        Application application = new Application();
        application.setEmail("mymail@gmail.com");
        application.setOffer(offer.getJobTitle());
        applicationController.create(application);
        verify(statusService).notify(eq(application));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOfferCreate() throws Exception {
        Application application = new Application();
        application.setEmail("mymail@gmail.com");
        application.setOffer("Random string");
        applicationController.create(application);
        fail("Application with invalid offer created");
    }

    @Test
    public void testDelete() throws Exception {
        Application application = createApplication(offer, "mymail@gmail.com");
        List<Application> applicationsByOffer = applicationController.getApplicationsByJobTitle(application.getOffer());
        assertTrue(applicationsByOffer.contains(application));

        applicationController.delete(application.getId());
        applicationsByOffer = applicationController.getApplicationsByJobTitle(application.getOffer());
        assertFalse(applicationsByOffer.contains(application));
    }

    @Test
    public void testUpdateApplication() {
        Application application = createApplication(offer, "mymail@gmail.com");
        application.setStatus(Application.APPLICATION_STATUS.INVITED);
        Application update = applicationController.update(application);
        verify(statusService).notify(eq(application));
        verify(statusService).notify(eq(update));
    }

    @Test
    public void getApplicationByOffer() throws Exception {
        Application application = new Application();
        application.setEmail("mymail@gmail.com");
        application.setOffer(offer.getJobTitle());
        applicationController.create(application);
        Application applicationByOffer = applicationController.getApplicationByJobTitle(offer.getJobTitle());
        assertTrue(applicationByOffer != null);
    }

    @Test
    public void getApplicationsByOffer() throws Exception {
        Application application = new Application();
        application.setEmail("mymail@gmail.com");
        application.setOffer(offer.getJobTitle());
        applicationController.create(application);

        Application application2 = new Application();
        application2.setEmail("hismail@gmail.com");
        application2.setOffer(offer.getJobTitle());
        applicationController.create(application2);

        List<Application> applications = applicationController.getApplicationsByJobTitle(offer.getJobTitle());
        assertTrue(applications.contains(application));
        assertTrue(applications.contains(application2));
    }

    @Test
    public void testEmptyApplicationCount() throws Exception {
        Offer offer = new Offer();
        offer.setJobTitle("ApplicationCount job");
        offerController.create(offer);

        long count = applicationController.getApplicationsCountByJobTitle(offer.getJobTitle());
        assertEquals(0, count);
    }


    @Test
    public void testApplicationCount() throws Exception {
        Offer offer = new Offer();
        offer.setJobTitle("ApplicationCount2 job");
        offerController.create(offer);

        createApplication(offer, "mymail@gmail.com");
        createApplication(offer, "hismail@gmail.com");

        long count = applicationController.getApplicationsCountByJobTitle(offer.getJobTitle());
        assertEquals(2, count);
    }


    private Application createApplication(Offer offer, String email) {
        Application application = new Application();
        application.setOffer(offer.getJobTitle());
        application.setEmail(email);
        return applicationController.create(application);
    }
}