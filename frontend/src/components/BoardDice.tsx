type BoardDiceProps = {
  value: number | string;
  locked?: boolean;
  size?: 'sm' | 'md';
};

export const BoardDice = ({
  value,
  locked = false,
  size = 'md'
}: BoardDiceProps) => {
  const isSmall = size === 'sm';
  const isTextValue = typeof value === 'string';

  const dotPositions: Record<number, number[]> = {
    1: [4],
    2: [0, 8],
    3: [0, 4, 8],
    4: [0, 2, 6, 8],
    5: [0, 2, 4, 6, 8],
    6: [0, 2, 3, 5, 6, 8],
  };
  const dots = !isTextValue ? (dotPositions[value as number] || []) : [];

  return (
    <div
      className={`
        relative flex items-center justify-center transition-all duration-200 select-none
        ${isSmall ?
          'w-5 h-5 md:w-6 md:h-6 rounded-md border-[1.5px]' :
          'w-12 h-12 md:w-14 md:h-14 rounded-xl border-[3px] md:border-4'}        
        ${locked ?
          'bg-frost-white border-polar-blue shadow-none translate-y-0.5' :
          'bg-frost-white border-midnight-ice'}
        ${!locked && !isSmall ? 'shadow-[3px_3px_0px_0px_rgba(1,54,89,1)]' : ''}
      `}
    >
      {isTextValue ? (
        // RENDER DE CARACTÈRE (A, B, +1, etc.)
        <span
          className={`font-black tracking-tighter text-center text-midnight-ice leading-none
            ${isSmall ? 'text-[8px] md:text-[9px]' : 'text-lg md:text-xl'}
          `}
        >
          {value}
        </span>
      ) : (
        // RENDER DE POINTS
        <div className={`grid grid-cols-3 grid-rows-3 gap-0.5 ${isSmall ? 'w-3.5 h-3.5 md:w-4 md:h-4' : 'w-8 h-8 md:w-9 md:h-9'}`}>
          {[...Array(9)].map((_, i) => (
            <div key={i} className="flex items-center justify-center">
              {dots.includes(i) && (
                <div
                  className={`rounded-full 
                    ${isSmall ? 'w-0.5 h-0.5 md:w-1 md:h-1' : 'w-1.5 h-1.5 md:w-2 md:h-2'} 
                    ${locked ? 'bg-polar-blue' : 'bg-midnight-ice'}
                  `}
                />
              )}
            </div>
          ))}
        </div>
      )}

      {/* Petit badge "Check" uniquement si grand dé verrouillé */}
      {locked && !isSmall && (
        <div className="absolute -bottom-1.5 -right-1.5 bg-polar-blue text-frost-white rounded-full p-0.5 border-2 border-midnight-ice shadow-sm flex items-center justify-center w-4 h-4">
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