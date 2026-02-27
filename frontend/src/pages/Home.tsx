import { useState } from "react";
import { ButtonLink } from "../components/ButtonLink";
import { Button } from "../components/Button";
import { usePwaInstall } from "../components/usePwaInstall";
import gameLogo from "../assets/logo.png";

export default function Home() {
  const [step, setStep] = useState<number>(1);
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

      {/* Main Section */}
      <section className="flex flex-col items-center justify-center gap-6 w-full flex-grow">

        {/* Intro text */}
        <div className="bg-frost-white/50 border-2 border-polar-blue/30 p-3 md:p-4 rounded-xl text-center shadow-inner w-full">
          <p className="text-midnight-ice text-sm md:text-lg leading-tight font-medium">
            Bienvenue sur <span className="font-heading text-polar-blue">Dice Dice Baby</span>, <br />
            un jeu mélangeant <span className="text-red-alert font-bold">Yam's</span> et <span className="text-red-alert font-bold">Morpion</span>.
          </p>
        </div>

        {step === 1 ? (
          /* Playing mode choice */
          <div className="flex flex-col gap-4 w-full animate-in fade-in slide-in-from-bottom-4 duration-300">
            <Button
              variant="primary"
              fullWidth
              className="py-6"
              onClick={() => setStep(2)}
            >
              Jouer sur un ecran partagé
            </Button>
            <Button
              variant="disabled"
              fullWidth
              className="py-6"
            >
              Jouer sur plusieurs écrans (inactif)
            </Button>
          </div>
        ) : (
          /* Numbers of players choice */
          <div className="flex flex-col items-center gap-8 w-full animate-in fade-in slide-in-from-right-4 duration-300">
            <div className="font-heading text-center space-y-1 uppercase">
              <p className="text-base md:text-xl text-midnight-ice tracking-tight">
                Nombre de joueurs
              </p>
              <p className="text-[10px] md:text-xs text-midnight-ice/60 italic">
                ( 2 à 4 joueurs )
              </p>
            </div>

            {/* Counter */}
            <div className="flex items-center bg-frost-white border-[3px] border-polar-blue rounded-sm shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] overflow-hidden">
              <Button
                className="w-14 h-14 md:w-20 md:h-20 text-3xl shadow-none active:translate-y-0 active:translate-x-0 rounded-none border-r-[3px] border-polar-blue"
                onClick={handleDecrement}
              >
                -
              </Button>
              <div className="w-20 md:w-32 text-center text-4xl md:text-6xl font-heading text-midnight-ice">
                {playersCount}
              </div>
              <Button
                className="w-14 h-14 md:w-20 md:h-20 text-3xl shadow-none active:translate-y-0 active:translate-x-0 rounded-none border-l-[3px] border-polar-blue"
                onClick={handleIncrement}
              >
                +
              </Button>
            </div>

            <ButtonLink
              to={`/party-launcher?players=${playersCount}`}
              fullWidth
              className="py-6"
            >
              VALIDER
            </ButtonLink>

            <div className="w-full flex justify-end">
              <Button
                variant="outlined"
                className="px-4"
                onClick={() => setStep(1)}
              >
                RETOUR
              </Button>
            </div>
          </div>
        )}
      </section>

      {/* Footer */}
      <footer className="w-full flex flex-row items-center justify-between gap-4 mt-auto pb-4 pt-4 border-t border-polar-blue/10">
        <div className="flex-1">
          {isInstallable && (
            <Button
              variant="warning"
              onClick={install}
              className="px-4 py-2 rounded-lg"
            >
              📲 Installer
            </Button>
          )}
        </div>
      </footer>
    </main>
  );
}