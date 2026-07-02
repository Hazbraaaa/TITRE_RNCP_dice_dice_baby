import {
  diceContainerStyles,
  diceTextStyles,
  diceGridStyles,
  diceDotStyles,
  diceDotPositions,
  type DiceContext,
} from '../types/diceConfig';
import { eDiceContext } from '../types/enums/eDiceContext';

type BoardDiceProps = {
  value: number | string;
  locked?: boolean;
  context?: DiceContext;
};

export const BoardDice = ({
  value,
  locked = false,
  context = eDiceContext.CARD_DESKTOP,
}: BoardDiceProps) => {
  const isTextValue = typeof value === 'string';

  const dots: readonly number[] = !isTextValue
    ? diceDotPositions[value as keyof typeof diceDotPositions] || []
    : [];

  return (
    <div
      className={`
        relative flex items-center justify-center transition-all duration-200 select-none
        ${diceContainerStyles[context]}    
        ${
          locked
            ? 'bg-frost-white border-polar-blue shadow-none translate-y-0.5'
            : 'bg-frost-white border-midnight-ice'
        }
        ${!locked && context === eDiceContext.HAND_DESKTOP ? 'shadow-[3px_3px_0px_0px_rgba(1,54,89,1)]' : ''}
        ${!locked && context === eDiceContext.HAND_MOBILE ? 'shadow-[2px_2px_0px_0px_rgba(1,54,89,1)]' : ''}
      `}
    >
      {isTextValue ? (
        // Characters Render (A, B, +1, etc.)
        <span
          className={`font-black tracking-tighter text-center text-midnight-ice leading-none ${diceTextStyles[context]}`}
        >
          {value}
        </span>
      ) : (
        // Dots Render
        <div
          className={`grid grid-cols-3 grid-rows-3 gap-0.5 ${diceGridStyles[context]}`}
        >
          {[...Array(9)].map((_, i) => (
            <div key={i} className="flex items-center justify-center">
              {dots.includes(i) && (
                <div
                  className={`rounded-full ${diceDotStyles[context]} ${locked ? 'bg-polar-blue' : 'bg-midnight-ice'}`}
                />
              )}
            </div>
          ))}
        </div>
      )}

      {/* "Check" badge only if dice is in HAND */}
      {locked &&
        (context == eDiceContext.HAND_MOBILE ||
          context == eDiceContext.HAND_DESKTOP) && (
          <div
            className={`absolute bg-polar-blue text-frost-white rounded-full p-0.5 border-2 border-midnight-ice shadow-sm flex items-center justify-center 
            ${context === eDiceContext.HAND_MOBILE ? '-bottom-1 -right-1 w-3.5 h-3.5' : '-bottom-1.5 -right-1.5 w-4 h-4'}`}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 256 256"
              className="w-2.5 h-2.5"
              fill="currentColor"
            >
              <path
                d="M104,196a8,8,0,0,1-5.66-2.34l-56-56a8,8,0,0,1,11.32-11.32L104,176.69,202.34,78.34a8,8,0,0,1,11.32,11.32l-104,104A8,8,0,0,1,104,196Z"
                stroke="currentColor"
                strokeWidth="12"
              ></path>
            </svg>
          </div>
        )}
    </div>
  );
};
