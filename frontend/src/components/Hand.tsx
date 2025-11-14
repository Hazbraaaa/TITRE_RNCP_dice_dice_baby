import { Dice } from "./Dice";

export default function Hand() {
    const dices = [
        {
            id: 1,
            value: 1
        },
        {
            id: 2,
            value: 3
        },
        {
            id: 3,
            value: 6
        },
        {
            id: 4,
            value: 3
        },
        {
            id: 5,
            value: 2
        },

    ];

    return (
        <div className="flex gap-2 p-4 bg-gray-100 rounded-lg justify-center">
            {dices.map((dice) => (
                <Dice key={dice.id} value={dice.value} />
            ))}
        </div>
    );
}