package com.bookstore.adapter.persistence.entity;

public enum Category {
    ROMANCE("Romance"),
    FICCAO("Ficção"),
    POESIA("Poesia"),
    BIOGRAFIA("Biografia"),
    DRAMA("Drama");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
