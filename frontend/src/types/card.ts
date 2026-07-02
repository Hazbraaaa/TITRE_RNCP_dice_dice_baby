import type { eCardRequirement } from './enums/eCardRequirement';

export type CardColor = 'green' | 'blue' | 'red';

export interface Card {
  id: number;
  combination: eCardRequirement;
  color: CardColor;
  pointLvl1: number;
  pointLvl2: number;
  ownerPointLvl1: number | null;
  ownerPointLvl2: number | null;
}
