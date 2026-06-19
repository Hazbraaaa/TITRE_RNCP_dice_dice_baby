import { useState, useEffect } from 'react';
import { rollDices, endTurn } from '../services/gameService';
import { setupNewGame } from '../services/gameSetupService';
import type { Game } from '../types/game';

const STORAGE_KEY = 'DDB_game_info';

export const useGame = () => {
  // Store game in local state and sync with localStorage for persistence across reloads
  const [game, setGame] = useState<Game | null>(() => {
    const saved = localStorage.getItem(STORAGE_KEY);
    return saved ? JSON.parse(saved) : null;
  });

  // Extract relevant game data for the UI
  useEffect(() => {
    const initGame = async () => {
      if (game) return;

      try {
        const data = await setupNewGame();
        setGame(data);
      } catch (error) {
        console.error('Erreur setup:', error);
      }
    };
    initGame();
  }, []);

  // Sync game state to localStorage whenever it changes
  useEffect(() => {
    if (game) {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(game));
    }
  }, [game]);

  // Local actions to toggle dice selection and handle rolling, which will update the game state accordingly
  const toggleDice = (diceId: number) => {
    if (!game) return;

    const updatedDices = game.diceSet.dices.map((d) =>
      d.id === diceId ? { ...d, isKept: !d.isKept } : d
    );

    setGame({
      ...game,
      diceSet: { ...game.diceSet, dices: updatedDices },
    });
  };

  // Handler for rolling the dice, which will call the backend and update the game state with the new data
  const handleRoll = async () => {
    if (!game) return;

    const keptDiceIds = game.diceSet.dices
      .filter((d) => d.isKept)
      .map((d) => d.id);

    try {
      const updatedGame: Game = await rollDices({
        diceSetId: game.diceSet.id,
        keptDiceIds,
      });
      setGame(updatedGame);
    } catch (error) {
      console.error('Erreur lors du lancer de dés:', error);
    }
  };

  // Handler for ending turn, which will call the backend and update the game state with the new data
  const handleEndTurn = async () => {
    if (!game) return;

    const keptDiceIds = game.diceSet.dices
      .filter((d) => d.isKept)
      .map((d) => d.id);

    try {
      const updatedGame: Game = await endTurn({
        diceSetId: game.diceSet.id,
        keptDiceIds,
      });
      setGame(updatedGame);
    } catch (error) {
      console.error('Erreur lors de lavérification de fin de tour:', error);
    }
  };

  // Return the game state and actions for use in the component
  return {
    game,
    cards: game?.board || [],
    dices: game?.diceSet.dices || [],
    keptDiceIds:
      game?.diceSet.dices.filter((d) => d.isKept).map((d) => d.id) || [],
    toggleDice,
    handleRoll,
    handleEndTurn,
  };
};
