package com.urise.webapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final WebSite webSite;
    private List<Position> positions;

    public Organization(String url, String name, Position... positions) {
        this(new WebSite(url, name), Arrays.asList(positions));
    }

    public Organization(WebSite url, List<Position> positions) {
        this.webSite = url;
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(webSite, that.webSite) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webSite, positions);
    }

    @Override
    public String toString() {
        return "Organization(" + webSite + "," + positions + ')';
    }

    public static class Position implements Serializable {
        private final YearMonth from;
        private final YearMonth to;
        private final String title;
        private final String description;


        public Position(YearMonth from, YearMonth to, String title, String description) {
            Objects.requireNonNull(from, "startDate must not be null");
            Objects.requireNonNull(to, "endDate must not be null");
            Objects.requireNonNull(title, "position must not be null");
            this.from = from;
            this.to = to;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position pos = (Position) o;
            return Objects.equals(from, pos.from) &&
                    Objects.equals(to, pos.to) &&
                    Objects.equals(title, pos.title) &&
                    Objects.equals(description, pos.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, title, description);
        }

        @Override
        public String toString() {
            return "Position(" + from + ',' + to + ',' + title + ',' + description + ')';
        }
    }
}
