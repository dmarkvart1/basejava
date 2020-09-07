package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private  List<Organization> organizationList;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizationList) {
        this(Arrays.asList(organizationList));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "experiences must not be null");
        this.organizationList = organizations;
    }

    public OrganizationSection(String readUTF) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;

        OrganizationSection that = (OrganizationSection) o;

        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public int hashCode() {
        return organizationList.hashCode();
    }

    @Override
    public String toString() {
        return organizationList.toString();
    }
}

