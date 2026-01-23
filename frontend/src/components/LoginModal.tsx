import Modal from "./Modal";
import { useState } from "react";

type LoginModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (email: string, password: string) => void;
    errorMessage?: string | null;
};

export default function LoginModal({
    isOpen,
    onClose,
    onSubmit,
    errorMessage,
}: LoginModalProps) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const inputStyle = "bg-white border-2 border-polar-blue/30 p-3 rounded-md focus:border-polar-blue focus:outline-none font-bold text-midnight-ice placeholder:text-snow-ashes/50 transition-colors";

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <h2 className="text-3xl font-heading text-polar-blue mb-6 text-center uppercase tracking-tight">
                Connexion
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
                    onSubmit(email, password);
                }}
                className="flex flex-col gap-4"
            >
                {/* Email input */}
                <div className="flex flex-col gap-1">
                    <label className="font-heading text-xs text-midnight-ice/70 ml-1">EMAIL</label>
                    <input
                        type="email"
                        placeholder="joueur@exemple.com"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className={inputStyle}
                        required
                    />
                </div>

                {/* Password input */}
                <div className="flex flex-col gap-1">
                    <label className="font-heading text-xs text-midnight-ice/70 ml-1">MOT DE PASSE</label>
                    <input
                        type="password"
                        placeholder="••••••••"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className={inputStyle}
                        required
                    />
                </div>

                {/* Validation button */}
                <button 
                    type="submit" 
                    className="mt-4 bg-polar-blue text-frost-white font-heading py-4 rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] hover:bg-midnight-ice active:translate-y-1 active:shadow-none transition-all uppercase text-xl"
                >
                    SE CONNECTER
                </button>
            </form>
        </Modal>
    );
}
