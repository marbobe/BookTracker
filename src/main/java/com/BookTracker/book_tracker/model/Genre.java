package com.BookTracker.book_tracker.model;

public enum Genre {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    FANTASY("Fantasy"),
    SCI_FI("Sci-Fi"),
    MYSTERY("Mystery"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    SELF_HELP("Self Help"),
    OTHER("Other"),
    ADVENTURE("Adventure"),
    CLASSIC("Classic"),
    CONTEMPORARY("Contemporary"),
    ANTHROPOLOGY("Anthropology"),
    HISTORICAL_FICTION("Historical Fiction"),
    GOTHIC("Gothic Fiction"),
    DYSTOPIAN("Dystopian"),
    THRILLER("Thriller");

    private final String displayName;

    Genre(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
