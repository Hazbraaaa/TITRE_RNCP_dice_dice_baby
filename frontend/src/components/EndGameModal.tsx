import Modal from './Modal';
import { Button } from './Button';
import type { Game } from '../types/game';

type EndGameModalProps = {
  isOpen: boolean;
  onClose: () => void;
  onRematch: () => void;
  onGoToMenu: () => void;
  game: Game;
};

export default function EndGameModal({
  isOpen,
  onClose,
  onRematch,
  onGoToMenu,
  game,
}: EndGameModalProps) {
  const winnerPlayer =
    game?.state === 'FINISHED'
      ? game.players.find((p) => p.playerId === game.winnerId)
      : null;

  const victoryMessage = winnerPlayer
    ? `Fin de la partie ! Le gagnant est ${winnerPlayer.username}`
    : 'Fin de la partie !';

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <h2 className="text-3xl font-heading text-polar-blue mb-6 text-center uppercase tracking-tight">
        Fin de partie
      </h2>

      {/* Message with result zone*/}
      {victoryMessage && (
        <div className="mb-6 p-4 bg-polar-blue/10 border-2 border-polar-blue text-midnight-ice rounded-md text-sm font-bold uppercase text-center tracking-wide shadow-inner">
          {victoryMessage}
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
