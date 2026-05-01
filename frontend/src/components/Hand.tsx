import { BoardDice } from './BoardDice';
import type { Dice } from '../types/dice';

interface HandProps {
  hand: Dice[];
  selectedIds: number[];
  onToggleDice: (id: number) => void;
}

export default function Hand({ hand, selectedIds, onToggleDice }: HandProps) {
  const sortedHand = [...hand].sort((a, b) => a.id - b.id);

  return (
    <div className="flex flex-wrap gap-3 p-4 bg-midnight-ice/10 border-2 border-dashed border-midnight-ice/30 rounded-xl justify-center items-center">
      {sortedHand.map((dice) => {
        const isSelected = selectedIds.includes(dice.id);

        return (
          <div
            key={dice.id}
            className="transition-transform hover:scale-110 cursor-pointer"
            onClick={() => onToggleDice(dice.id)}
          >
            <BoardDice value={dice.value} locked={isSelected} />
          </div>
        );
      })}
    </div>
  );
}
