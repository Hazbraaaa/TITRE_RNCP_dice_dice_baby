package com.dicedicebaby.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dice_sets")
public class DiceSetEntity extends AuditableEntity {

  // region Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "diceSet", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DiceEntity> dices;

  // endregion

  // region Constructor
  public DiceSetEntity() {}

  // endregion

  // region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<DiceEntity> getDices() {
    return dices;
  }

  public void setDices(List<DiceEntity> dices) {
    this.dices = dices;
  }
  // endregion
}
