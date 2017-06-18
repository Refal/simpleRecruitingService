package com.heavenhr.controllers;

import com.heavenhr.domain.Offer;
import com.heavenhr.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/offers")
public class OfferController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OfferController.class);
    @Autowired
    private
    OfferRepository offerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Offer create(@RequestBody Offer offer) {
        return offerRepository.save(offer);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void delete(@PathVariable String id) {
        offerRepository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public Offer update(@PathVariable String id, @RequestBody Offer offer) {
        Offer update = offerRepository.findOne(id);
        if (update == null) {
            throw new IllegalArgumentException("There is no offer:" + id);
        }
        update.setStartDate(offer.getStartDate());
        return offerRepository.save(update);
    }
}
