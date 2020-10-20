package com.urise.webapp.model;

import java.util.Objects;

public class TextContentSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private  String content;

    public TextContentSection() {
    }

    public TextContentSection(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextContentSection)) return false;
        TextContentSection that = (TextContentSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content;
    }
}
