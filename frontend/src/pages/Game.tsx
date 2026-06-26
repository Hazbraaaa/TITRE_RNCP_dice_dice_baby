import ScoreBoard from '../components/ScoreBoard';
import Hand from '../components/Hand';
import GameBoard from '../components/GameBoard';
import { Button } from '../components/Button';
import { useGame } from '../hooks/useGame';
import Snackbar from '../components/SnackBar';

export default function Game() {
  const {
    alertMessage,
    game,
    cards,
    dices,
    keptDiceIds,
    selectedCardId,
    toggleDice,
    toggleSelectCard,
    handleRoll,
    handleEndTurn,
    handleSkipTurn,
    clearAlert,
  } = useGame();

  // If the game data is not yet available, show a loading state
  if (!game) {
    return (
      <div className="min-h-screen bg-frost-white/30 flex items-center justify-center">
        <div className="font-heading text-2xl text-midnight-ice animate-pulse uppercase">
          Création de la table de jeu...
        </div>
      </div>
    );
  }

  // Transform the players data for the ScoreBoard component
  const playersForBoard = game.players.map((player, index) => ({
    id: player.playerId,
    number: index + 1,
    name: player.username || `Joueur ${player.playerId}`,
    score: player.score,
    remainingChips: player.remainingChips,
    isCurrent: player.playerId === game.currentPlayer.playerId,
  }));

  return (
    <main className="min-h-screen bg-frost-white/30 py-4 px-4 md:px-8">
      {/* Header */}
      <header className="text-center mb-6">
        <h1 className="font-heading text-2xl md:text-4xl text-polar-blue uppercase tracking-tighter drop-shadow-sm">
          Dice Dice <span className="text-red-alert">Baby</span>
        </h1>

        {/* Game info (MOVE ELSEWHERE) */}
        <div className="mt-2 font-heading text-xs md:text-sm text-midnight-ice/60 uppercase tracking-wider">
          Manche{' '}
          <span className="text-midnight-ice font-bold">
            #{game.roundNumber}
          </span>{' '}
          — Au tour de :{' '}
          <span className="text-red-alert font-bold">
            {game.currentPlayer.username}
          </span>
        </div>
      </header>

      <div className="max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
        {/* Scoreboard and Throwing Zone */}
        <div className="lg:col-span-4 flex flex-col gap-6 order-1">
          {/* Scoreboard */}
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] p-4">
            <h2 className="font-heading text-midnight-ice/60 text-xs uppercase mb-3 ml-1">
              Tableau des scores
            </h2>
            <ScoreBoard players={playersForBoard} />
          </section>

          {/* Action zone */}
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[6px_6px_0px_0px_rgba(1,54,89,1)] p-6 flex flex-col gap-4">
            {/* Hand of dices */}
            <Hand
              hand={dices}
              selectedIds={keptDiceIds}
              onToggleDice={toggleDice}
            />

            {/* Throw Button */}
            <Button
              variant={game.rollsLeft <= 0 ? 'disabled' : 'primary'}
              fullWidth
              className="flex flex-col items-center justify-center py-6 text-xl shadow-[4px_4px_0px_0px_rgba(0,0,0,0.2)]"
              onClick={handleRoll}
              disabled={game.rollsLeft <= 0}
            >
              <div>LANCER LES DÉS</div>
              <span className="text-sm font-normal mt-1 opacity-80">
                ({game.rollsLeft} restants)
              </span>
            </Button>

            {/* End Turn Button */}
            <Button
              variant={
                game.rollsLeft === 3 || selectedCardId === null
                  ? 'disabled'
                  : 'warning'
              }
              onClick={handleEndTurn}
              disabled={game.rollsLeft === 3 || selectedCardId === null}
            >
              <div>FIN DU TOUR</div>
            </Button>

            {/* Skip Turn Button (only if no more rolls left) */}
            {game.rollsLeft === 0 && (
              <Button variant="warning" onClick={handleSkipTurn}>
                <div>PASSER LE TOUR</div>
              </Button>
            )}
          </section>
        </div>

        {/* Game Board */}
        <div className="lg:col-span-8 order-2">
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm p-2 md:p-6 shadow-inner">
            <GameBoard
              cards={cards}
              selectedCardId={selectedCardId}
              onToggleCard={toggleSelectCard}
            />
          </section>
        </div>
      </div>

      {/* Snackbar */}
      <Snackbar message={alertMessage} onClose={clearAlert} />
    </main>
  );
}
