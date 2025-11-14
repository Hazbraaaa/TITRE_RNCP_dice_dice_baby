type DiceProps = {
    value: number;
    className?: string;
};

export const Dice = ({
    value,
    className = "",
}: DiceProps) => {
    return (
        <div className={`w-12 h-12 rounded-md bg-white shadow-md flex items-center justify-center text-xl font-bold text-gray-800 border border-gray-400 ${className}`}>
            {value}
        </div>
    );
};