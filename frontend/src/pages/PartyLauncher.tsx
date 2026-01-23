import { useSearchParams } from "react-router-dom";
import { useState } from "react";
import PlayerLogin from "../components/PlayerLogin";
import LoginModal from "../components/LoginModal";
import RegisterModal from "../components/RegisterModal";
import GuestModal from "../components/GuestModal";
import { ButtonLink } from "../components/ButtonLink";
import { registerUser, loginUser, guestUser, savePlayerToLocalStorage, getPlayersFromLocalStorage, deletePlayerFromLocalStorage } from "../services/authService";

type ModalType = "login" | "register" | "guest";

interface OpenModalState {
  playerNumber: number;
  type: ModalType;
}

export default function PartyLauncher() {
  const [searchParams] = useSearchParams();
  const playersCount = Number(searchParams.get("players"));
  const isValidPlayersCount = playersCount >= 2 && playersCount <= 4;

  const [connectedPlayers, setConnectedPlayers] = useState(getPlayersFromLocalStorage());
  const [openModal, setOpenModal] = useState<OpenModalState | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  // Handle authentication success update
  const handleAuthSuccess = (playerData: any) => {
    savePlayerToLocalStorage(playerData);
    setConnectedPlayers(getPlayersFromLocalStorage());
    setOpenModal(null);
  };

  // Handle user login
  const handleLogin = async (email: string, password: string) => {
    try {
      // Reinitialize error state
      setError(null);

      // Get player number from modal
      const playerNumber = openModal?.playerNumber;

      // Send login request
      const userData = { email, password, playerNumber };
      const response = await loginUser(userData);

      // Save player in local strorage and update state
      handleAuthSuccess({
        playerNumber: response.playerNumber,
        username: response.username,
        score: response.score,
        token: response.token
      });

      // Log response, then close the modal upon successful registration
      console.log(`Joueur ${response.playerNumber} connecté :`, response);
      setOpenModal(null);

      // Show success message
      setSuccessMessage(`Joueur ${response.username} connecté avec succès !`);
      setTimeout(() => setSuccessMessage(null), 3000);
    }
    catch (err: any) {
      // Handle login error, keep the modal open to let user correct inputs
      setError(err.message);
    }
  };

  // Handle user registration
  const handleRegister = async (username: string, email: string, password: string) => {
    try {
      // Reinitialize error state
      setError(null);

      // Get player number from modal
      const playerNumber = openModal?.playerNumber;

      // Send register request
      const userData = { username, email, password, playerNumber };
      const response = await registerUser(userData);

      // Save player in local strorage and update state
      handleAuthSuccess({
        playerNumber: response.playerNumber,
        username: response.username,
        score: response.score,
        token: response.token
      });

      // Log response, then close the modal upon successful registration
      console.log(`Joueur ${response.playerNumber} inscrit :`, response);
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

  // Handle user guest addition
  const handleGuest = async (username: string) => {
    try {
      // Reinitialize error state
      setError(null);

      // Get player number from modal
      const playerNumber = openModal?.playerNumber;

      // Send guest request
      const userData = { username, playerNumber };
      const response = await guestUser(userData);

      // Save player in local strorage and update state
      handleAuthSuccess({
        playerNumber: response.playerNumber,
        username: response.username,
        score: response.score,
        token: response.token
      });

      // Log response, then close the modal upon successful guest addition
      console.log(`Joueur ${response.playerNumber} invité :`, response);
      setOpenModal(null);

      // Show success message
      setSuccessMessage(`Joueur ${username} ajouté en tant qu'invité avec succès !`);
      setTimeout(() => setSuccessMessage(null), 3000);

    }
    catch (err: any) {
      // Handle guest error, keep the modal open to let user correct inputs
      setError(err.message);
    }
  };

  // Handle user logout
  const handleLogout = (pNumber: number) => {
    deletePlayerFromLocalStorage(pNumber);
    setConnectedPlayers(getPlayersFromLocalStorage());

    // Show success message
    setSuccessMessage(`Joueur ${pNumber} déconnecté.`);
    setTimeout(() => setSuccessMessage(null), 3000);
  };

  // ---------- Render ----------
  return (
    <main className="flex flex-col items-center justify-between min-h-[92vh] py-6 px-4 max-w-5xl mx-auto">
      
      <header className="w-full text-center mb-6">
        <h2 className="text-3xl md:text-5xl font-heading text-polar-blue uppercase drop-shadow-sm">
          Identification
        </h2>
        <div className="h-1 w-20 bg-red-alert mx-auto mt-2 rounded-full" />
      </header>

      {/* Message de succès flottant */}
      {successMessage && (
        <div className="fixed top-20 left-1/2 -translate-x-1/2 z-50 w-full max-w-xs animate-bounce">
          <div className="bg-frost-white border-2 border-polar-blue p-3 text-midnight-ice font-heading text-center rounded-lg shadow-xl">
             ✅ {successMessage}
          </div>
        </div>
      )}

      {isValidPlayersCount ? (
        <div className="w-full flex flex-col flex-grow justify-center gap-8">
          
          {/* Grille des joueurs : 1 col sur mobile, 2 sur tablette/desktop */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 md:gap-6 w-full max-w-4xl mx-auto">
            {Array.from({ length: playersCount }).map((_, index) => {
              const playerNumber = index + 1;
              const currentPlayer = connectedPlayers.find(p => p.playerNumber === playerNumber);

              return (
                <div key={index} className="flex flex-col gap-2">
                  <span className="font-heading text-polar-blue text-sm md:text-lg ml-1">
                    JOUEUR {playerNumber}
                  </span>
                  
                  {currentPlayer ? (
                    /* CONNECTED PLAYER CARD */
                    <div className="p-4 bg-polar-blue text-frost-white rounded-sm border-2 border-midnight-ice shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] flex justify-between items-center animate-in fade-in zoom-in duration-300">
                      <div>
                        <p className="text-xl md:text-2xl font-heading truncate max-w-[150px]">
                          {currentPlayer.username}
                        </p>
                        <p className="text-[10px] md:text-xs opacity-80 font-bold uppercase tracking-widest">
                          Score: {currentPlayer.score}
                        </p>
                      </div>
                      <button
                        onClick={() => handleLogout(playerNumber)}
                        className="bg-red-alert hover:bg-midnight-ice text-white p-2 rounded-md transition-colors"
                        title="Déconnecter"
                      >
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                          <path fillRule="evenodd" d="M3 3a1 1 0 00-1 1v12a1 1 0 102 0V4a1 1 0 00-1-1zm10.293 9.293a1 1 0 001.414 1.414l3-3a1 1 0 000-1.414l-3-3a1 1 0 10-1.414 1.414L14.586 9H7a1 1 0 100 2h7.586l-1.293 1.293z" clipRule="evenodd" />
                        </svg>
                      </button>
                    </div>
                  ) : (
                    /* NOT CONNECTED PLAYER CARD */
                    <PlayerLogin
                      playerNumber={playerNumber}
                      onLogin={() => setOpenModal({ playerNumber, type: "login" })}
                      onRegister={() => setOpenModal({ playerNumber, type: "register" })}
                      onGuest={() => setOpenModal({ playerNumber, type: "guest" })}
                    />
                  )}
                </div>
              );
            })}
          </div>

          {/* Launch party button */}
          <div className="flex flex-col items-center gap-4 mt-auto">
            <ButtonLink
              to={`/game`}
              disabled={connectedPlayers.length < playersCount}
              className="w-full max-w-sm py-5 text-2xl md:text-4xl"
            >
              LANCER PARTIE
            </ButtonLink>
            
            <ButtonLink 
              to="/" 
              className="bg-transparent border-2 border-polar-blue !text-polar-blue !shadow-none py-2 px-8 text-sm md:text-base hover:bg-polar-blue hover:!text-frost-white"
            >
              RETOUR ACCUEIL
            </ButtonLink>
          </div>
        </div>
      ) : (
        <div className="text-center p-10 bg-red-100 rounded-xl border-2 border-red-alert">
          <p className="text-red-alert font-heading text-xl">Nombre de joueurs invalide.</p>
          <ButtonLink to="/" className="mt-4">RETOUR</ButtonLink>
        </div>
      )}

      {/* Set login modal */}
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
          onSubmit={(username) => {
            handleGuest(username);
          }}
          errorMessage={error}
        />
      )}
    </main>
  );
}
