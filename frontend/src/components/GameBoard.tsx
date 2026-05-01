import { BoardCard } from './BoardCard';
import type { Card } from '../types/card';

interface GameBoardProps {
  cards: Card[];
}

export default function GameBoard({ cards }: GameBoardProps) {
  return (
    <div className="flex justify-center items-center w-full h-full">
      <div className="grid grid-cols-4 gap-1.5 md:gap-2 w-full max-w-[340px] md:max-w-[450px] mx-auto">
        {cards.map((card) => (
          <BoardCard key={card.id} {...card} />
        ))}
      </div>
    </div>
  );
}
