import Modal from "./Modal";
import { useState } from "react";

type GuestModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (name: string) => void;
};

export default function GuestModal({
    isOpen,
    onClose,
    onSubmit,
}: GuestModalProps) {
    const [name, setName] = useState("");

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-xl font-bold mb-4">Invité</h2>
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
