import { useState } from "react";
import { ButtonLink } from "../components/ButtonLink";
import { usePwaInstall } from "../components/usePwaInstall";

export default function Home() {
  const [playersCount, setPlayersCount] = useState<string>("");
  const { isInstallable, install } = usePwaInstall();

  return (
    <main className="flex flex-col justify-center items-center min-h-screen gap-10 p-4 bg-white">
      <h1 className="text-3xl font-bold text-center">Dice Dice Baby</h1>

      <section className="max-w-md text-center">
        <p className="text-gray-700 leading-relaxed">
          Bienvenue sur <strong>Dice Dice Baby</strong>, un jeu qui mélange le
          <em> Yam's </em> et le <em> Morpion</em>.
        </p>
        <p className="mt-2">
          Choisissez le nombre de joueurs (entre 2 et 4) pour commencer une partie :
        </p>
      </section>

      <div className="flex flex-col gap-4 w-full max-w-xs">
        <label htmlFor="players" className="text-sm font-medium text-gray-600">
          Nombre de joueurs
        </label>
        <select
          id="players"
          className="border border-gray-300 rounded px-3 py-2"
          value={playersCount}
          onChange={(e: React.ChangeEvent<HTMLSelectElement>) =>
            setPlayersCount(e.target.value)
          }
        >
          <option value="">-- Choisir nombre de joueurs --</option>
          <option value="2">2 joueurs</option>
          <option value="3">3 joueurs</option>
          <option value="4">4 joueurs</option>
        </select>

        <ButtonLink
          to={`/party-launcher?players=${playersCount}`}
          disabled={!playersCount}
          className="mt-2"
        >
          Valider
        </ButtonLink>

        {isInstallable && (
          <button
            onClick={install}
            style={{
              padding: "10px 20px",
              fontSize: "16px",
              borderRadius: "10px",
              backgroundColor: "#ff0000",
              color: "#fff",
              border: "none",
              cursor: "pointer",
            }}
          >
            📲 Installer le jeu
          </button>
        )}
      </div>
    </main>
  );
}
