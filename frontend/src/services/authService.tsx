// ---------- REQUESTS TO BACKEND API ----------
const apiUrl = import.meta.env.VITE_API_URL;

export async function registerUser(userData: { 
    username: string; 
    email: string; 
    password: string;
    playerNumber: number | undefined;
}) {
    try {
        // Send request with body to API to register a new user
        const response = await fetch(`${apiUrl}/auth/register`, {
            method: "POST",
            credentials: "include",
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
        const response = await fetch(`${apiUrl}/auth/login`, {
            method: "POST",
            credentials: "include",
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

export async function guestUser(userData: { 
    username: string; 
    playerNumber: number | undefined;
}) {
    try {
        // Send request with body to API to add a guest user
        const response = await fetch(`${apiUrl}/auth/guest`, {
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
        console.error("Erreur dans guestUser:", error.message);
        
        // Rethrow the error to be handled by the caller React component
        throw error;
    }
}

export async function fetchSession() {
    try {
        // Request current session based on HttpOnly cookies
        const response = await fetch(`${apiUrl}/auth/session`, {
            method: "GET",
            credentials: "include",
            headers: { "Content-Type": "application/json" },
        });

        // Verify response status, throw error if not ok
        if (!response.ok) {
            const errorBody = await response.json().catch(() => ({}));
            throw new Error(errorBody.message || `Erreur session: ${response.status}`);
        }

        // Parse and return the list of players
        const data = await response.json();
        return data;

    } catch (error: any) {
        // Log the error for debugging purposes
        console.error("Erreur dans fetchSession:", error.message);
        
        // Rethrow the error to be handled by the caller (the useEffect in usePartyAuth)
        throw error;
    }
}

export async function logoutUser(userData: {
    username: string;  
    playerNumber: number | undefined;
}) {
    try {
        const response = await fetch(`${apiUrl}/auth/logout`, {
            method: "POST",
            credentials: "include",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userData),
        });

        if (!response.ok) {
            const errorBody = await response.json().catch(() => ({}));
            throw new Error(errorBody.message || `Erreur logout: ${response.status}`);
        }

        if (response.status === 200) {
            return null; 
        }

        return await response.json();
    } catch (error: any) {
        console.error("Erreur dans logoutUser:", error.message);
        throw error;
    }
}

// ---------- LOCAL STORAGE ----------
export interface AuthenticatedPlayer {
    playerId: number;
    username: string;
    playerNumber: number;
    score: number;
    isGuest: boolean;
}

export const STORAGE_KEY = "game_players";

export const savePlayerToLocalStorage = (player: AuthenticatedPlayer) => {
    // Get existing players from local storage
    const existingPlayers = getPlayersFromLocalStorage();
    
    // Remove player if already exists (by id)
    const filteredPlayers = existingPlayers.filter(p => p.playerId !== player.playerId)

    // Update the players
    const updatedPlayers = filteredPlayers.filter(p => p.playerNumber !== player.playerNumber);
    
    // Add player
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