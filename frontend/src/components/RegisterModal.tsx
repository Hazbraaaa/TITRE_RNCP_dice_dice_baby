import Modal from "./Modal";
import { useState } from "react";

type RegisterModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (name: string, email: string, password: string) => void;
};

export default function RegisterModal({
    isOpen,
    onClose,
    onSubmit,
}: RegisterModalProps) {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4">Inscription</h2>
            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit(name, email, password);
                }}
                className="flex flex-col gap-4"
            >
                <input
                    type="text"
                    placeholder="Nom"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
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
