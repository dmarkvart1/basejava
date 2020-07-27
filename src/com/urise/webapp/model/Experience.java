package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience extends AbstractSection{
    private final WebSite webSite;

    private final YearMonth from;
    private final YearMonth to;
    private final String position;
    private final String description;

    public Experience(String url, String organisation, YearMonth from, YearMonth to, String position, String description) {

        this.webSite = new WebSite(url, organisation);
        this.from = from;
        this.to = to;
        this.position = position;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!webSite.equals(that.webSite)) return false;
        if (!from.equals(that.from)) return false;
        if (!to.equals(that.to)) return false;
        if (!position.equals(that.position)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = webSite.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "NameOrganisation=" + webSite.nameOrganisation +
                ", WebSite=" + webSite.url +
                ", DateStart=" + from +
                ", DateEnd=" + to +
                ", VacationOrEducation='" + position + '\'' +
                ", Description='" + description + '\'' +
                '}';
    }
}
