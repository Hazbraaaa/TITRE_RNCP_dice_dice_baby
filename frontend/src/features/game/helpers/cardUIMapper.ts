import { eCardRequirement } from '../../../types/enums/eCardRequirement';

/**
 * Name of a supported card requirement.
 */
export type CardRequirementKey = keyof typeof eCardRequirement;

/**
 * Value displayed on a dice face.
 */
export type DiceFace = number | string;

/**
 * Describes how a card requirement should be rendered.
 */
export type CardUiRender =
  | { type: 'dice'; values: DiceFace[]; maxDicePerLine: 2 | 3 }
  | { type: 'text'; label: string };

/**
 * Maps a card requirement to its visual representation.
 *
 * @param combination The card requirement to display.
 * @param displayName The fallback label for unsupported requirements.
 * @returns The dice or text representation of the requirement.
 */
export const getUiForCombination = (
  combination: CardRequirementKey,
  displayName: string
): CardUiRender => {
  switch (combination) {
    case 'PAIR_1':
      return { type: 'dice', values: [1, 1], maxDicePerLine: 2 };
    case 'PAIR_2':
      return { type: 'dice', values: [2, 2], maxDicePerLine: 2 };
    case 'PAIR_3':
      return { type: 'dice', values: [3, 3], maxDicePerLine: 2 };
    case 'PAIR_4':
      return { type: 'dice', values: [4, 4], maxDicePerLine: 2 };
    case 'PAIR_5':
      return { type: 'dice', values: [5, 5], maxDicePerLine: 2 };
    case 'PAIR_6':
      return { type: 'dice', values: [6, 6], maxDicePerLine: 2 };

    case 'THREE_OF_A_KIND_1':
      return { type: 'dice', values: [1, 1, 1], maxDicePerLine: 3 };
    case 'THREE_OF_A_KIND_2':
      return { type: 'dice', values: [2, 2, 2], maxDicePerLine: 3 };
    case 'THREE_OF_A_KIND_3':
      return { type: 'dice', values: [3, 3, 3], maxDicePerLine: 3 };
    case 'THREE_OF_A_KIND_4':
      return { type: 'dice', values: [4, 4, 4], maxDicePerLine: 3 };
    case 'THREE_OF_A_KIND_5':
      return { type: 'dice', values: [5, 5, 5], maxDicePerLine: 3 };
    case 'THREE_OF_A_KIND_6':
      return { type: 'dice', values: [6, 6, 6], maxDicePerLine: 3 };

    case 'DOUBLE_PAIR':
      return { type: 'dice', values: ['A', 'A', 'B', 'B'], maxDicePerLine: 2 };
    case 'SMALL_STRAIGHT':
      return {
        type: 'dice',
        values: ['A', '+1', '+2', '+3'],
        maxDicePerLine: 2,
      };
    case 'LARGE_STRAIGHT':
      return {
        type: 'dice',
        values: ['A', '+1', '+2', '+3', '+4'],
        maxDicePerLine: 3,
      };
    case 'FULL_HOUSE':
      return {
        type: 'dice',
        values: ['A', 'A', 'A', 'B', 'B'],
        maxDicePerLine: 3,
      };
    case 'FOUR_OF_A_KIND':
      return { type: 'dice', values: ['A', 'A', 'A', 'A'], maxDicePerLine: 2 };
    case 'ALL_IDENTICAL':
      return {
        type: 'dice',
        values: ['A', 'A', 'A', 'A', 'A'],
        maxDicePerLine: 3,
      };
    case 'ALL_DIFFERENT':
      return {
        type: 'dice',
        values: ['A', 'B', 'C', 'D', 'E'],
        maxDicePerLine: 3,
      };

    case 'SUM_12_13_14':
      return { type: 'text', label: '12 / 13 / 14' };
    case 'SUM_21_22_23':
      return { type: 'text', label: '21 / 22 / 23' };
    case 'LESS_THAN_9':
      return { type: 'text', label: '≤ 9' };
    case 'GREATER_THAN_26':
      return { type: 'text', label: '≥ 26' };
    case 'ALL_EVEN':
      return { type: 'text', label: 'Tous Pairs' };
    case 'ALL_ODD':
      return { type: 'text', label: 'Tous Impairs' };

    default:
      return { type: 'text', label: displayName };
  }
};
