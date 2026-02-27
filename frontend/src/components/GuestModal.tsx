import Modal from "./Modal";
import { useState } from "react";
import { Button } from "./Button";

type GuestModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (username: string) => void;
    errorMessage?: string | null;
};

export default function GuestModal({
    isOpen,
    onClose,
    onSubmit,
    errorMessage,
}: GuestModalProps) {
    const [username, setUsername] = useState("");

    const inputStyle = "bg-white border-2 border-polar-blue/30 p-3 rounded-md focus:border-polar-blue focus:outline-none font-bold text-midnight-ice placeholder:text-snow-ashes/50 transition-colors";

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-3xl font-heading text-polar-blue mb-6 text-center uppercase tracking-tight">
                Invité
            </h2>

            {/* Display error message */}
            {errorMessage && (
                <div className="mb-4 p-3 bg-red-alert/10 border-2 border-red-alert text-red-alert rounded-md text-xs font-bold uppercase text-center animate-shake">
                    ⚠️ {errorMessage}
                </div>
            )}

            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit(username);
                }}
                className="flex flex-col gap-4"
            >
                {/* Username input */}
                <div className="flex flex-col gap-1">
                    <label className="font-heading text-xs text-midnight-ice/70 ml-1">PSEUDO</label>
                    <input
                        type="text"
                        placeholder="Ton nom de joueur"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className={inputStyle}
                        required
                    />
                </div>

                {/* Validation button */}
                <Button
                    type="submit"
                    fullWidth
                    className="mt-4 py-3 text-sm"
                >
                    S'INVITER
                </Button>
            </form>
        </Modal>
    );
}
