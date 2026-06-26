package com.dicedicebaby.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class PlayerEntity extends AuditableEntity {

  // region Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String playerUsername;

  @Column private Boolean isGuest = false;

  @OneToOne
  @JoinColumn(name = "account_id")
  private AccountEntity account;

  @Column private int playerNumber;

  @Column private int score;

  @Column private int remainingChips = 6;

  @ManyToOne
  @JoinColumn(name = "game_id")
  private GameEntity game;

  @Column private String currentJwt;

  // endregion

  // region Constructor
  public PlayerEntity() {}

  // endregion

  // region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPlayerUsername() {
    return playerUsername;
  }

  public void setPlayerUsername(String playerUsername) {
    this.playerUsername = playerUsername;
  }

  public Boolean getIsGuest() {
    return isGuest;
  }

  public void setIsGuest(Boolean isGuest) {
    this.isGuest = isGuest;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public void setAccount(AccountEntity account) {
    this.account = account;
  }

  public int getPlayerNumber() {
    return playerNumber;
  }

  public void setPlayerNumber(int playerNumber) {
    this.playerNumber = playerNumber;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getRemainingChips() {
    return remainingChips;
  }

  public void setRemainingChips(int remainingChips) {
    this.remainingChips = remainingChips;
  }

  public GameEntity getGame() {
    return game;
  }

  public void setGame(GameEntity game) {
    this.game = game;
  }

  public String getCurrentJwt() {
    return currentJwt;
  }

  public void setCurrentJwt(String currentJwt) {
    this.currentJwt = currentJwt;
  }
  // endregion
}
