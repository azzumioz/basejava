package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListAbstractSection extends AbstractSection {
    private final List<String> items;

    public ListAbstractSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ListAbstractSection that = (ListAbstractSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + items.hashCode();
        return result;
    }
}
