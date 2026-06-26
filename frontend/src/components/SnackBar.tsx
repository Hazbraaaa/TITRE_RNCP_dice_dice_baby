import { useEffect } from 'react';

interface SnackbarProps {
  message: string | null;
  onClose: () => void;
  duration?: number;
}

export default function Snackbar({
  message,
  onClose,
  duration = 4000,
}: SnackbarProps) {
  // Use effect to automatically close the snackbar after the specified duration
  useEffect(() => {
    if (!message) return;

    const timer = setTimeout(() => {
      onClose();
    }, duration);

    // Cleanup the timer when the component unmounts or when message/duration changes
    return () => clearTimeout(timer);
  }, [message, duration, onClose]);

  // If there's no message, don't render the snackbar
  if (!message) return null;

  return (
    <div className="fixed top-5 left-1/2 -translate-x-1/2 z-50 w-11/12 max-w-md">
      {/* Snackbar content */}
      <div className="bg-red-alert text-white border-[3px] border-midnight-ice font-heading text-sm uppercase px-4 py-3 rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] flex items-center justify-between gap-3 animate-fade-in-up">
        <div className="flex-1 tracking-wide">{message}</div>

        {/* Close button */}
        <button
          onClick={onClose}
          className="hover:scale-110 active:scale-95 transition-transform font-bold border-l-2 border-white/30 pl-3 h-full self-center"
        >
          ✕
        </button>
      </div>
    </div>
  );
}
