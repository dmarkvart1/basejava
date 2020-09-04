package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class WebSite implements Serializable {
    private static final long serialVersionUID = 1L;

    public String url;
    public String nameOrganisation;

    public WebSite() {
    }

    public WebSite(String url, String nameOrganisation) {
        Objects.requireNonNull(nameOrganisation, "nameOrganisation must not be null");
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
        return "Organisation:" + nameOrganisation + ',' + url + ')';
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
