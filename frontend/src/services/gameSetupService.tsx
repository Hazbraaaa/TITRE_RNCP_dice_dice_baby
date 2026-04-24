// ---------- REQUESTS TO BACKEND API ----------
const apiUrl = import.meta.env.VITE_API_URL;

export async function getBoard() {
  try {
    // Send request with body to API to register a new user
    const response = await fetch(`${apiUrl}/game/setup`, {
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

    // Parse and return response data
    const data = await response.json();
    return data;
  } catch (error: any) {
    // Log the error for debugging purposes
    console.error('Erreur dans getBoard:', error.message);

    // Rethrow the error to be handled by the caller React component
    throw error;
  }
}
