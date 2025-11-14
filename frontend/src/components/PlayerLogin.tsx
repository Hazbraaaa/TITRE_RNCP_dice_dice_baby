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
    <div className="flex items-start gap-4">
      <label className="text-lg font-medium">Joueur {playerNumber} :</label>
      <div className="flex flex-col gap-2">
        <button
          onClick={onLogin}
          className="bg-gray-300 hover:bg-gray-400 text-black py-1 px-2 rounded"
        >
          Se connecter
        </button>
        <button
          onClick={onRegister}
          className="bg-gray-300 hover:bg-gray-400 text-black py-1 px-2 rounded"
        >
          Créer un compte
        </button>
        <button
          onClick={onGuest}
          className="bg-gray-300 hover:bg-gray-400 text-black py-1 px-2 rounded"
        >
          Jouer en tant qu'invité
        </button>
      </div>
    </div>
  );
}
