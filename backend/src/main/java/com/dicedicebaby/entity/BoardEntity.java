package com.dicedicebaby.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "boards")
public class BoardEntity extends AuditableEntity {
  // region Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("id ASC")
  private List<GameCardEntity> gameCards;

  @OneToOne
  @JoinColumn(name = "game_id")
  private GameEntity game;

  // endregion

  // region Constructor
  public BoardEntity() {}

  // endregion

  // region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<GameCardEntity> getGameCards() {
    return gameCards;
  }

  public void setGameCards(List<GameCardEntity> gameCards) {
    this.gameCards = gameCards;
  }

  public GameEntity getGame() {
    return game;
  }

  public void setGame(GameEntity game) {
    this.game = game;
  }

  // endregion
}
