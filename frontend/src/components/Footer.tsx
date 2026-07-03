import { Link } from 'react-router-dom';
import { Button } from '../components/Button';

interface FooterProps {
  isInstallable?: boolean;
  onInstall?: () => void;
}

export default function Footer({
  isInstallable = false,
  onInstall,
}: FooterProps) {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="w-full flex flex-col items-center justify-center gap-3 mt-4 pt-2 flex-shrink-0 border-t border-polar-blue/10 text-xs text-midnight-ice/60">
      {/* PWA Install Button */}
      {isInstallable && onInstall && (
        <div>
          <Button
            variant="warning"
            size="sm"
            onClick={onInstall}
            className="px-4 py-2 rounded-lg"
          >
            📲 Installer l'application
          </Button>
        </div>
      )}

      {/* Legal Links and Copyright */}
      <div className="flex flex-row items-center justify-center gap-2 flex-wrap text-center">
        <span>© {currentYear} DICE DICE BABY.</span>
        <span className="hidden sm:inline">•</span>
        <Link to="/legal#mentions" className="hover:underline">
          Mentions légales
        </Link>
        <span>•</span>
        <Link to="/legal#confidentialite" className="hover:underline">
          Politique de confidentialité
        </Link>
      </div>
    </footer>
  );
}
