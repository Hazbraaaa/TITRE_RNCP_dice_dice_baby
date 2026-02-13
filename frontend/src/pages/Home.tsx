import { useState } from "react";
import { ButtonLink } from "../components/ButtonLink";
import { usePwaInstall } from "../components/usePwaInstall";
import gameLogo from "../assets/logo.png";

export default function Home() {
  const [playersCount, setPlayersCount] = useState<number>(2);
  const { isInstallable, install } = usePwaInstall();

  const handleIncrement = () => setPlayersCount(prev => Math.min(4, prev + 1));
  const handleDecrement = () => setPlayersCount(prev => Math.max(2, prev - 1));

  return (
    <main className="flex flex-col items-center justify-between min-h-screen py-4 px-4 max-w-2xl mx-auto overflow-y-auto">
      
      {/* Header */}
      <header className="mt-2 mb-4 md:mb-8 flex-shrink-0 landscape:mt-1">
        <div className="max-w-[280px] sm:max-w-[400px] md:max-w-[500px]">
          <img 
            src={gameLogo} 
            alt="Dice Dice Baby Logo" 
            className="w-full h-auto drop-shadow-md rounded-lg"
          />
        </div>
      </header>

      {/* Section */}
      <section className="flex flex-col items-center justify-center gap-6 w-full flex-grow">
        
        {/* Intro text */}
        <div className="bg-frost-white/50 border-2 border-polar-blue/30 p-3 md:p-4 rounded-xl text-center shadow-inner w-full">
          <p className="text-midnight-ice text-sm md:text-lg leading-tight font-medium">
            Bienvenue sur <span className="font-heading text-polar-blue">Dice Dice Baby</span>, <br />
            un jeu mélangeant <span className="text-red-alert font-bold">Yam's</span> et <span className="text-red-alert font-bold">Morpion</span>.
          </p>
        </div>

        {/* Player number text */}
        <div className="text-center space-y-1">
          <p className="font-heading text-base md:text-xl text-midnight-ice uppercase tracking-tight">
            Nombre de joueurs
          </p>
          <p className="font-heading text-[10px] md:text-xs text-midnight-ice/60 italic uppercase">
            ( 2 à 4 joueurs )
          </p>
        </div>

        {/* Counter */}
        <div className="flex items-center bg-frost-white border-[3px] border-polar-blue rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)]">
          <button 
            onClick={handleDecrement}
            className="w-12 h-12 md:w-16 md:h-16 flex items-center justify-center bg-polar-blue text-frost-white text-3xl font-bold hover:bg-midnight-ice active:scale-95 transition-all"
          >
            -
          </button>
          <div className="w-20 md:w-28 text-center text-3xl md:text-5xl font-heading text-midnight-ice">
            {playersCount}
          </div>
          <button 
            onClick={handleIncrement}
            className="w-12 h-12 md:w-16 md:h-16 flex items-center justify-center bg-polar-blue text-frost-white text-3xl font-bold hover:bg-midnight-ice active:scale-95 transition-all"
          >
            +
          </button>
        </div>

        <ButtonLink
          to={`/party-launcher?players=${playersCount}`}
          className="w-full py-4 text-lg md:text-2xl shadow-[4px_4px_0px_0px_rgba(1,54,89,1)]"
        >
          VALIDER
        </ButtonLink>
      </section>

      {/* Footer */}
      <footer className="w-full flex flex-row items-center justify-between gap-4 mt-auto pb-4 pt-4 border-t border-polar-blue/10">
        <div className="flex-1">
          {isInstallable && (
            <button
              onClick={install}
              className="w-full sm:w-auto bg-red-alert text-frost-white font-heading px-4 py-2 rounded-lg shadow-md text-[10px] md:text-xs uppercase tracking-tighter"
            >
              📲 Installer
            </button>
          )}
        </div>
        
        <button className="font-heading border-2 border-polar-blue px-6 py-1.5 text-polar-blue rounded-md hover:bg-polar-blue hover:text-frost-white transition-all text-xs uppercase">
          RETOUR
        </button>
      </footer>
    </main>
  );
}