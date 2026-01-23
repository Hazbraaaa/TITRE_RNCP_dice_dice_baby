import Modal from "./Modal";
import { useState } from "react";

type GuestModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (name: string) => void;
    errorMessage?: string | null;
};

export default function GuestModal({
    isOpen,
    onClose,
    onSubmit,
    errorMessage,
}: GuestModalProps) {
    const [name, setName] = useState("");

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4">Invité</h2>

            {/* Display error message */}
            {errorMessage && (
                <div className="mx-auto mb-4 p-2 bg-red-100 border border-red-400 text-red-700 rounded text-sm">
                {errorMessage}
                </div>
            )}

            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit(name);
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
                <button type="submit" className="bg-blue-500 text-white rounded py-2">
                    Valider le nom
                </button>
            </form>
        </Modal>
    );
}
