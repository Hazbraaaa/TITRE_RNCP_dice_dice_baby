import ScoreBoard from '../components/ScoreBoard';
import Hand from '../components/Hand';
import GameBoard from '../components/GameBoard';
import { Button } from '../components/Button';
import { useGame } from '../hooks/useGame';
import Snackbar from '../components/SnackBar';
import EndGameModal from '../components/EndGameModal';

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
    handleRematch,
    handleGoToMenu,
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
  const playersForBoard = [...game.players]
    .sort((a, b) => a.playerNumber - b.playerNumber)
    .map((player) => ({
      id: player.playerId,
      number: player.playerNumber,
      name: player.username || `Joueur ${player.playerId}`,
      score: player.score,
      remainingChips: player.remainingChips,
      isCurrent: player.playerId === game.currentPlayer.playerId,
    }));

  return (
    <main className="min-h-screen bg-frost-white/30 py-4 px-4 md:px-8">
      {/* Grid */}
      <div className="max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
        {/* Left zone */}
        <div className="lg:col-span-4 flex flex-col gap-4 order-1">
          {/* Header */}
          <header className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] p-2 text-center">
            <h1 className="font-heading text-2xl md:text-4xl text-polar-blue uppercase tracking-tighter drop-shadow-sm">
              Dice Dice <span className="text-red-alert">Baby</span>
            </h1>
          </header>

          {/* Scoreboard */}
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] p-4">
            {/* Game info */}
            <div className="mb-2 font-heading text-xs md:text-sm text-midnight-ice/60 uppercase tracking-wider">
              Manche{' '}
              <span className="text-midnight-ice font-bold">
                {game.roundNumber}
              </span>{' '}
              — Au tour de :{' '}
              <span className="text-red-alert font-bold">
                {game.currentPlayer.username}
              </span>
            </div>

            <ScoreBoard players={playersForBoard} />
          </section>

          {/* Action zone */}
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] p-4 flex flex-col gap-4">
            {/* Hand of dices */}
            <Hand
              hand={dices}
              selectedIds={keptDiceIds}
              onToggleDice={toggleDice}
            />

            {/* Throw Button */}
            <Button
              variant={game.rollsLeft <= 0 ? 'disabled' : 'primary'}
              size="md"
              fullWidth
              className="flex flex-col items-center justify-center shadow-[4px_4px_0px_0px_rgba(0,0,0,0.2)]"
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
                  : 'secondary'
              }
              size="md"
              onClick={handleEndTurn}
              disabled={game.rollsLeft === 3 || selectedCardId === null}
            >
              <div>VALIDER LE CHOIX</div>
            </Button>

            {/* Skip Turn Button (only if no more rolls left) */}
            {game.rollsLeft === 0 && (
              <Button
                variant={selectedCardId === null ? 'warning' : 'outlined'}
                size="md"
                onClick={handleSkipTurn}
              >
                <div>PASSER LE TOUR</div>
              </Button>
            )}
          </section>
        </div>

        {/* Right zone */}
        <div className="lg:col-span-8 order-2">
          {/* Game Board */}
          <section className="bg-frost-white border-[3px] border-midnight-ice rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] p-2 md:p-4">
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

      {/* End game modal */}
      <EndGameModal
        isOpen={game?.state === 'FINISHED'}
        onClose={() => setAlertMessage(null)}
        onRematch={handleRematch}
        onGoToMenu={handleGoToMenu}
        game={game}
      />
    </main>
  );
}
