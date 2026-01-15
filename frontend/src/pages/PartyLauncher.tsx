import { useSearchParams } from "react-router-dom";
import { useState } from "react";
import PlayerLogin from "../components/PlayerLogin";
import LoginModal from "../components/LoginModal";
import RegisterModal from "../components/RegisterModal";
import GuestModal from "../components/GuestModal";
import { ButtonLink } from "../components/ButtonLink";
import { registerUser, loginUser } from "../services/authService";

type ModalType = "login" | "register" | "guest";

interface OpenModalState {
  playerNumber: number;
  type: ModalType;
}

export default function PartyLauncher() {
  const [searchParams] = useSearchParams();
  const playersCount = Number(searchParams.get("players"));
  const isValidPlayersCount = playersCount >= 2 && playersCount <= 4;

  const [openModal, setOpenModal] = useState<OpenModalState | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  // Handle user registration
  const handleRegister = async (username: string, email: string, password: string) => {
    try {
      // Reinitialize error state
      setError(null);

      const userData = { username, email, password };
      const response = await registerUser(userData);

      // Log response, then close the modal upon successful registration
      console.log(`Joueur ${openModal?.playerNumber} inscrit :`, response);
      setOpenModal(null);

      // Show success message
      setSuccessMessage(`Joueur ${username} enregistré avec succès !`);
      setTimeout(() => setSuccessMessage(null), 3000);

    }
    catch (err: any) {
      // Handle registration error, keep the modal open to let user correct inputs
      setError(err.message);
    }
  };

  // Handle user login
  const handleLogin = async (email: string, password: string) => {
    try {
      // Reinitialize error state
      setError(null);

      const userData = { email, password };
      const response = await loginUser(userData);

      // Log response, then close the modal upon successful registration
      console.log(`Joueur ${openModal?.playerNumber} connecté :`, response);
      setOpenModal(null);

      // Show success message
      setSuccessMessage(`Joueur ${response.username} connecté avec succès !`);
      setTimeout(() => setSuccessMessage(null), 3000);
    } 
    catch (err: any) {
      // Handle login error, keep the modal open to let user correct inputs
      setError(err.message);
    }
  }

  // Render
  return (
    <main className="p-4">
      <h2 className="text-xl font-bold text-center mb-4">Identification des joueurs</h2>

      {/* Display success message */}
      {successMessage && (
        <div className="max-w-xs mx-auto mb-4 p-3 bg-green-100 border border-green-400 text-green-700 rounded-lg text-sm flex items-center shadow-sm">
          <span className="mr-2">✅</span>
          {successMessage}
        </div>
      )}

      {/* Check number of players */}
      {isValidPlayersCount ? (
        <div className="flex flex-col gap-4 w-full max-w-xs mx-auto">
          {Array.from({ length: playersCount }).map((_, index) => (
            <PlayerLogin
              key={index}
              playerNumber={index + 1}
              onLogin={() => setOpenModal({ playerNumber: index + 1, type: "login" })}
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

      {/* Set Login modal */}
      {openModal?.type === "login" && (
        <LoginModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={() => {
            setOpenModal(null);
            setError(null);
          }}
          onSubmit={(email, password) => {
            handleLogin(email, password);
          }}
          errorMessage={error}
        />
      )}

      {/* Set Register modal */}
      {openModal?.type === "register" && (
        <RegisterModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={() => {
            setOpenModal(null);
            setError(null);
          }}
          onSubmit={(username, email, password) => {
            handleRegister(username, email, password);
          }}
          errorMessage={error}
        />
      )}

      {/* Set Guest modal */}
      {openModal?.type === "guest" && (
        <GuestModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={() => {
            setOpenModal(null);
            setError(null);
          }}
          onSubmit={(pseudo) => {
            console.log(`Joueur ${openModal.playerNumber} invité :`, pseudo);
            setOpenModal(null);
          }}
        />
      )}
    </main>
  );
}
