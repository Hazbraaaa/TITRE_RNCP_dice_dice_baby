import Modal from "./Modal";
import { useState } from "react";

type ConnexionModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (email: string, password: string) => void;
};

export default function ConnexionModal({
    isOpen,
    onClose,
    onSubmit,
}: ConnexionModalProps) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4">Connexion</h2>
            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit(email, password);
                }}
                className="flex flex-col gap-4"
            >
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
                    Se connecter
                </button>
            </form>
        </Modal>
    );
}
