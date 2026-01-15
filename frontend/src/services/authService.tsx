const API_URL = "http://localhost:8080/api";

export async function registerUser(userData: { 
    username: string; 
    email: string; 
    password: string 
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