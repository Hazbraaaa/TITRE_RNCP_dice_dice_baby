import { colorVariants } from "../services/colorVariants";
import type { ColorName } from "../services/colorVariants";

type BoardCardProps = {
    combinaison: string;
    color?: ColorName;
    ptLvl1: number;
    // ptLvl2: number;
    className?: string;
};

export const BoardCard = ({
    combinaison,
    color,
    ptLvl1,
    // ptLvl2,
    className = "",
}: BoardCardProps) => {
    const colors = colorVariants[color];

    return (
        <div className={`w-[81px] h-[81px] grid grid-cols-4 grid-rows-4 gap-1 ${colors.base} rounded-md p-1 relative text-white text-[10px] text-center font-semibold ${className}`}>
            <div className={`col-start-1 col-span-3 row-start-2 row-span-3 flex items-center justify-center ${colors.dark} rounded`}>
                {combinaison}
            </div>

            <div className={`col-start-4 row-start-1 flex items-center justify-center w-4 h-4 text-xs ${colors.dark} rounded-full`}>
                {ptLvl1}
            </div>

            {/* <div className={`col-start-4 row-start-1 flex items-center justify-center w-4 h-4 text-xs ${colors.dark} rounded-full`}>
                {ptLvl2}
            </div> */}
        </div>
    );
};