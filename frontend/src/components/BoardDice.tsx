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
        w-14 h-14 md:w-16 md:h-16 rounded-xl border-4 flex items-center justify-center transition-all duration-200
        ${
          locked
            ? 'bg-white border-midnight-ice shadow-none translate-y-1' // GARDÉ : Blanc, stable, enfoncé
            : 'bg-red-alert border-midnight-ice shadow-[4px_4px_0px_0px_rgba(1,54,89,1)]'
        } // À RELANCER : Rouge, alerte, ressorti
      `}
    >
      <div className="grid grid-cols-3 grid-rows-3 gap-1 w-10 h-10 md:w-11 md:h-11">
        {[...Array(9)].map((_, i) => (
          <div key={i} className="flex items-center justify-center">
            {dots.includes(i) && (
              <div
                className={`w-2.5 h-2.5 md:w-3 md:h-3 rounded-full ${
                  locked ? 'bg-midnight-ice' : 'bg-white'
                }`}
              />
            )}
          </div>
        ))}
      </div>
    </div>
  );
};
