import { colorVariants } from '../types/colorVariants';
import type { Card } from '../types/card';
import { CardRequirement } from '../types/enums/cardRequirement';

type BoardCardProps = Card & {
  className?: string;
  selectedCardId: number | null;
  onToggleCard: (cardId: number) => void;
};

export const BoardCard = ({
  id,
  combination,
  color,
  pointLvl1,
  pointLvl2,
  className = '',
  selectedCardId,
  onToggleCard,
}: BoardCardProps) => {
  const colors = colorVariants[color];
  const displayName =
    CardRequirement[combination as keyof typeof CardRequirement] || combination;

  const isSelected = selectedCardId === id;

  return (
    <div
      className={`aspect-square w-full grid grid-cols-4 grid-rows-4 gap-1 ${colors.base} rounded-md p-1 relative text-white text-[10px] text-center font-semibold cursor-pointer transition-all ${isSelected ? 'ring-2 ring-midnight-ice scale-105 z-10 shadow-lg' : 'hover:scale-105'} ${className}`}
      onClick={() => onToggleCard(id)}
    >
      {/* Combination */}
      <div
        className={`col-start-1 col-span-3 row-start-2 row-span-3 flex items-center justify-center ${colors.dark} rounded px-0.5 leading-tight`}
      >
        {displayName}
      </div>

      {/* Slot point Lvl1 */}
      <div
        className={`col-start-4 row-start-1 flex items-center justify-center w-4 h-4 text-[10px] ${colors.dark} rounded-full self-center justify-self-center`}
      >
        {pointLvl1}
      </div>

      {/* Slot point Lvl2 */}
      <div
        className={`col-start-4 row-start-2 flex items-center justify-center w-4 h-4 text-[10px] ${colors.dark} opacity-80 rounded-full border border-white/20 self-center justify-self-center`}
      >
        {pointLvl2}
      </div>
    </div>
  );
};
