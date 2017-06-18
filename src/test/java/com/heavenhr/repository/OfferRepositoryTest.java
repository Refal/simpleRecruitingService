package com.heavenhr.repository;

import com.heavenhr.domain.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OfferRepositoryTest {

    @Autowired
    private
    OfferRepository offerRepository;


    @Test
    public void testOfferSave() throws Exception {
        Offer offer = new Offer();
        offer.setJobTitle("job1");
        offerRepository.save(offer);

        Offer saved = offerRepository.findOne(offer.getJobTitle());
        assertEquals(offer, saved);

    }
}