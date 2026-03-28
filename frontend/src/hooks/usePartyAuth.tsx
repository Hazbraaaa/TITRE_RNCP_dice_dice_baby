import { useState, useEffect } from 'react';
import {
  registerUser,
  loginUser,
  guestUser,
  getPlayersFromLocalStorage,
  fetchSession,
  logoutUser,
  deleteUser,
  STORAGE_KEY,
  type AuthenticatedPlayer,
} from '../services/authService';

type ModalType = 'login' | 'register' | 'guest' | 'account';
interface OpenModalState {
  playerNumber: number;
  type: ModalType;
}

export const usePartyAuth = () => {
  const [connectedPlayers, setConnectedPlayers] = useState(
    getPlayersFromLocalStorage()
  );
  const [openModal, setOpenModal] = useState<OpenModalState | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  // On mount, sync local storage with server session data
  useEffect(() => {
    const syncWithServer = async () => {
      try {
        // Fetch current session players from server (if any)
        const serverPlayers = await fetchSession();
        if (serverPlayers && serverPlayers.length > 0) {
          // Update local storage and state with server data
          localStorage.setItem(STORAGE_KEY, JSON.stringify(serverPlayers));
          setConnectedPlayers(serverPlayers);
        }
      } catch (error) {
        console.log('Mode offline ou serveur injoignable');
      }
    };
    syncWithServer();
  }, []);

  // Display notification message
  const notify = (msg: string) => {
    setSuccessMessage(msg);
    setTimeout(() => setSuccessMessage(null), 3000);
  };

  // Close any open modal and clear error messages
  const closeModal = () => {
    setOpenModal(null);
    setError(null);
  };

  // Handle authentication actions (login, register, guest) with common logic
  const handleAuthAction = async (
    action: () => Promise<any>,
    successText: string,
    playerNumber?: number
  ) => {
    try {
      setError(null);
      const result = await action();

      // Get current local players to determine how to update after action
      const currentLocal = getPlayersFromLocalStorage();
      let finalPlayers: AuthenticatedPlayer[] = [];

      // For logout/delete, if success text indicates disconnection or deletion, remove player from local storage and sync with server
      if (
        successText.includes('déconnecté') ||
        successText.includes('supprimé')
      ) {
        // Get players from server to ensure we have the most up-to-date list after removal
        const serverPlayers = await fetchSession();
        // Find guest players in local storage that are not the one being removed
        const localGuests = currentLocal.filter(
          (p) => p.isGuest && p.playerNumber !== playerNumber
        );
        // Combine server players with remaining local guests (if any)
        finalPlayers = serverPlayers.concat(localGuests);
      }

      // For login/register/guest, add new player if not already in local storage
      else if (result) {
        const isAlreadyThere = currentLocal.some(
          (p) => p.playerId === result.playerId
        );
        finalPlayers = isAlreadyThere
          ? currentLocal
          : currentLocal.concat(result);
      }

      // Update local storage and state
      localStorage.setItem(STORAGE_KEY, JSON.stringify(finalPlayers));
      setConnectedPlayers(finalPlayers);

      setOpenModal(null);
      notify(successText);
    } catch (error: any) {
      setError(error.message);
    }
  };

  // ---------- Render ----------
  return {
    connectedPlayers,
    openModal,
    setOpenModal,
    closeModal,
    error,
    successMessage,
    login: (e: string, p: string) =>
      handleAuthAction(
        () =>
          loginUser({
            email: e,
            password: p,
            playerNumber: openModal?.playerNumber,
          }),
        `Joueur connecté !`
      ),
    register: (u: string, e: string, p: string) =>
      handleAuthAction(
        () =>
          registerUser({
            username: u,
            email: e,
            password: p,
            playerNumber: openModal?.playerNumber,
          }),
        `Compte créé !`
      ),
    guest: (u: string) =>
      handleAuthAction(
        () => guestUser({ username: u, playerNumber: openModal?.playerNumber }),
        `Invité ajouté !`
      ),
    logout: (u: string, n: number) =>
      handleAuthAction(
        () => logoutUser({ username: u, playerNumber: n }),
        `Joueur déconnecté !`,
        n
      ),
    delete: (u: string, p: string) =>
      handleAuthAction(
        () =>
          deleteUser({
            username: u,
            password: p,
            playerNumber: openModal?.playerNumber,
          }),
        `Compte supprimé !`
      ),
  };
};
