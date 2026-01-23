import React from 'react';

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    children: React.ReactNode;
}

export default function Modal({ isOpen, onClose, children }: ModalProps) {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-midnight-ice/80 backdrop-blur-sm p-4">
            <div className="bg-frost-white rounded-sm border-4 border-polar-blue shadow-[8px_8px_0px_0px_rgba(1,54,89,1)] p-6 w-full max-w-md relative animate-in zoom-in duration-200">
                <button
                    onClick={onClose}
                    className="absolute -top-3 -right-3 w-10 h-10 bg-red-alert text-frost-white rounded-full border-2 border-midnight-ice font-bold hover:scale-110 transition-transform shadow-md flex items-center justify-center"
                    aria-label="Close modal"
                >
                    ✕
                </button>
                {children}
            </div>
        </div>
    );
}