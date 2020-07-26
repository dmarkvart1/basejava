package com.urise.webapp.model;

import java.util.Objects;

public class WebSite {
    public String site;
    public String nameFirmaOrEdu;

    public WebSite(String site, String nameFirmaOrEdu) {
        Objects.requireNonNull(nameFirmaOrEdu, "webSite must not be null");
        this.site=site;
        this.nameFirmaOrEdu=nameFirmaOrEdu;
    }

    public String getSite() {
        return site;
    }

    public String getNameFirmaOrEdu() {
        return nameFirmaOrEdu;
    }

    @Override
    public String toString() {
        return "Site:" + nameFirmaOrEdu + ',' + site + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebSite webSite = (WebSite) o;

        if (!Objects.equals(site, webSite.site)) return false;
        return nameFirmaOrEdu.equals(webSite.nameFirmaOrEdu);
    }

    @Override
    public int hashCode() {
        int result = site != null ? site.hashCode() : 0;
        result = 31 * result + nameFirmaOrEdu.hashCode();
        return result;
    }
}
