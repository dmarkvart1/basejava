package com.urise.webapp.model;

import java.util.Objects;

public class WebSite {
    public String url;
    public String nameOrganisation;

    public WebSite(String url, String nameOrganisation) {
        this.url = url;
        this.nameOrganisation = nameOrganisation;
    }

    public String getUrl() {
        return url;
    }

    public String getNameOrganisation() {
        return nameOrganisation;
    }

    @Override
    public String toString() {
        return "Site:" + nameOrganisation + ',' + url + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebSite webSite = (WebSite) o;

        if (!Objects.equals(url, webSite.url)) return false;
        return nameOrganisation.equals(webSite.nameOrganisation);
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + nameOrganisation.hashCode();
        return result;
    }
}
