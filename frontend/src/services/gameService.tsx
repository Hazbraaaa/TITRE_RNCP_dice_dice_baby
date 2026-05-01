// ---------- REQUESTS TO BACKEND API ----------
const apiUrl = import.meta.env.VITE_API_URL;

export async function rollDices(payload: {
  diceSetId: number | null;
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
