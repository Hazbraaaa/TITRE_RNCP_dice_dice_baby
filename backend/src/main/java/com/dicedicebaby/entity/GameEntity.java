package com.dicedicebaby.entity;

import com.dicedicebaby.enums.GameState;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "games")
public class GameEntity extends AuditableEntity {
  // region Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "state", nullable = false)
  private GameState state = GameState.IN_PROGRESS;

  @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
  private BoardEntity board;

  @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
  private DiceSetEntity diceSet;

  @ManyToOne
  @JoinColumn(name = "current_player_id")
  private PlayerEntity currentPlayer;

  @Column(nullable = false)
  private int rollsLeft = 3;

  @Column private int roundNumber = 1;

  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
  private List<PlayerEntity> players;

  // endregion

  // region Constructor
  public GameEntity() {}

  // endregion

  // region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GameState getState() {
    return state;
  }

  public void setState(GameState state) {
    this.state = state;
  }

  public BoardEntity getBoard() {
    return board;
  }

  public void setBoard(BoardEntity board) {
    this.board = board;
  }

  public PlayerEntity getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(PlayerEntity currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public DiceSetEntity getDiceSet() {
    return diceSet;
  }

  public void setDiceSet(DiceSetEntity diceSet) {
    this.diceSet = diceSet;
  }

  public List<PlayerEntity> getPlayers() {
    return players;
  }

  public void setPlayers(List<PlayerEntity> players) {
    this.players = players;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public int getRollsLeft() {
    return rollsLeft;
  }

  public void setRollsLeft(int rollsLeft) {
    this.rollsLeft = rollsLeft;
  }

  // endregion
}
