import { Player } from "./Player";

export default function ScoreBoard() {
    const players = [
        { id: 1, name: "J1", score: 1, nb_of_pieces: 6 },
        { id: 2, name: "J2", score: 2, nb_of_pieces: 6 },
        { id: 3, name: "J3", score: 3, nb_of_pieces: 6 },
        // { id: 4, name: "J4", score: 4, nb_of_pieces: 6 },
    ];

    return (
        <div className="flex flex-col gap-3 bg-white">
            <div className="flex justify-between">
                <div className="w-40">
                    {players[0] && <Player {...players[0]} align="left" />}
                </div>
                <div className="w-40">
                    {players[1] && <Player {...players[1]} align="right" />}
                </div>
            </div>

            <div className="flex justify-between">
                <div className="w-40">
                    {players[2] && <Player {...players[2]} align="left" />}
                </div>
                <div className="w-40">
                    {players[3] && <Player {...players[3]} align="right" />}
                </div>
            </div>
        </div>

    );
}
