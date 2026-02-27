import { useSearchParams } from "react-router-dom";
import PlayerLogin from "../components/PlayerLogin";
import LoginModal from "../components/LoginModal";
import RegisterModal from "../components/RegisterModal";
import GuestModal from "../components/GuestModal";
import { ButtonLink } from "../components/ButtonLink";
import { usePartyAuth } from "../hooks/usePartyAuth";
import { Button } from "../components/Button";

export default function PartyLauncher() {
  const [searchParams] = useSearchParams();
  const playersCount = Number(searchParams.get("players"));
  const isValidPlayersCount = playersCount >= 2 && playersCount <= 4;

  const { connectedPlayers, successMessage, openModal, setOpenModal, closeModal, error, logout, login, register, guest } = usePartyAuth();


  // ---------- Render ----------
  return (
    <main className="flex flex-col items-center justify-between min-h-[92vh] py-6 px-4 max-w-5xl mx-auto">

      <header className="w-full text-center mb-6">
        <h2 className="text-3xl md:text-5xl font-heading text-polar-blue uppercase drop-shadow-sm">
          Identification
        </h2>
        <div className="h-1 w-20 bg-red-alert mx-auto mt-2 rounded-full" />
      </header>

      {/* Floatting success message */}
      {successMessage && (
        <div className="fixed top-20 left-1/2 -translate-x-1/2 z-50 w-full max-w-xs animate-bounce">
          <div className="bg-frost-white border-2 border-polar-blue p-3 text-midnight-ice font-heading text-center rounded-lg shadow-xl">
            ✅ {successMessage}
          </div>
        </div>
      )}

      {isValidPlayersCount ? (
        <div className="w-full flex flex-col flex-grow justify-center gap-8">

          {/* Players grid : 1 col on mobile, 2 on tablet/desktop */}
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
                      <Button
                        onClick={() => logout(currentPlayer.username, currentPlayer.playerNumber)}
                        variant="warning"
                        className="p-2 shadow-none"
                      >
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                          <path fillRule="evenodd" d="M3 3a1 1 0 00-1 1v12a1 1 0 102 0V4a1 1 0 00-1-1zm10.293 9.293a1 1 0 001.414 1.414l3-3a1 1 0 000-1.414l-3-3a1 1 0 10-1.414 1.414L14.586 9H7a1 1 0 100 2h7.586l-1.293 1.293z" clipRule="evenodd" />
                        </svg>
                      </Button>
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
              fullWidth
              className="max-w-md py-5 text-2xl md:text-4xl"
            >
              LANCER PARTIE
            </ButtonLink>

            <ButtonLink
              to="/"
              variant="outlined"
              className="px-6 py-2 text-[10px] md:text-sm"
            >
              RETOUR ACCUEIL
            </ButtonLink>
          </div>
        </div>
      ) : (
        <div className="text-center p-10 bg-red-100 rounded-xl border-2 border-red-alert flex flex-col gap-6">
          <p className="text-red-alert font-heading text-xl">Nombre de joueurs invalide.</p>
          <ButtonLink
            variant="outlined"
            to="/"
            className="px-4"
          >
            RETOUR
          </ButtonLink>
        </div>
      )}

      {/* Set login modal */}
      {openModal?.type === "login" && (
        <LoginModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={closeModal}
          onSubmit={login}
          errorMessage={error}
        />
      )}

      {/* Set Register modal */}
      {openModal?.type === "register" && (
        <RegisterModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={closeModal}
          onSubmit={register}
          errorMessage={error}
        />
      )}

      {/* Set Guest modal */}
      {openModal?.type === "guest" && (
        <GuestModal
          playerNumber={openModal.playerNumber}
          isOpen
          onClose={closeModal}
          onSubmit={guest}
          errorMessage={error}
        />
      )}
    </main>
  );
}
