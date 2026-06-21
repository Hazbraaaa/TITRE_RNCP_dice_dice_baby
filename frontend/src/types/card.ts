import type { CardRequirement } from './enums/cardRequirement';

export type CardColor = 'green' | 'blue' | 'red';

export interface Card {
  id: number;
  combination: CardRequirement;
  color: CardColor;
  pointLvl1: number;
  pointLvl2: number;
  ownerPointLvl1: number | null;
  ownerPointLvl2: number | null;
}
