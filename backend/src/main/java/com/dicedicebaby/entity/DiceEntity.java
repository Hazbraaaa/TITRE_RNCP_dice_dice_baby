package com.dicedicebaby.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dices")
public class DiceEntity extends AuditableEntity {

  // region Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int value;

  @Column private boolean isKept;

  @ManyToOne
  @JoinColumn(name = "diceSet_id")
  private DiceSetEntity diceSet;

  // endregion

  // region Constructor
  public DiceEntity() {}

  // endregion

  // region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public boolean isKept() {
    return isKept;
  }

  public void setKept(boolean kept) {
    isKept = kept;
  }

  public DiceSetEntity getDiceSet() {
    return diceSet;
  }

  public void setDiceSet(DiceSetEntity diceSet) {
    this.diceSet = diceSet;
  }
  // endregion
}
