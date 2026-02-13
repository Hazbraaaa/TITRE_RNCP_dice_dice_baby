import { useState } from "react";
import { registerUser, loginUser, guestUser, savePlayerToLocalStorage, getPlayersFromLocalStorage, deletePlayerFromLocalStorage } from "../services/authService";

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
            const response = await action();
            savePlayerToLocalStorage({
                playerNumber: response.playerNumber,
                username: response.username,
                score: response.score,
                token: response.token
            });
            setConnectedPlayers(getPlayersFromLocalStorage());
            setOpenModal(null);
            notify(successText);
        } catch (err: any) {
            setError(err.message);
        }
    };

    // Handle user logout
    const logout = (pNumber: number) => {
        deletePlayerFromLocalStorage(pNumber);
        setConnectedPlayers(getPlayersFromLocalStorage());
        notify(`Joueur ${pNumber} déconnecté.`);
    };

    // ---------- Render ----------
    return {
        connectedPlayers,
        openModal, 
        setOpenModal, 
        closeModal,
        error,
        successMessage,
        logout,
        login: (e: string, p: string) => handleAuthAction(() => loginUser({ email: e, password: p, playerNumber: openModal?.playerNumber }), `Joueur connecté !`),
        register: (u: string, e: string, p: string) => handleAuthAction(() => registerUser({ username: u, email: e, password: p, playerNumber: openModal?.playerNumber }), `Compte créé !`),
        guest: (u: string) => handleAuthAction(() => guestUser({ username: u, playerNumber: openModal?.playerNumber }), `Invité ajouté !`)
    };
};