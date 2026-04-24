import { useState, useEffect } from 'react';
import { getBoard } from '../services/gameSetupService';
import type { Card } from '../types/card';

export const useGameSetup = () => {
  const [cards, setCards] = useState<Card[]>([]);

  // Load the game board cards when the component mounts
  useEffect(() => {
    const refreshCards = async () => {
      try {
        const data = await getBoard();
        setCards(data);
      } catch (error) {
        console.error(error);
      }
    };
    refreshCards();
  }, []);

  return { cards };
};
