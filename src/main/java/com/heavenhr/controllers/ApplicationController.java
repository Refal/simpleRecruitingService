package com.heavenhr.controllers;

import com.heavenhr.domain.Application;
import com.heavenhr.domain.Offer;
import com.heavenhr.repository.ApplicationRepository;
import com.heavenhr.repository.OfferRepository;
import com.heavenhr.services.ApplicationStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/applications")
@Transactional
public class ApplicationController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private
    ApplicationStatusService statusService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Application> getAll() {
        return applicationRepository.findAll();
    }


    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Application create(@RequestBody Application application) {
        Offer offer = offerRepository.findOne(application.getOffer());
        if (offer == null) {
            throw new IllegalArgumentException("No offer:" + application.getOffer());
        }
        offer.addApplication(application);
        offerRepository.save(offer);
        statusService.notify(application);
        Optional<Application> first = offer.getApplications().stream().filter(app -> app.equals(application)).findFirst();
        return first.orElseThrow(() -> new IllegalArgumentException("No application:" + application.toString()));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void delete(@PathVariable Long id) {
        applicationRepository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Application update(@RequestBody Application application) {
        Application update = applicationRepository.findOneByEmailAndOffer(application.getEmail(), application.getOffer());
        if (update == null) {
            throw new IllegalArgumentException("There is no application :" + application.toString());
        }
        update.setStatus(application.getStatus());
        Application result = applicationRepository.save(update);
        statusService.notify(result);
        return result;
    }

    @RequestMapping(path = "/list", params = "jobTitle")
    public List<Application> getApplicationsByJobTitle(String jobTitle) {
        List<Application> applications = applicationRepository.findByOffer(jobTitle);
        return applications;
    }

    @RequestMapping(path = "/get", params = "jobTitle")
    public Application getApplicationByJobTitle(String jobTitle) {
        Application application = applicationRepository.findOneByOffer(jobTitle);
        return application;
    }


    @RequestMapping(path = "/count", params = "jobTitle")
    public long getApplicationsCountByJobTitle(String jobTitle) {
        return applicationRepository.countByOffer(jobTitle);
    }
}
