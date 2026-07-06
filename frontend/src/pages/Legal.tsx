import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

export default function Legal() {
  const { hash } = useLocation();
  const navigate = useNavigate();

  // Scroll to the section corresponding to the hash in the URL when the component mounts or when the hash changes
  useEffect(() => {
    if (hash) {
      const element = document.getElementById(hash.replace('#', ''));
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    } else {
      window.scrollTo(0, 0);
    }
  }, [hash]);

  const currentYear = new Date().getFullYear();

  return (
    <main className="min-h-screen py-8 px-4 max-w-3xl mx-auto font-body text-midnight-ice">
      {/* Return button */}
      <div className="mb-8">
        <button
          onClick={() => navigate(-1)}
          className="inline-flex items-center gap-2 text-sm font-medium text-polar-blue hover:underline cursor-pointer"
        >
          ← Retour
        </button>
      </div>

      {/* Main Title */}
      <h1 className="text-3xl uppercase tracking-tight text-center mb-12 border-b-2 border-polar-blue/20 pb-4">
        Informations Légales
      </h1>

      <div className="space-y-16">
        {/* Legal Mentions */}
        <section
          id="mentions"
          className="scroll-mt-6 space-y-4 animate-in fade-in duration-300"
        >
          <h2 className="text-xl font-bold uppercase tracking-wide border-l-4 border-polar-blue pl-3">
            Mentions Légales
          </h2>

          <div className="bg-frost-white/40 border border-polar-blue/10 rounded-xl p-5 space-y-3 text-sm leading-relaxed">
            <p>
              <strong>Éditeur du site :</strong> Clément HAZERA, éditeur du site
              [futur-domaine-a-changer.com]. Code source disponible publiquement
              sur GitHub. Vous pouvez me contacter à l'adresse suivante :{' '}
              <span className="underline">[email.a.changer@gmail.com]</span>.
            </p>
            <p>
              <strong>Hébergement :</strong> Ce site et son infrastructure
              backend sont hébergés par [Futur hébergeur à changer].
            </p>
            <p>
              <strong>Propriété intellectuelle :</strong> L'ensemble des
              contenus originaux de ce site (textes, logos, graphismes liés au
              jeu) est la propriété exclusive de l'éditeur.
            </p>
          </div>
        </section>

        {/* Confidentiality Policy */}
        <section
          id="confidentialite"
          className="scroll-mt-6 space-y-4 animate-in fade-in duration-500"
        >
          <div className="space-y-1">
            <h2 className="text-xl font-bold uppercase tracking-wide border-l-4 border-polar-blue pl-3">
              Politique de Confidentialité
            </h2>
            <p className="text-[11px] text-midnight-ice/50 italic pl-4">
              Dernière mise à jour : juillet 2026
            </p>
          </div>

          <div className="bg-frost-white/40 border border-polar-blue/10 rounded-xl p-5 space-y-6 text-sm leading-relaxed">
            <div className="space-y-2">
              <h3 className="font-bold text-base">Introduction</h3>
              <p>
                La protection de vos données personnelles est au cœur du
                développement de <strong>Dice Dice Baby</strong>. Cette
                politique de confidentialité vous explique de manière
                transparente quelles données nous collectons pour permettre le
                fonctionnement du jeu en ligne, conformément au RGPD.
              </p>
            </div>

            <div className="space-y-2">
              <h3 className="font-bold text-base">
                Données collectées & Finalités
              </h3>
              <p>
                Nous collectons et stockons en base de données de manière
                sécurisée :
              </p>
              <ul className="list-disc pl-5 space-y-1.5">
                <li>
                  <strong>Données de compte :</strong> Votre pseudo, votre
                  adresse email (pour la gestion du compte / récupération) ainsi
                  que votre mot de passe (ce dernier est systématiquement haché
                  et chiffré avant d'entrer en base).
                </li>
                <li>
                  <strong>Données de jeu :</strong> L'historique de vos parties,
                  statistiques et scores nécessaires au fonctionnement des
                  fonctionnalités multijoueurs de l'application.
                </li>
              </ul>
            </div>

            <div className="space-y-2">
              <h3 className="font-bold text-base">
                Stockage local & Cookies de session
              </h3>
              <p>
                Pour assurer le confort de jeu et la sécurité, nous utilisons :
              </p>
              <ul className="list-disc pl-5 space-y-1.5">
                <li>
                  <strong>Cookie sécurisé (HttpOnly) :</strong> Nous utilisons
                  un cookie <code>HttpOnly</code> et <code>Secure</code> pour
                  stocker votre token d'authentification. Ce cookie est
                  inaccessible par les scripts JavaScript externes, ce qui
                  protège votre session contre les attaques XSS.
                </li>
                <li>
                  <strong>LocalStorage (Navigateur) :</strong> Utilisé
                  localement sur votre appareil pour conserver temporairement
                  des données de confort liées à la partie en cours.
                </li>
                <li>
                  <strong>PWA (Progressive Web App) :</strong> L'application met
                  en cache les fichiers nécessaires sur votre appareil afin de
                  permettre un chargement instantané et une installation sur
                  votre écran d'accueil.
                </li>
              </ul>
            </div>

            <div className="space-y-2">
              <h3 className="font-bold text-base">Destinataires et Sécurité</h3>
              <p>
                Vos données sont strictement confidentielles. Elles ne sont
                jamais vendues, louées ou transmises à des tiers à des fins
                publicitaires. Les communications entre votre navigateur et
                notre serveur backend sont entièrement chiffrées via le
                protocole HTTPS.
              </p>
            </div>

            <div className="space-y-2">
              <h3 className="font-bold text-base">Vos droits (RGPD)</h3>
              <p>
                Conformément au RGPD, vous disposez d'un droit d'accès, de
                rectification et de suppression de vos données personnelles
                (comme votre compte joueur). Pour exercer ces droits ou demander
                la suppression de votre compte, vous pouvez nous écrire à :{' '}
                <span className="underline">[email.a.changer@gmail.com]</span>.
              </p>
            </div>

            <div className="space-y-2">
              <h3 className="font-bold text-base">Réclamation</h3>
              <p>
                Si vous estimez que le traitement de vos données enfreint le
                RGPD, vous pouvez introduire une réclamation auprès de la CNIL :{' '}
                <a
                  href="https://www.cnil.fr"
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-polar-blue hover:underline font-medium"
                >
                  www.cnil.fr
                </a>
                .
              </p>
            </div>
          </div>
        </section>
      </div>

      {/* Copyright */}
      <div className="text-center text-xs font-heading text-midnight-ice/40 mt-16">
        © {currentYear} DICE DICE BABY. Tous droits réservés.
      </div>
    </main>
  );
}
