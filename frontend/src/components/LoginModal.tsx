import Modal from "./Modal";
import { useState } from "react";
import { Button } from "./Button";

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
    const [showPassword, setShowPassword] = useState(false);

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
                    <div className="relative flex items-center">
                        <input
                            type={showPassword ? "text" : "password"}
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className={`${inputStyle} w-full pr-12`}
                            required
                        />
                        <button
                            type="button"
                            onClick={() => setShowPassword(!showPassword)}
                            className="absolute right-3 p-1 text-polar-blue/60 hover:text-polar-blue transition-colors focus:outline-none"
                        >
                            {showPassword ? (
                                /* Hide icon */
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="w-5 h-5">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M3.98 8.223A10.477 10.477 0 001.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.45 10.45 0 0112 4.5c4.756 0 8.773 3.162 10.065 7.498a10.523 10.523 0 01-4.293 5.774M6.228 6.228L3 3m3.228 3.228l3.65 3.65m7.894 7.894L21 21m-3.228-3.228l-3.65-3.65m0 0a3 3 0 10-4.243-4.243m4.242 4.242L9.88 9.88" />
                                </svg>
                            ) : (
                                /* Show icon */
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="w-5 h-5">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z" />
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                                </svg>
                            )}
                        </button>
                    </div>
                </div>

                {/* Validation button */}
                <Button
                    type="submit"
                    fullWidth
                    className="mt-4 py-3 text-sm"
                >
                    SE CONNECTER
                </Button>
            </form>
        </Modal>
    );
}
