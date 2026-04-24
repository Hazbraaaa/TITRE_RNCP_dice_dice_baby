export type CardColor = 'green' | 'blue' | 'red';

export interface Card {
  id: number;
  combination: string;
  color: CardColor;
  pointLvl1: number;
  pointLvl2: number;
}
