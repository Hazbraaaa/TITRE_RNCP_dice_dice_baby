import { BoardDice } from './BoardDice';
import type { Dice } from '../types/dice';
import { eDiceContext } from '../types/enums/eDiceContext';

interface HandProps {
  hand: Dice[];
  selectedIds: number[];
  onToggleDice: (id: number) => void;
}

export default function Hand({ hand, selectedIds, onToggleDice }: HandProps) {
  const sortedHand = [...hand].sort((a, b) => a.id - b.id);

  return (
    <div className="flex gap-3 p-3 bg-midnight-ice/10 border-2 border-dashed border-midnight-ice/30 rounded-xl justify-center items-center w-full mx-auto">
      {sortedHand.map((dice) => {
        const isSelected = selectedIds.includes(dice.id);

        return (
          <div
            key={dice.id}
            className="transition-transform hover:scale-105 active:scale-95 cursor-pointer flex justify-center"
            onClick={() => onToggleDice(dice.id)}
          >
            {/* Mobile version */}
            <div className="md:hidden">
              <BoardDice
                value={dice.value}
                locked={isSelected}
                context={eDiceContext.HAND_MOBILE}
              />
            </div>

            {/* Desktop version */}
            <div className="hidden md:block">
              <BoardDice
                value={dice.value}
                locked={isSelected}
                context={eDiceContext.HAND_DESKTOP}
              />
            </div>
          </div>
        );
      })}
    </div>
  );
}
