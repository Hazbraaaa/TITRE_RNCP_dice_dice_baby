import type { PlayerInGame } from './playerInGame';
import type { DiceSet } from './diceSet';
import type { Card } from './card';

export type GameState = 'IN_PROGRESS' | 'FINISHED';

export interface Game {
  id: number;
  state: GameState;
  rollsLeft: number;
  roundNumber: number;
  board: Card[];
  diceSet: DiceSet;
  currentPlayer: PlayerInGame;
  players: PlayerInGame[];
}
