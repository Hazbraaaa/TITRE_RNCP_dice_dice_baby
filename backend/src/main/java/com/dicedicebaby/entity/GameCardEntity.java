package com.dicedicebaby.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game_cards")
public class GameCardEntity extends AuditableEntity {
  // region Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private BoardEntity board;

  @ManyToOne
  @JoinColumn(name = "card_id")
  private CardEntity card;

  @ManyToOne
  @JoinColumn(name = "owner_point_lvl1_id")
  private PlayerEntity ownerPointLvl1;

  @ManyToOne
  @JoinColumn(name = "owner_point_lvl2_id")
  private PlayerEntity ownerPointLvl2;

  // endregion

  // region Constructor
  public GameCardEntity() {}

  // endregion

  // region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BoardEntity getBoard() {
    return board;
  }

  public void setBoard(BoardEntity board) {
    this.board = board;
  }

  public CardEntity getCard() {
    return card;
  }

  public void setCard(CardEntity card) {
    this.card = card;
  }

  public PlayerEntity getOwnerPointLvl1() {
    return ownerPointLvl1;
  }

  public void setOwnerPointLvl1(PlayerEntity ownerPointLvl1) {
    this.ownerPointLvl1 = ownerPointLvl1;
  }

  public PlayerEntity getOwnerPointLvl2() {
    return ownerPointLvl2;
  }

  public void setOwnerPointLvl2(PlayerEntity ownerPointLvl2) {
    this.ownerPointLvl2 = ownerPointLvl2;
  }

  // endregion
}
