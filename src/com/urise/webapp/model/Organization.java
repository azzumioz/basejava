package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {

    private List<OrganizationDetail> listOrganizationDetail;
    private final Link homePage;
    private final String name;

    public Organization(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
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
                ", name='" + name + '\'' +
                ", listOrganizationDetail=" + listOrganizationDetail +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!listOrganizationDetail.equals(that.listOrganizationDetail)) return false;
        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = listOrganizationDetail.hashCode();
        result = 31 * result + (homePage != null ? homePage.hashCode() : 0);
        result = 31 * result + name.hashCode();
        return result;
    }
}
