package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExperienceSection extends AbstractSection {
        private final List<Experience> experienceList;

    public ExperienceSection(Experience... experienceList) {
        this(Arrays.asList(experienceList));
    }

        public ExperienceSection(List<Experience> experiences) {
            Objects.requireNonNull(experiences, "experiences must not be null");
            this.experienceList = experiences;
        }

        public List<Experience> getExperienceList() {
            return experienceList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ExperienceSection that = (ExperienceSection) o;

            return experienceList.equals(that.experienceList);

        }

        @Override
        public int hashCode() {
            return experienceList.hashCode();
        }

        @Override
        public String toString() {
            return experienceList.toString();
        }
    }

