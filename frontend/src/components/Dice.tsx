type DiceProps = {
  value: number;
  locked?: boolean;
  onClick?: () => void;
  className?: string;
};

export const Dice = ({ value, locked = false, onClick, className = '' }: DiceProps) => {
  return (
    <div
      onClick={onClick}
      className={`
        w-10 h-10 md:w-10 md:h-10 
        rounded-xl flex items-center justify-center 
        text-2xl font-black transition-all duration-150 cursor-pointer
        border-[3px] border-midnight-ice
        ${locked 
          ? 'bg-red-alert text-white translate-y-1 shadow-none' 
          : 'bg-white text-midnight-ice shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] active:translate-y-1 active:shadow-none'
        }
        ${className}
      `}
    >
      {value}
    </div>
  );
};