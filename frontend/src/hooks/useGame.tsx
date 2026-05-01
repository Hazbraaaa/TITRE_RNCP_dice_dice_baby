import { useState, useEffect } from 'react';
import { rollDices } from '../services/gameService';
import { setupNewGame } from '../services/gameSetupService';
import type { Card } from '../types/card';
import type { Dice } from '../types/dice';

export const useGame = () => {
  const [dices, setDices] = useState<Dice[]>([]);
  const [cards, setCards] = useState<Card[]>([]);
  const [diceSetId, setDiceSetId] = useState<number | null>(null);
  const [keptDiceIds, setKeptDiceIds] = useState<number[]>([]);

  useEffect(() => {
    const initGame = async () => {
      try {
        const data = await setupNewGame();
        setCards(data.board);
        setDices(data.dices);
        setDiceSetId(data.diceSetId);
      } catch (error) {
        console.error('Erreur setup:', error);
      }
    };
    initGame();
  }, []);

  const toggleDice = (id: number) => {
    setKeptDiceIds((prev) =>
      prev.includes(id) ? prev.filter((diceId) => diceId !== id) : [...prev, id]
    );
  };

  const handleRoll = async () => {
    if (!diceSetId) return;
    try {
      const data = await rollDices({
        diceSetId: diceSetId,
        keptDiceIds: keptDiceIds,
      });
      setDices(data.dices);
      const newKeptIds = data.dices
        .filter((d: Dice) => d.isKept)
        .map((d: Dice) => d.id);
      setKeptDiceIds(newKeptIds);
    } catch (error) {
      console.error(error);
    }
  };

  return { dices, cards, keptDiceIds, toggleDice, handleRoll };
};
