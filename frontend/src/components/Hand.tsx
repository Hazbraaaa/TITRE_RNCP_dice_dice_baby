import { Dice } from './Dice';

export default function Hand({ dices = [] }) {
  // Mock data
  const displayDices =
    dices.length > 0
      ? dices
      : [
          { id: 1, value: 1, locked: false },
          { id: 2, value: 3, locked: false },
          { id: 3, value: 6, locked: false },
          { id: 4, value: 3, locked: false },
          { id: 5, value: 2, locked: false },
        ];

  return (
    <div className="flex flex-wrap gap-3 p-4 bg-midnight-ice/10 border-2 border-dashed border-midnight-ice/30 rounded-xl justify-center items-center">
      {displayDices.map((dice) => (
        <div
          key={dice.id}
          className="transition-transform hover:scale-110 cursor-pointer"
        >
          <Dice value={dice.value} locked={dice.locked} />
        </div>
      ))}
    </div>
  );
}
