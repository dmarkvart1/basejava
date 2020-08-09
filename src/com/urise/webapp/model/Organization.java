package com.urise.webapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final WebSite webSite;
    private List<Position> positions = new ArrayList<>();

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
        private final String position;
        private final String description;

//        public Position(YearMonth from, YearMonth to, String position, String description) {
//            this(this.from=from, this.position=position, this.description=description);
//        }

        public Position(YearMonth from, YearMonth to, String position, String description) {
            Objects.requireNonNull(from, "startDate must not be null");
            Objects.requireNonNull(to, "endDate must not be null");
            Objects.requireNonNull(position, "position must not be null");
            this.from = from;
            this.to = to;
            this.position = position;
            this.description = description;
        }

        public YearMonth getStartDate() {
            return from;
        }

        public YearMonth getEndDate() {
            return to;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(from, position.from) &&
                    Objects.equals(to, position.to) &&
                    Objects.equals(position, position.position) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, position, description);
        }

        @Override
        public String toString() {
            return "Position(" + from + ',' + to + ',' + position + ',' + description + ')';
        }
    }
}
