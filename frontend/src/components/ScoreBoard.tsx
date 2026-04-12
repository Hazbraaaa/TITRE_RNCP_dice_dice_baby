import { Player } from './Player';

type ScoreBoardProps = {
  players: {
    id: number;
    number: number;
    name: string;
    score: number;
    nb_of_pieces: number;
  }[];
};

export default function ScoreBoard({ 
  players = [] 
}: ScoreBoardProps) {
  return (
    <div className="grid grid-cols-2 gap-4 bg-transparent">
      {players.map((player, index) => (
        <div 
          key={player.id || index} 
          className={`w-full ${index % 2 === 1 ? 'flex justify-end' : 'flex justify-start'}`}
        >
          <Player 
            {...player} 
            align={index % 2 === 1 ? "right" : "left"} 
          />
        </div>
      ))}
    </div>
  );
}