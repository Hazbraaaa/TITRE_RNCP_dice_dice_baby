import { BoardCard } from './BoardCard';
import type { Card } from '../types/card';

interface GameBoardProps {
  cards: Card[];
  selectedCardId: number | null;
  onToggleCard: (cardId: number) => void;
}

export default function GameBoard({
  cards,
  selectedCardId,
  onToggleCard,
}: GameBoardProps) {
  return (
    <div className="flex justify-center items-center w-full h-full">
      <div className="grid grid-cols-4 gap-1.5 md:gap-2 w-full max-w-[340px] md:max-w-[450px] mx-auto">
        {cards.map((card) => (
          <BoardCard
            key={card.id}
            {...card}
            selectedCardId={selectedCardId}
            onToggleCard={onToggleCard}
          />
        ))}
      </div>
    </div>
  );
}
