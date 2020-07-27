package com.urise.webapp.model;

public class TextContentSection extends AbstractSection {
    private final String content;

    public TextContentSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextContentSection that = (TextContentSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
