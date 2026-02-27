import { Button } from "./Button";

type PlayerLoginProps = {
  playerNumber: number;
  onLogin: () => void;
  onRegister: () => void;
  onGuest: () => void;
};

export default function PlayerLogin({
  playerNumber,
  onLogin,
  onRegister,
  onGuest,
}: PlayerLoginProps) {

  return (
    <div className="bg-frost-white/80 border-2 border-dashed border-polar-blue p-4 rounded-sm flex flex-col gap-4 animate-in fade-in slide-in-from-bottom-2 duration-500">
      
      {/* Waiting for player */}
      <div className="flex items-center gap-2">
        <div className="w-3 h-3 rounded-full bg-snow-ashes animate-pulse" />
        <span className="text-xs font-bold text-midnight-ice/60 uppercase tracking-widest">
          En attente du joueur...
        </span>
      </div>

      <div className="grid grid-cols-1 gap-3">
        {/* Login button */}
        <Button
          fullWidth
          className="py-3 text-sm shadow-none"
          onClick={onLogin}
        >
          Se connecter
        </Button>

        <div className="grid grid-cols-2 gap-2">
          {/* Register button */}
          <Button
            variant="outlined"
            fullWidth
            className="py-3 text-sm"
            onClick={onRegister}
          >
            S'inscrire
          </Button>

          {/* Guest button */}
          <Button
            variant="disabled"
            fullWidth
            className="py-3 text-sm !cursor-pointer !opacity-100 hover:bg-midnight-ice"
            onClick={onGuest}
          >
            Invité
          </Button>
        </div>
      </div>
    </div>
  );
}