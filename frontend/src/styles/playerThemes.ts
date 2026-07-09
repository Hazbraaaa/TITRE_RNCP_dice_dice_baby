export interface PlayerTheme {
  penguinSrc: string;
  displayName: string;
}

export const PLAYER_THEMES: Record<number, PlayerTheme> = {
  1: {
    penguinSrc: '../assets/penguins/penguin_blue.png',
    displayName: 'Pingouin Bleu',
  },
  2: {
    penguinSrc: '../assets/penguins/penguin_green.png',
    displayName: 'Pingouin Vert',
  },
  3: {
    penguinSrc: '../assets/penguins/penguin_yellow.png',
    displayName: 'Pingouin Jaune',
  },
  4: {
    penguinSrc: '../assets/penguins/penguin_red.png',
    displayName: 'Pingouin Rouge',
  },
};
