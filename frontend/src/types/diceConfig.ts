import { eDiceContext } from './enums/eDiceContext';

export type DiceContext = eDiceContext;

export const diceContainerStyles = {
  [eDiceContext.CARD_MOBILE]: 'w-3 h-3 rounded-[4px] border-[1px]',
  [eDiceContext.CARD_DESKTOP]: 'w-6 h-6 rounded-md border-[1.5px]',
  [eDiceContext.HAND_MOBILE]: 'w-10 h-10 rounded-lg border-2',
  [eDiceContext.HAND_DESKTOP]: 'w-13 h-13 rounded-xl border-3',
} as const;

export const diceTextStyles = {
  [eDiceContext.CARD_MOBILE]: 'text-[6px]',
  [eDiceContext.CARD_DESKTOP]: 'text-md',
  [eDiceContext.HAND_MOBILE]: '',
  [eDiceContext.HAND_DESKTOP]: '',
} as const;

export const diceGridStyles = {
  [eDiceContext.CARD_MOBILE]: 'w-1.5 h-1.5 gap-[1px]',
  [eDiceContext.CARD_DESKTOP]: 'w-4 h-4 gap-0.5',
  [eDiceContext.HAND_MOBILE]: 'w-6 h-6 gap-0.5',
  [eDiceContext.HAND_DESKTOP]: 'w-8 h-8 gap-0.5',
} as const;

export const diceDotStyles = {
  [eDiceContext.CARD_MOBILE]: 'w-1 h-1',
  [eDiceContext.CARD_DESKTOP]: 'w-1 h-1',
  [eDiceContext.HAND_MOBILE]: 'w-1.5 h-1.5',
  [eDiceContext.HAND_DESKTOP]: 'w-2 h-2',
} as const;

export const diceDotPositions = {
  1: [4],
  2: [0, 8],
  3: [0, 4, 8],
  4: [0, 2, 6, 8],
  5: [0, 2, 4, 6, 8],
  6: [0, 2, 3, 5, 6, 8],
} as const;
