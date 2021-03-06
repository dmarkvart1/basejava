package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListStringSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> lists;

    public ListStringSection() {
    }

    public ListStringSection(String... lists) {
        this(Arrays.asList(lists));
    }

    public ListStringSection(List<String> lists) {
        Objects.requireNonNull(lists, "lists must not be null");
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
        if (!(o instanceof ListStringSection)) return false;
        ListStringSection section = (ListStringSection) o;
        return lists.equals(section.lists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lists);
    }
}
