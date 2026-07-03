// ---------- REQUESTS TO BACKEND API ----------
const apiUrl = import.meta.env.VITE_API_URL;

export async function rollDices(payload: {
  gameId: number;
  keptDiceIds: number[];
}) {
  try {
    // Send request with body to API to roll the dice
    const response = await fetch(`${apiUrl}/game/roll`, {
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
    console.error('Erreur dans rollDices:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}

export async function endTurn(payload: { gameId: number; gameCardId: number }) {
  try {
    // Send request with body to API to end the turn
    const response = await fetch(`${apiUrl}/game/end-turn`, {
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
    console.error('Erreur dans endTurn:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}

export async function skipTurn(payload: { gameId: number }) {
  try {
    // Send request with body to API to skip the turn
    const response = await fetch(`${apiUrl}/game/skip-turn`, {
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
    console.error('Erreur dans skipTurn:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}

export async function leaveGame() {
  try {
    // Send request to API to leave the game
    const response = await fetch(`${apiUrl}/game/leave`, {
      method: 'POST',
      credentials: 'include',
    });

    // Verify response status, throw error if not ok
    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(
        errorBody.message || `Erreur serveur: ${response.status}`
      );
    }
  } catch (error: any) {
    // Log the error for debugging purposes
    console.error('Erreur dans leaveGame:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}