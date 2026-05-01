type PlayerProps = {
  number: number;
  name: string;
  score: number;
  nb_of_pieces: number;
  align?: 'left' | 'right';
  className?: string;
};

export const Player = ({
  number,
  name,
  score,
  nb_of_pieces,
  align = 'left',
  className = '',
}: PlayerProps) => {
  const isRight = align === 'right';

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
      <div className="flex-shrink-0 w-8 h-8 bg-polar-blue border-2 border-midnight-ice flex items-center justify-center rounded-full text-white font-heading text-xs shadow-sm">
        {number}
      </div>

      {/* Player Info */}
      <div className="flex flex-col flex-grow leading-none">
        <span className="font-heading text-midnight-ice text-sm truncate max-w-[80px]">
          {name}
        </span>
        <div
          className={`flex flex-col gap-0.5 mt-1 font-sans text-[10px] font-bold ${isRight ? 'items-end' : 'items-start'}`}
        >
          <span className="text-red-alert uppercase">Score: {score}</span>
          <span className="text-midnight-ice/60">Pions: {nb_of_pieces}</span>
        </div>
      </div>
    </div>
  );
};
