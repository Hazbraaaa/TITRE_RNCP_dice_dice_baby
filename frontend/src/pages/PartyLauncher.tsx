// PartyLauncher.tsx
import { useSearchParams } from "react-router-dom";
import { useState } from "react";
import PlayerLogin from "../components/PlayerLogin";
import ConnexionModal from "../components/ConnexionModal";
import RegisterModal from "../components/RegisterModal";
import GuestModal from "../components/GuestModal";
import { ButtonLink } from "../components/ButtonLink";

type ModalType = "connexion" | "register" | "guest";

interface OpenModalState {
  playerNumber: number;
  type: ModalType;
}

export default function PartyLauncher() {
  const [searchParams] = useSearchParams();
  const playersCount = Number(searchParams.get("players"));
  const isValidPlayersCount = playersCount >= 2 && playersCount <= 4;

  const [openModal, setOpenModal] = useState<OpenModalState | null>(null);

  return (
    <main className="p-4">
      <h2 className="text-xl font-bold text-center mb-4">Identification des joueurs</h2>

      {isValidPlayersCount ? (
        <div className="flex flex-col gap-4 w-full max-w-xs mx-auto">
          {Array.from({ length: playersCount }).map((_, index) => (
            <PlayerLogin
              key={index}
              playerNumber={index + 1}
              onLogin={() => setOpenModal({ playerNumber: index + 1, type: "connexion" })}
              onRegister={() => setOpenModal({ playerNumber: index + 1, type: "register" })}
              onGuest={() => setOpenModal({ playerNumber: index + 1, type: "guest" })}
            />
          ))}
          <ButtonLink
            to={`/game`}
            className="text-center"
          >
            Lancer partie
          </ButtonLink>
        </div>
      ) : (
        <p className="text-red-600 text-center">
          Nombre de joueurs invalide. Veuillez revenir à l'accueil.
        </p>
      )}

      {openModal?.type === "connexion" && (
        <ConnexionModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={() => setOpenModal(null)}
          onSubmit={(data) => {
            console.log(`Joueur ${openModal.playerNumber} connecté :`, data);
            setOpenModal(null);
          }}
        />
      )}

      {openModal?.type === "register" && (
        <RegisterModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={() => setOpenModal(null)}
          onSubmit={(data) => {
            console.log(`Joueur ${openModal.playerNumber} inscrit :`, data);
            setOpenModal(null);
          }}
        />
      )}

      {openModal?.type === "guest" && (
        <GuestModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={() => setOpenModal(null)}
          onSubmit={(pseudo) => {
            console.log(`Joueur ${openModal.playerNumber} invité :`, pseudo);
            setOpenModal(null);
          }}
        />
      )}
    </main>
  );
}
