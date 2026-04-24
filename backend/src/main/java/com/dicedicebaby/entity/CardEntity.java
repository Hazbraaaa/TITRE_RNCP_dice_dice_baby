package com.dicedicebaby.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cards")
public class CardEntity extends AuditableEntity {
  // region Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String combination;

  @Column(nullable = false)
  private String color;

  @Column(nullable = false)
  private int pointLvl1;

  @Column(nullable = false)
  private int pointLvl2;

  // endregion

  // region Constructor
  public CardEntity() {}

  // endregion

  // region Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCombination() {
    return combination;
  }

  public void setCombination(String combination) {
    this.combination = combination;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getPointLvl1() {
    return pointLvl1;
  }

  public void setPointLvl1(int pointLvl1) {
    this.pointLvl1 = pointLvl1;
  }

  public int getPointLvl2() {
    return pointLvl2;
  }

  public void setPointLvl2(int pointLvl2) {
    this.pointLvl2 = pointLvl2;
  }
  // endregion
}
