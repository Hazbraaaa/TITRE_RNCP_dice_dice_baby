package com.dicedicebaby.model;

public class Player {

    //region Attributes
    private final Long id;
    private final String username;
    private final Boolean isGuest;
    private final Long accountId;
    //endregion

    //region Constructor
    public Player(Long id, String username, Boolean isguest, Long accountId) {
        this.id = id;
        this.username = username;
        this.isGuest = isguest;
        this.accountId = accountId;
    }
    //endregion

    //region Methods
    public boolean canAccessProfile() {
        return !isGuest && accountId != null;
    }
    //endregion

    //region Getters & Setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Boolean getIsGuest() { return isGuest; }
    public Long getAccountId() { return accountId; }
    //endregion
}
