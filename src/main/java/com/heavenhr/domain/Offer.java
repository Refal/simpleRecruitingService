package com.heavenhr.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@Entity
public class Offer implements Serializable {
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<Application> applications = new ArrayList<>();
    @Id
    private String jobTitle;
    @NotNull
    private LocalDate startDate = LocalDate.now();

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void addApplication(Application application) {
        applications.add(application);
        application.setOffer(this.getJobTitle());
    }

    public void removeApplication(Application application) {
        applications.remove(application);
        application.setOffer(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (!jobTitle.equals(offer.jobTitle)) return false;
        return startDate.equals(offer.startDate);
    }

    @Override
    public int hashCode() {
        int result = jobTitle.hashCode();
        result = 31 * result + startDate.hashCode();
        return result;
    }
}
