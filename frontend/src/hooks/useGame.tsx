import { useState, useEffect } from 'react';
import { rollDices, endTurn, skipTurn, leaveGame } from '../services/gameService';
import { setupNewGame } from '../services/gameSetupService';
import { useNavigate } from 'react-router-dom';
import type { Game } from '../types/game';

const STORAGE_KEY = 'DDB_game_info';

export const useGame = () => {
  // React Router's navigation hook for programmatic navigation
  const navigate = useNavigate();

  // State to hold any alert messages for the user
  const [alertMessage, setAlertMessage] = useState<string | null>(null);

  // Store game in local state and sync with localStorage for persistence across reloads
  const [game, setGame] = useState<Game | null>(() => {
    const saved = localStorage.getItem(STORAGE_KEY);
    return saved ? JSON.parse(saved) : null;
  });

  // Store the currently selected card ID in local state
  const [selectedCardId, setSelectedCardId] = useState<number | null>(null);

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

  // Local action to toggle card selection
  const toggleSelectCard = (cardId: number) => {
    setSelectedCardId((prev) => (prev === cardId ? null : cardId));
  };

  // Handler for rolling the dice, which will call the backend and update the game state with the new data
  const handleRoll = async () => {
    if (!game) return;

    const keptDiceIds = game.diceSet.dices
      .filter((d) => d.isKept)
      .map((d) => d.id);

    try {
      const updatedGame: Game = await rollDices({
        gameId: game.id,
        keptDiceIds,
      });
      setGame(updatedGame);
    } catch (error) {
      console.error('Erreur lors du lancer de dés:', error);
    }
  };

  // Handler for ending turn, which will call the backend and update the game state with the new data
  const handleEndTurn = async () => {
    if (!game || !selectedCardId) return;

    try {
      const updatedGame: Game = await endTurn({
        gameId: game.id,
        gameCardId: selectedCardId,
      });
      setGame(updatedGame);
      setAlertMessage(null);
      setSelectedCardId(null);
    } catch (error: any) {
      console.error('Erreur lors de la vérification de fin de tour:', error);
      setAlertMessage(error.message);
    }
  };

  // Handler for skipping turn, which will call the backend and update the game state with the new data
  const handleSkipTurn = async () => {
    if (!game) return;

    try {
      const updatedGame: Game = await skipTurn({
        gameId: game.id,
      });
      setGame(updatedGame);
      setSelectedCardId(null);
      setAlertMessage(null);
    } catch (error: any) {
      console.error('Erreur lors de la vérification de fin de tour:', error);
      setAlertMessage(error.message);
    }
  };

  // Handler for rematch, which will reset the game state and start a new game
  const handleRematch = async () => {
    try {
      const newGame: Game = await setupNewGame();
      setGame(newGame);
      setSelectedCardId(null);
      setAlertMessage(null);
    } catch (error) {
      console.error('Erreur lors de la création d\'une nouvelle partie:', error);
    }
  };

  // Handler for going back to the menu, which will clear the game state and navigate to the main menu
  const handleGoToMenu = async () => {
    try {
      await leaveGame();
    } catch (error) {
      console.error('Erreur lors de la suppression des données de jeu:', error);
    }

    // Clear game state and localStorage
    setGame(null);
    setSelectedCardId(null);
    setAlertMessage(null);
    localStorage.removeItem('DDB_game_info');
    localStorage.removeItem('DDB_lobby_players');

    // Redirect to the main menu using React Router's navigate function
    navigate('/'); 
  };

  // Return the game state and actions for use in the component
  return {
    alertMessage,
    game,
    cards: game?.board || [],
    dices: game?.diceSet.dices || [],
    keptDiceIds:
      game?.diceSet.dices.filter((d) => d.isKept).map((d) => d.id) || [],
    selectedCardId,
    toggleDice,
    toggleSelectCard,
    handleRoll,
    handleEndTurn,
    handleSkipTurn,
    handleRematch,
    handleGoToMenu,
    clearAlert: () => setAlertMessage(null),
  };
};