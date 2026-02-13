package com.BookTracker.book_tracker.model;

/**
 * Defines the available literary genres in the application.
 * <p>
 * This enum is used to categorize books. Each constant has a {@code displayName}
 * which is a user-friendly string intended for UI rendering (e.g., in dropdown menus),
 * while the enum constant name is used for database persistence.
 */
public enum Genre {
    ADVENTURE("Adventure"),
    ANTHROPOLOGY("Anthropology"),
    BIOGRAPHY("Biography"),
    CLASSIC("Classic"),
    COMEDY("Comedy"),
    CONTEMPORARY("Contemporary"),
    CRIME("Crime"),
    DYSTOPIAN("Dystopian"),
    FANTASY("Fantasy"),
    FICTION("Fiction"),
    GOTHIC("Gothic Fiction"),
    HISTORICAL_FICTION("Historical Fiction"),
    HISTORY("History"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    NON_FICTION("Non-Fiction"),
    OTHER("Other"),
    ROMANCE("Romance"),
    SAPPHIC("Sapphic"),
    SCI_FI("Sci-Fi"),
    SELF_HELP("Self Help"),
    THRILLER("Thriller");

    /**
     * The human-readable name of the genre to be displayed in the user interface.
     */
    private final String displayName;

    /**
     * Constructor for the Genre enum.
     *
     * @param displayName The formatted string representation of the genre.
     */
    Genre(String displayName){
        this.displayName = displayName;
    }

    /**
     * Retrieves the display name of the genre.
     *
     * @return The user-friendly string (e.g., "Sci-Fi" instead of "SCI_FI").
     */
    public String getDisplayName() {
        return displayName;
    }
}
