package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class ListStringSection extends AbstractSection {
    private final List<String> lists;

    public ListStringSection(String... lists) {
        this(Arrays.asList(lists));
    }

    public ListStringSection(List<String> lists) {
        this.lists = lists;
    }

    public List<String> getLists() {
        return lists;
    }

    @Override
    public String toString() {
        return  lists.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListStringSection that = (ListStringSection) o;

        return lists.equals(that.lists);
    }

    @Override
    public int hashCode() {
        return lists.hashCode();
    }
}
