package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<Organization> organizationList;

    public OrganizationSection(Organization... organizationList) {
        this(Arrays.asList(organizationList));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "experiences must not be null");
        this.organizationList = organizations;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizationList.equals(that.organizationList);

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

