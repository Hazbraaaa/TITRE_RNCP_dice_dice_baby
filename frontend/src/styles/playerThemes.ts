export interface PlayerTheme {
  colorClass: string;
  penguinSrc: string;
  displayName: string;
}

export const PLAYER_THEMES: Record<number, PlayerTheme> = {
  1: {
    colorClass: 'bg-blue-500 text-white',
    penguinSrc: '../assets/penguins/penguin_blue.png',
    displayName: 'Pingouin Bleu',
  },
  2: {
    colorClass: 'bg-green-500 text-white',
    penguinSrc: '../assets/penguins/penguin_green.png',
    displayName: 'Pingouin Vert',
  },
  3: {
    colorClass: 'bg-yellow-500 text-white',
    penguinSrc: '../assets/penguins/penguin_yellow.png',
    displayName: 'Pingouin Jaune',
  },
  4: {
    colorClass: 'bg-red-500 text-white',
    penguinSrc: '../assets/penguins/penguin_red.png',
    displayName: 'Pingouin Rouge',
  },
};
