package com.dicedicebaby.enums;

import org.springframework.http.HttpStatus;

public enum ApiErrorCode {
  SESSION_MISSING(HttpStatus.UNAUTHORIZED, "Session absente"),
  SESSION_INVALID(HttpStatus.UNAUTHORIZED, "Session invalide ou expirée"),
  GAME_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Cette session n'est pas autorisée pour cette partie"),
  GAME_ALREADY_FINISHED(HttpStatus.CONFLICT, "Cette partie est déjà finie"),
  GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "Cette partie n'existe pas"),
  CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Cette carte n'existe pas"),
  NEXT_PLAYER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Joueur suivant introuvable"),
  NO_ROLL_LEFT(HttpStatus.CONFLICT, "Plus de lancers disponibles pour ce tour"),
  CARD_ALREADY_OWNED(HttpStatus.CONFLICT, "Vous avez déjà validé cette carte"),
  CARD_FULLY_CLAIMED(HttpStatus.CONFLICT, "Cette carte est déjà entièrement prise"),
  CARD_VALIDATION_FAILED(
      HttpStatus.CONFLICT, "Les dés ne correspondent pas aux exigences de la carte");

  // region Attributes
  private final HttpStatus status;
  private final String message;

  // endregion

  // region Constructor
  ApiErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  // endregion

  // region Methods
  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
  // endregion
}
