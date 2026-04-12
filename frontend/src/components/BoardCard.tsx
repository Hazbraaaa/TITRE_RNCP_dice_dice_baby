import { colorVariants } from '../services/colorVariants';
import type { ColorName } from '../services/colorVariants';

type BoardCardProps = {
  combinaison: string;
  color?: ColorName;
  ptLvl1: number;
  ptLvl2: number;
  className?: string;
};

export const BoardCard = ({
  combinaison,
  color = 'green',
  ptLvl1,
  ptLvl2,
  className = '',
}: BoardCardProps) => {
  const colors = colorVariants[color];

  return (
    <div
      className={`aspect-square w-full grid grid-cols-4 grid-rows-4 gap-1 ${colors.base} rounded-md p-1 relative text-white text-[10px] text-center font-semibold ${className}`}
    >
      {/* Combination */}
      <div
        className={`col-start-1 col-span-3 row-start-2 row-span-3 flex items-center justify-center ${colors.dark} rounded px-0.5 leading-tight`}
      >
        {combinaison}
      </div>

      {/* Slot point Lvl1 */}
      <div
        className={`col-start-4 row-start-1 flex items-center justify-center w-4 h-4 text-[10px] ${colors.dark} rounded-full self-center justify-self-center`}
      >
        {ptLvl1}
      </div>

      {/* Slot point Lvl2 */}
      <div
        className={`col-start-4 row-start-2 flex items-center justify-center w-4 h-4 text-[10px] ${colors.dark} opacity-80 rounded-full border border-white/20 self-center justify-self-center`}
      >
        {ptLvl2}
      </div>
    </div>
  );
};