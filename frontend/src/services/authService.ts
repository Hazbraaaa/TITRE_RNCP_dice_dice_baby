// ---------- REQUESTS TO BACKEND API ----------
const apiUrl = import.meta.env.VITE_API_URL;

/**
 * Registers a new account and connects its player.
 *
 * @param payload The registration data.
 * @returns The newly connected player.
 * @throws If the registration request fails.
 */
export async function registerUser(payload: {
  username: string;
  email: string;
  password: string;
  playerNumber: number | undefined;
}) {
  try {
    // Send request with body to API to register a new user
    const response = await fetch(`${apiUrl}/auth/register`, {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    // Verify response status, throw error if not ok
    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }

    // Parse and return response data
    const data = await response.json();
    return data;
  } catch (error: any) {
    // Log the error for debugging purposes
    console.error('Erreur dans registerUser:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}

/**
 * Authenticates an account and connects its player.
 *
 * @param payload The login credentials and player number.
 * @returns The connected player.
 * @throws If authentication fails.
 */
export async function loginUser(payload: {
  email: string;
  password: string;
  playerNumber: number | undefined;
}) {
  try {
    // Send request with body to API to login user
    const response = await fetch(`${apiUrl}/auth/login`, {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    // Verify response status, throw error if not ok
    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }

    // Parse and return response data
    const data = await response.json();
    return data;
  } catch (error: any) {
    // Log the error for debugging purposes
    console.error('Erreur dans loginUser:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}

/**
 * Creates and connects a guest player.
 *
 * @param payload The guest username and player number.
 * @returns The newly connected guest.
 * @throws If the guest creation request fails.
 */
export async function guestUser(payload: {
  username: string;
  playerNumber: number | undefined;
}) {
  try {
    // Send request with body to API to add a guest user
    const response = await fetch(`${apiUrl}/auth/guest`, {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    // Verify response status, throw error if not ok
    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }

    // Parse and return response data
    const data = await response.json();
    return data;
  } catch (error: any) {
    // Log the error for debugging purposes
    console.error('Erreur dans guestUser:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}

/**
 * Retrieves the players connected to the current session.
 *
 * @returns The connected players.
 * @throws If the session request fails.
 */
export async function fetchSession() {
  try {
    // Request current session based on HttpOnly cookies
    const response = await fetch(`${apiUrl}/auth/session`, {
      method: 'GET',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
    });

    // Verify response status, throw error if not ok
    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }

    // Parse and return the list of players
    const data = await response.json();
    return data;
  } catch (error: any) {
    // Log the error for debugging purposes
    console.error('Erreur dans fetchSession:', error.message);

    // Rethrow the error to be handled by the caller (the useEffect in usePartyAuth)
    throw error;
  }
}

/**
 * Disconnects a player from the current session.
 *
 * @param payload The player logout data.
 * @throws If the logout request fails.
 */
export async function logoutUser(payload: {
  username: string;
  playerNumber: number | undefined;
}) {
  try {
    const response = await fetch(`${apiUrl}/auth/logout`, {
      method: 'DELETE',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }

    if (response.status === 200) {
      return null;
    }

    return await response.json();
  } catch (error: any) {
    console.error('Erreur dans logoutUser:', error.message);
    throw error;
  }
}

/**
 * Updates the authenticated account.
 *
 * @param payload The account fields to update and current password.
 * @returns The updated player.
 * @throws If authentication or the update request fails.
 */
export async function updateUser(payload: {
  username: string;
  email: string;
  newPassword: string;
  currentPassword: string;
  playerNumber: number | undefined;
}) {
  try {
    const sanitizedData = Object.fromEntries(
      Object.entries(payload).map(([key, value]) => [
        key,
        value === '' ? null : value,
      ])
    );

    const response = await fetch(`${apiUrl}/auth/update`, {
      method: 'PUT',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(sanitizedData),
    });

    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }

    // Parse and return response data
    const data = await response.json();
    return data;
  } catch (error: any) {
    console.error('Erreur dans updateUser:', error.message);
    throw error;
  }
}

/**
 * Deletes the authenticated account.
 *
 * @param payload The deletion data.
 * @returns The result of the deletion.
 * @throws If the deletion request fails.
 */
export async function deleteUser(payload: {
  username: string;
  password: string;
  playerNumber: number | undefined;
}) {
  try {
    const response = await fetch(`${apiUrl}/auth/delete`, {
      method: 'DELETE',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }

    if (response.status === 200) {
      return null;
    }

    return await response.json();
  } catch (error: any) {
    console.error('Erreur dans deleteUser:', error.message);
    throw error;
  }
}

// ---------- LOCAL STORAGE ----------
export interface AuthenticatedPlayer {
  playerId: number;
  username: string;
  playerNumber: number;
  isGuest: boolean;
}

export const STORAGE_KEY = 'DDB_lobby_players';

/**
 * Saves or updates a player in local storage.
 *
 * @param player The authenticated player to save.
 */
export const savePlayerToLocalStorage = (player: AuthenticatedPlayer) => {
  // Get existing players from local storage
  const existingPlayers = getPlayersFromLocalStorage();

  // Remove player if already exists (by id)
  const filteredPlayers = existingPlayers.filter(
    (p) => p.playerId !== player.playerId
  );

  // Update the players
  const updatedPlayers = filteredPlayers.filter(
    (p) => p.playerNumber !== player.playerNumber
  );

  // Add player
  updatedPlayers.push(player);

  // Save in local storage
  localStorage.setItem(STORAGE_KEY, JSON.stringify(updatedPlayers));
};

/**
 * Retrieves the players stored locally.
 *
 * @returns The stored players, or an empty array if none are available.
 */
export const getPlayersFromLocalStorage = (): AuthenticatedPlayer[] => {
  const data = localStorage.getItem(STORAGE_KEY);

  // Return players or empty array if none
  return data ? JSON.parse(data) : [];
};

/**
 * Removes all locally stored players.
 */
export const clearPlayers = () => {
  // Remove all players from local storage
  localStorage.removeItem(STORAGE_KEY);
};

/**
 * Removes a player from local storage.
 *
 * @param playerNumber The number of the player to remove.
 */
export const deletePlayerFromLocalStorage = (playerNumber: number) => {
  const players = getPlayersFromLocalStorage();
  // Keep only players without the specified player
  const updatedPlayers = players.filter((p) => p.playerNumber !== playerNumber);

  if (updatedPlayers.length === 0) {
    localStorage.removeItem(STORAGE_KEY);
  } else {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(updatedPlayers));
  }
};
