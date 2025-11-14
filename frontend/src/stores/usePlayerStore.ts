import { create } from 'zustand';

type Player = {
  id: number;
  name: string;
  score: number;
  pieces: number;
  token?: string;
};

type PlayerStore = {
  players: Player[];
  setPlayers: (players: Player[]) => void;
  updatePlayer: (id: number, data: Partial<Player>) => void;
  reset: () => void;
};

export const usePlayerStore = create<PlayerStore>((set) => ({
  players: [],
  setPlayers: (players) => set({ players }),
  updatePlayer: (id, data) =>
    set((state) => ({
      players: state.players.map((player) =>
        player.id === id ? { ...player, ...data } : player
      ),
    })),
  reset: () => set({ players: [] }),
}));
