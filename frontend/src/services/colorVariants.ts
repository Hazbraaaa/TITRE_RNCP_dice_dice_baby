export const colorVariants = {
  red: {
    base: "bg-red-400",
    dark: "bg-red-600",
  },
  blue: {
    base: "bg-blue-400",
    dark: "bg-blue-600",
  },
  green: {
    base: "bg-green-400",
    dark: "bg-green-600",
  },
} as const;

export type ColorName = keyof typeof colorVariants;
