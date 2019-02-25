package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {

    private List<OrganizationDetail> listOrganizationDetail;
    private final Link homePage;

    public Organization(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.homePage = new Link(name, url);
    }

    public void addOrganizationDetail(OrganizationDetail organizationDetail) {
        if (listOrganizationDetail == null) {
            listOrganizationDetail = new ArrayList<>();
        }
        this.listOrganizationDetail.add(organizationDetail);
    }

    @Override
    public String toString() {
        return "Organization{" +
                " homePage=" + homePage +
                ", listOrganizationDetail=" + listOrganizationDetail +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!listOrganizationDetail.equals(that.listOrganizationDetail)) return false;
        return homePage.equals(that.homePage);
    }

    @Override
    public int hashCode() {
        int result = listOrganizationDetail.hashCode();
        result = 31 * result + homePage.hashCode();
        return result;
    }
}
