package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationAbstractSection extends AbstractSection {
    private final List<Organization> organizations;

    public OrganizationAbstractSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationAbstractSection that = (OrganizationAbstractSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }
}


