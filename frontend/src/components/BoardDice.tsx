type BoardDiceProps = {
  value: number;
  locked?: boolean;
};

export const BoardDice = ({ value, locked = false }: BoardDiceProps) => {
  const dotPositions: Record<number, number[]> = {
    1: [4],
    2: [0, 8],
    3: [0, 4, 8],
    4: [0, 2, 6, 8],
    5: [0, 2, 4, 6, 8],
    6: [0, 2, 3, 5, 6, 8],
  };

  const dots = dotPositions[value] || [];

  return (
    <div
      className={`
        relative w-14 h-14 md:w-16 md:h-16 rounded-xl border-4 flex items-center justify-center transition-all duration-200
        ${
          locked
            ? 'bg-frost-white border-polar-blue shadow-none translate-y-1'
            : 'bg-frost-white border-midnight-ice shadow-[4px_4px_0px_0px_rgba(1,54,89,1)]'
        }
      `}
    >
      <div className="grid grid-cols-3 grid-rows-3 gap-1 w-10 h-10 md:w-11 md:h-11">
        {[...Array(9)].map((_, i) => (
          <div key={i} className="flex items-center justify-center">
            {dots.includes(i) && (
              <div
                className={`w-2.5 h-2.5 md:w-3 md:h-3 rounded-full ${
                  locked ? 'bg-polar-blue' : 'bg-midnight-ice'
                }`}
              />
            )}
          </div>
        ))}
      </div>

      {/* Petit badge "Check" en bas à droite si sélectionné */}
      {locked && (
        <div className="absolute -bottom-2.5 -right-2.5 bg-polar-blue text-frost-white rounded-full p-0.5 border-2 border-midnight-ice shadow-sm flex items-center justify-center w-4 h-4 md:w-4.5 md:h-4.5 translate-y-[-4px]">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 256 256"
            className="w-3 h-3 md:w-3.5 md:h-3.5"
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
