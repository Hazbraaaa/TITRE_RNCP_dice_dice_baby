import { PLAYER_THEMES } from '../styles/playerThemes';

type PlayerProps = {
  number: number;
  name: string;
  score: number;
  remainingChips: number;
  align?: 'left' | 'right';
  className?: string;
};

export const Player = ({
  number,
  name,
  score,
  remainingChips,
  align = 'left',
  className = '',
}: PlayerProps) => {
  const isRight = align === 'right';

  // On récupère le thème du joueur actuel
  const theme = PLAYER_THEMES[number];

  return (
    <div
      className={`
        flex items-center gap-3 p-2
        bg-frost-white border-2 border-midnight-ice 
        shadow-[3px_3px_0px_0px_rgba(1,54,89,1)]
        rounded-sm min-w-[160px]
        ${isRight ? 'flex-row-reverse text-right' : 'flex-row text-left'}
        ${className}
      `}
    >
      {/* Player number icon */}
      <div className="flex-shrink-0 w-9 h-9 border-2 border-midnight-ice flex items-center justify-center rounded-full bg-polar-blue overflow-hidden shadow-sm relative">
        {' '}
        <img
          src={theme.penguinSrc}
          alt={`Joueur ${number}`}
          className="w-full h-full object-cover scale-130 object-[center_top]"
        />
      </div>

      {/* Player Info */}
      <div
        className={`flex flex-col flex-grow gap-0.5 leading-none ${isRight ? 'items-end' : 'items-start'}`}
      >
        <span className="font-heading text-midnight-ice text-sm truncate max-w-[80px]">
          {name}
        </span>
        <span className="text-red-alert uppercase font-sans font-bold text-[10px]">
          Score: {score}
        </span>
        <span className="text-midnight-ice/60 font-sans font-bold text-[10px]">
          Pions: {remainingChips}
        </span>
      </div>
    </div>
  );
};
