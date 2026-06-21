export const CardRequirement = {
  // Pairs
  PAIR_1: 'Paire de 1',
  PAIR_2: 'Paire de 2',
  PAIR_3: 'Paire de 3',
  PAIR_4: 'Paire de 4',
  PAIR_5: 'Paire de 5',
  PAIR_6: 'Paire de 6',
  DOUBLE_PAIR: 'Double paires',
  // Three of a kind
  THREE_OF_A_KIND_1: 'Brelan de 1',
  THREE_OF_A_KIND_2: 'Brelan de 2',
  THREE_OF_A_KIND_3: 'Brelan de 3',
  THREE_OF_A_KIND_4: 'Brelan de 4',
  THREE_OF_A_KIND_5: 'Brelan de 5',
  THREE_OF_A_KIND_6: 'Brelan de 6',
  // Others
  SUM_12_13_14: 'Somme 12, 13 ou 14',
  SUM_21_22_23: 'Somme 21, 22 ou 23',
  LESS_THAN_9: 'Moins ou égale à 9',
  GREATER_THAN_26: 'Plus ou égale à 26',
  ALL_EVEN: 'Toutes paires',
  ALL_ODD: 'Toutes impaires',
  SMALL_STRAIGHT: 'Suite petite',
  LARGE_STRAIGHT: 'Suite grande',
  FULL_HOUSE: 'Full house',
  FOUR_OF_A_KIND: 'Carré',
  ALL_DIFFERENT: 'Toutes différentes',
  ALL_IDENTICAL: 'Toutes identiques',
} as const;

export type CardRequirement =
  (typeof CardRequirement)[keyof typeof CardRequirement];
