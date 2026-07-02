import { colorVariants } from '../types/colorVariants';
import { PLAYER_THEMES } from '../styles/playerThemes';
import type { Card } from '../types/card';
import { CardRequirement } from '../types/enums/cardRequirement';
import { BoardDice } from './BoardDice';
import { getUiForCombination } from '../features/game/helpers/cardUIMapper';

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
  ownerPointLvl1,
  ownerPointLvl2,
  className = '',
  selectedCardId,
  onToggleCard,
}: BoardCardProps) => {
  const colors = colorVariants[color];
  const displayName = CardRequirement[combination as keyof typeof CardRequirement] || combination;
  const isSelected = selectedCardId === id;
  const ui = getUiForCombination(combination as keyof typeof CardRequirement, displayName);

  const maxWithDiceMap = {
    2: 'max-w-[52px]',
    3: 'max-w-[76px]',
  };

  return (
    <div
      className={`aspect-square w-full grid grid-cols-4 grid-rows-4 gap-1.5 ${colors.base} rounded-md p-1.5 relative text-white text-[10px] text-center font-semibold cursor-pointer transition-all ${isSelected ? 'ring-2 ring-midnight-ice scale-105 z-10 shadow-lg' : 'hover:scale-105'} ${className}`} 
      onClick={() => onToggleCard(id)}
    >
      {/* Combination */}
      <div
        className={`col-start-1 col-span-3 row-start-2 row-span-3 flex items-center justify-center ${colors.dark} rounded px-0.5 p-1 leading-tight`}
      >
        {ui.type === 'dice' ? (
          <div
            className={`flex flex-wrap gap-1 justify-center items-center mx-auto ${maxWithDiceMap[ui.maxDicePerLine]}`}
          >
            {ui.values.map((faceValue, index) => (
              <BoardDice key={index} value={faceValue} size="sm" />
            ))}
          </div>
        ) : (
          <span className="uppercase text-bold text-[9px] md:text-[10px] whitespace-pre-line px-0.5">
            {ui.label}
          </span>
        )}
      </div>

      {/* Slot point Lvl1 */}
      <div
        className={`relative col-start-4 row-start-1 flex items-center justify-center w-6 h-6 text-[10px] ${colors.dark} rounded-full self-center justify-self-center`}
      >
        {pointLvl1}
        {ownerPointLvl1 !== null && ownerPointLvl1 !== undefined && (
          <div className="absolute inset-0 rounded-full flex items-center justify-center shadow-sm animate-popIn bg-polar-blue overflow-hidden">
            <img
              src={PLAYER_THEMES[ownerPointLvl1]?.penguinSrc}
              alt={`Joueur ${ownerPointLvl1}`}
              className="w-full h-full object-cover scale-130 object-[center_top]"
            />
          </div>
        )}
      </div>

      {/* Slot point Lvl2 */}
      <div
        className={`relative col-start-4 row-start-2 flex items-center justify-center w-6 h-6 text-[10px] ${colors.dark} rounded-full self-center justify-self-center`}
      >
        {pointLvl2}
        {ownerPointLvl2 !== null && ownerPointLvl2 !== undefined && (
          <div className="absolute inset-0 rounded-full flex items-center justify-center shadow-sm animate-popIn bg-polar-blue overflow-hidden">
            <img
              src={PLAYER_THEMES[ownerPointLvl2]?.penguinSrc}
              alt={`Joueur ${ownerPointLvl2}`}
              className="w-full h-full object-cover scale-130 object-[center_top]"
            />
          </div>
        )}
      </div>
    </div >
  );
};
