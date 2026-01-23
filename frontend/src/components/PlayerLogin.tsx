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
  const btnStyle = "flex items-center justify-center gap-2 w-full py-2 px-4 font-heading text-sm md:text-base uppercase transition-all duration-200 rounded-sm border-2 border-midnight-ice shadow-[3px_3px_0px_0px_rgba(1,54,89,1)] active:translate-y-[2px] active:shadow-none";

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
        <button
          onClick={onLogin}
          className={`${btnStyle} bg-polar-blue text-frost-white hover:bg-midnight-ice`}
        >
          Se connecter
        </button>

        <div className="grid grid-cols-2 gap-2">
          {/* Register button */}
          <button
            onClick={onRegister}
            className={`${btnStyle} bg-white text-polar-blue border-polar-blue hover:bg-polar-blue/10`}
          >
            S'inscrire
          </button>

          {/* Guest button */}
          <button
            onClick={onGuest}
            className={`${btnStyle} bg-snow-ashes text-frost-white border-midnight-ice hover:bg-midnight-ice`}
          >
            Invité
          </button>
        </div>
      </div>
    </div>
  );
}