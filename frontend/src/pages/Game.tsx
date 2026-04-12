import ScoreBoard from '../components/ScoreBoard';
import Hand from '../components/Hand';
import GameBoard from '../components/GameBoard';
import { Button } from '../components/Button';
import { usePartyAuth } from '../hooks/usePartyAuth';

export default function Game() {

  const { connectedPlayers } = usePartyAuth();

  const playersForBoard = connectedPlayers.map(p => ({
    id: p.playerId,
    number: p.playerNumber,
    name: p.username || `Joueur ${p.playerNumber}`,
    score: 0,
    nb_of_pieces: 6
  }));

  return (
    <main className="min-h-screen bg-frost-white/30 py-4 px-4 md:px-8">
      {/* Header */}
      <header className="text-center mb-6">
        <h1 className="font-heading text-2xl md:text-4xl text-polar-blue uppercase tracking-tighter drop-shadow-sm">
          Dice Dice <span className="text-red-alert">Baby</span>
        </h1>
      </header>

      <div className="max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
        
        {/* Scoreboard and Throwing Zone */}
        <div className="lg:col-span-4 flex flex-col gap-6 order-1">
          
          {/* Scoreboard */}
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] p-4">
            <h2 className="font-heading text-midnight-ice/60 text-xs uppercase mb-3 ml-1">
              Tableau des scores
            </h2>
            <ScoreBoard players={playersForBoard}/>
          </section>

          {/* Hand */}
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[6px_6px_0px_0px_rgba(1,54,89,1)] p-6 flex flex-col gap-4">
            <Hand />
            <Button 
              variant="primary" 
              fullWidth 
              className="py-6 text-2xl shadow-[4px_4px_0px_0px_rgba(0,0,0,0.2)] hover:scale-[1.02] active:scale-[0.98]"
            >
              LANCER !
            </Button>
          </section>

        </div>

        {/* Game Board */}
        <div className="lg:col-span-8 order-2">
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm p-2 md:p-6 shadow-inner">
            <GameBoard />
          </section>
        </div>

      </div>
    </main>
  );
}