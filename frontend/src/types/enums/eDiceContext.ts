export const eDiceContext = {
  CARD_MOBILE: 'CardMobile',
  CARD_DESKTOP: 'CardDesktop',
  HAND_MOBILE: 'HandMobile',
  HAND_DESKTOP: 'HandDesktop',
} as const;

export type eDiceContext = (typeof eDiceContext)[keyof typeof eDiceContext];
