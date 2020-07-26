package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class ExperienceEducationSection extends AbstractSection{
    private final WebSite webSite;

    private final YearMonth dateFrom;
    private final YearMonth finaldateTo;
    private final String vacanOrEdu;
    private final String description;

    public ExperienceEducationSection(String site, String nameFirmaOrEdu, YearMonth dateFrom, YearMonth finaldateTo, String vacanOrEdu, String description) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(finaldateTo, "finaldateTo must not be null");
        Objects.requireNonNull(vacanOrEdu, "vacanOrEdu must not be null");
        Objects.requireNonNull(description, "description must not be null");

        this.webSite = new WebSite(site, nameFirmaOrEdu);
        this.dateFrom = dateFrom;
        this.finaldateTo = finaldateTo;
        this.vacanOrEdu = vacanOrEdu;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceEducationSection that = (ExperienceEducationSection) o;

        if (!webSite.equals(that.webSite)) return false;
        if (!dateFrom.equals(that.dateFrom)) return false;
        if (!finaldateTo.equals(that.finaldateTo)) return false;
        if (!vacanOrEdu.equals(that.vacanOrEdu)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = webSite.hashCode();
        result = 31 * result + dateFrom.hashCode();
        result = 31 * result + finaldateTo.hashCode();
        result = 31 * result + vacanOrEdu.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FirmsOrEducations{" +
                "NameFirmaOrEducation=" + webSite.nameFirmaOrEdu +
                ", WebSite=" + webSite.site +
                ", DateStart=" + dateFrom +
                ", DateEnd=" + finaldateTo +
                ", VacationOrEducation='" + vacanOrEdu + '\'' +
                ", Description='" + description + '\'' +
                '}';
    }
}
