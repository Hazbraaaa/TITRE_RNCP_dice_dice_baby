import { BoardCard } from "./BoardCard";

export default function GameBoard() {
    const cards = [
        {
            id: 1,
            combinaison: "Paire de 2",
            color: "green",
            ptLvl1: 1,
            ptLvl2: 1
        },
        {
            id: 2,
            combinaison: "Paire de 5",
            color: "green",
            ptLvl1: 1,
            ptLvl2: 1
        },
        {
            id: 3,
            combinaison: "Brelan de 6",
            color: "blue",
            ptLvl1: 2,
            ptLvl2: 1
        },
        {
            id: 4,
            combinaison: "Petite Suite",
            color: "red",
            ptLvl1: 3,
            ptLvl2: 2
        },
        {
            id: 5,
            combinaison: "Carré",
            color: "red",
            ptLvl1: 4,
            ptLvl2: 3
        },
        {
            id: 6,
            combinaison: "Full",
            color: "red",
            ptLvl1: 3,
            ptLvl2: 2
        },
        {
            id: 7,
            combinaison: "Brelan de 1",
            color: "blue",
            ptLvl1: 2,
            ptLvl2: 1
        },
        {
            id: 8,
            combinaison: "Double paire",
            color: "green",
            ptLvl1: 3,
            ptLvl2: 2
        },
        {
            id: 9,
            combinaison: "Paire de 1",
            color: "green",
            ptLvl1: 1,
            ptLvl2: 1
        },
        {
            id: 10,
            combinaison: "Brelan de 5",
            color: "blue",
            ptLvl1: 2,
            ptLvl2: 1
        },
        {
            id: 11,
            combinaison: ">= 26",
            color: "red",
            ptLvl1: 3,
            ptLvl2: 2
        },
        {
            id: 12,
            combinaison: "11/12/13",
            color: "red",
            ptLvl1: 3,
            ptLvl2: 2
        },
        {
            id: 13,
            combinaison: "Paire de 6",
            color: "green",
            ptLvl1: 1,
            ptLvl2: 1
        },
        {
            id: 14,
            combinaison: "<= 9",
            color: "red",
            ptLvl1: 3,
            ptLvl2: 2
        },
        {
            id: 15,
            combinaison: "Brelan de 3",
            color: "blue",
            ptLvl1: 2,
            ptLvl2: 1
        },
        {
            id: 16,
            combinaison: "Grande Suite",
            color: "red",
            ptLvl1: 3,
            ptLvl2: 2
        },
    ];

    return (
        <div className="grid grid-cols-4 gap-1">
            {cards.map((card) => (
                <BoardCard
                    key={card.id}
                    combinaison={card.combinaison}
                    color={card.color}
                    ptLvl1={card.ptLvl1}
                // ptLvl2={card.ptLvl2}
                />
            ))}
        </div>
    );
};