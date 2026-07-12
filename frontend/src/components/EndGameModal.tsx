import Modal from './Modal';
import { Button } from './Button';
import type { Game } from '../types/game';
import { Player } from './Player';

type EndGameModalProps = {
  isOpen: boolean;
  onRematch: () => void;
  onGoToMenu: () => void;
  game: Game;
};

export default function EndGameModal({
  isOpen,
  onRematch,
  onGoToMenu,
  game,
}: EndGameModalProps) {
  const winnerPlayer =
    game?.state === 'FINISHED'
      ? game.players.find((p) => p.playerId === game.winnerId)
      : null;

  return (
    <Modal isOpen={isOpen}>
      <h2 className="text-3xl font-heading text-polar-blue mb-6 text-center uppercase tracking-tight">
        Fin de partie
      </h2>

      {/* Message with result zone*/}
      {winnerPlayer && (
        <div className="mb-6 flex flex-col items-center gap-3">
          <p className="text-sm font-bold uppercase text-midnight-ice">
            Le gagnant est
          </p>

          <Player
            number={winnerPlayer.playerNumber}
            name={winnerPlayer.username}
            score={winnerPlayer.score}
            remainingChips={winnerPlayer.remainingChips}
          />
        </div>
      )}

      {/* Replay button */}
      <div className="flex flex-col gap-3">
        <Button onClick={onRematch} fullWidth className="mt-4 py-3 text-sm">
          REJOUER
        </Button>

        {/* Return to menu button */}
        <Button onClick={onGoToMenu} fullWidth className="mt-4 py-3 text-sm">
          RETOUR AU MENU
        </Button>
      </div>
    </Modal>
  );
}
