// ---------- REQUESTS TO BACKEND API ----------
const API_URL = "http://localhost:8080/api";

export async function registerUser(userData: { 
    username: string; 
    email: string; 
    password: string;
    playerNumber: number | undefined;
}) {
    try {
        // Send request with body to API to register a new user
        const response = await fetch(`${API_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json",},
            body: JSON.stringify(userData),
        });

        // Verify response status, throw error if not ok
        if (!response.ok) {
            const errorBody = await response.json().catch(() => ({}));
            throw new Error(errorBody.message || `Erreur serveur: ${response.status}`);
        }

        // Parse and return response data
        const data = await response.json();
        return data;
        
    }
    catch (error: any) {
        // Log the error for debugging purposes
        console.error("Erreur dans registerUser:", error.message);
        
        // Rethrow the error to be handled by the caller React component
        throw error;
    }
}

export async function loginUser(userData: { 
    email: string; 
    password: string;
    playerNumber: number | undefined;
}) {
    try {
        // Send request with body to API to login user
        const response = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json",},
            body: JSON.stringify(userData),
        });

        // Verify response status, throw error if not ok
        if (!response.ok) {
            const errorBody = await response.json().catch(() => ({}));
            throw new Error(errorBody.message || `Erreur serveur: ${response.status}`);
        }

        // Parse and return response data
        const data = await response.json();
        return data;
        
    }
    catch (error: any) {
        // Log the error for debugging purposes
        console.error("Erreur dans loginUser:", error.message);
        
        // Rethrow the error to be handled by the caller React component
        throw error;
    }
}

// ---------- LOCAL STORAGE ----------
export interface AuthenticatedPlayer {
    playerNumber: number;
    username: string;
    score: number;
    token: string;
}

const STORAGE_KEY = "game_players";

export const savePlayerToLocalStorage = (player: AuthenticatedPlayer) => {
    // Get existing players from local storage
    const existingPlayers = getPlayersFromLocalStorage();
    
    // Update or add the player
    const updatedPlayers = existingPlayers.filter(p => p.playerNumber !== player.playerNumber);
    updatedPlayers.push(player);
    
    // Save in local storage
    localStorage.setItem(STORAGE_KEY, JSON.stringify(updatedPlayers));
};

export const getPlayersFromLocalStorage = (): AuthenticatedPlayer[] => {
    const data = localStorage.getItem(STORAGE_KEY);

    // Return players or empty array if none
    return data ? JSON.parse(data) : [];
};

export const clearPlayers = () => {
    // Remove all players from local storage
    localStorage.removeItem(STORAGE_KEY);
};

export const deletePlayerFromLocalStorage = (playerNumber: number) => {
    const players = getPlayersFromLocalStorage();
    // Keep only players without the specified player
    const updatedPlayers = players.filter(p => p.playerNumber !== playerNumber);
    
    if (updatedPlayers.length === 0) {
        localStorage.removeItem("game_players");
    } else {
        localStorage.setItem("game_players", JSON.stringify(updatedPlayers));
    }
};