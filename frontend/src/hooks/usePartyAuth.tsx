import { useState, useEffect } from "react";
import { registerUser, loginUser, guestUser, getPlayersFromLocalStorage, fetchSession, STORAGE_KEY, logoutUser, type AuthenticatedPlayer } from "../services/authService";

type ModalType = "login" | "register" | "guest";
interface OpenModalState {
    playerNumber: number;
    type: ModalType;
}

export const usePartyAuth = () => {
    const [connectedPlayers, setConnectedPlayers] = useState(getPlayersFromLocalStorage());
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
            } catch (err) {
                console.log("Mode offline ou serveur injoignable");
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
    const handleAuthAction = async (action: () => Promise<any>, successText: string) => {
        try {
            setError(null);
            const result = await action();

            // Get current local players to determine how to update after action
            const currentLocal = getPlayersFromLocalStorage();
            let finalPlayers: AuthenticatedPlayer[] = [];

            // For logout, we need to remove the player from local storage and then fetch the updated session data from server to ensure sync
            if (successText.includes("déconnecté")) {
                const serverPlayers = await fetchSession();
                const localGuests = currentLocal.filter(p => p.isGuest);
                finalPlayers = serverPlayers.concat(localGuests);
            }
            // For login/register/guest, add new player if not already in local storage
            else if (result) {
                const isAlreadyThere = currentLocal.some(p => p.username === result.username);
                finalPlayers = isAlreadyThere ? currentLocal : currentLocal.concat(result);
            }

            // Update local storage and state
            localStorage.setItem(STORAGE_KEY, JSON.stringify(finalPlayers));
            setConnectedPlayers(finalPlayers);

            setOpenModal(null);
            notify(successText);

        } catch (err: any) {
            setError(err.message);
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
        login: (e: string, p: string) => handleAuthAction(() => loginUser({ email: e, password: p, playerNumber: openModal?.playerNumber }), `Joueur connecté !`),
        register: (u: string, e: string, p: string) => handleAuthAction(() => registerUser({ username: u, email: e, password: p, playerNumber: openModal?.playerNumber }), `Compte créé !`),
        guest: (u: string) => handleAuthAction(() => guestUser({ username: u, playerNumber: openModal?.playerNumber }), `Invité ajouté !`),
        logout: (u: string, n: number) => handleAuthAction(() => logoutUser({ username: u, playerNumber: n }), `Joueur déconnecté !`)
    };
};