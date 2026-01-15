import Modal from "./Modal";
import { useState } from "react";

type RegisterModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (username: string, email: string, password: string) => void;
    errorMessage?: string | null;
};

export default function RegisterModal({
    isOpen,
    onClose,
    onSubmit,
    errorMessage,
}: RegisterModalProps) {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4">Inscription</h2>

            {/* Display error message */}
            {errorMessage && (
                <div className="mx-auto mb-4 p-2 bg-red-100 border border-red-400 text-red-700 rounded text-sm">
                {errorMessage}
                </div>
            )}

            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit(username, email, password);
                }}
                className="flex flex-col gap-4"
            >
                <input
                    type="text"
                    placeholder="Nom"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="border px-3 py-2 rounded"
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="border px-3 py-2 rounded"
                />
                <input
                    type="password"
                    placeholder="Mot de passe"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="border px-3 py-2 rounded"
                />
                <button type="submit" className="bg-blue-500 text-white rounded py-2">
                    Créer un compte
                </button>
            </form>
        </Modal>
    );
}
