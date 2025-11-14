type PlayerProps = {
    name: string;
    score: number;
    nb_of_pieces: number;
    align?: "left" | "right";
    className?: string;
};

export const Player = ({
    name,
    score,
    nb_of_pieces,
    align = "left",
    className = "",
}: PlayerProps) => {
    return (
        <div
            className={`w-40 h-10 bg-gray-300 flex items-center justify-between px-3 text-sm font-bold text-black ${className}`}
        >
            {align === "left" && (
                <>
                    <span>{name}</span>
                    <div className="flex flex-col text-right text-sm font-normal">
                        <span>Score: {score}</span>
                        <span>Pions restants: {nb_of_pieces}</span>
                    </div>
                </>
            )}

            {align === "right" && (
                <>
                    <div className="flex flex-col text-left text-sm font-normal">
                        <span>Score: {score}</span>
                        <span>Pions restants: {nb_of_pieces}</span>
                    </div>
                    <span>{name}</span>
                </>
            )}
        </div>
    );
};
