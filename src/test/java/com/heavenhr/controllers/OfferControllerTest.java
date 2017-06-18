package com.heavenhr.controllers;

import com.heavenhr.domain.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OfferControllerTest {
    @Autowired
    private
    OfferController offerController;

    @Test
    public void testCreate() throws Exception {
        Offer offer = new Offer();
        offer.setJobTitle("first job");
        offer.setStartDate(LocalDate.now());
        Offer offerResult = offerController.create(offer);
        assertTrue(offerResult != null);
        assertEquals(offer, offerResult);
    }

    @Test
    public void testGetAll() throws Exception {
        Offer offer = new Offer();
        offer.setJobTitle("first job2");
        offer.setStartDate(LocalDate.now());
        offerController.create(offer);
        List<Offer> offerResult = offerController.getAll();
        assertTrue(offerResult.contains(offer));
    }
}