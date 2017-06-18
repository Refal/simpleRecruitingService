package com.heavenhr.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by egor on 17.06.17.
 */
@SuppressWarnings("ALL")
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"offer", "email"})
})
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
    private String resume;
    @NotNull
    @Enumerated(EnumType.STRING)
    private APPLICATION_STATUS status = APPLICATION_STATUS.APPLIED;

    private String offer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }


    public APPLICATION_STATUS getStatus() {
        return status;
    }

    public void setStatus(APPLICATION_STATUS status) {
        this.status = status;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (!email.equals(that.email)) return false;
        if (resume != null ? !resume.equals(that.resume) : that.resume != null) return false;
        if (status != that.status) return false;
        return offer != null ? offer.equals(that.offer) : that.offer == null;
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + (resume != null ? resume.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (offer != null ? offer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", resume='" + resume + '\'' +
                ", status=" + status +
                ", offer='" + offer + '\'' +
                '}';
    }

    public enum APPLICATION_STATUS {
        APPLIED, INVITED, REJECTED, HIRED
    }
}
