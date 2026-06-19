import React from 'react';
import { buttonVariants } from '../styles/buttonStyles';

type ButtonProps = {
  onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
  disabled?: boolean;
  children: React.ReactNode;
  className?: string;
  variant?: keyof typeof buttonVariants.colors;
  type?: 'button' | 'submit' | 'reset';
  fullWidth?: boolean;
};

export const Button = ({
  onClick,
  disabled = false,
  children,
  className = '',
  variant = 'primary',
  type = 'button',
  fullWidth = false,
}: ButtonProps) => {
  const widthStyle = fullWidth ? 'w-full' : '';

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`${buttonVariants.base} ${buttonVariants.colors[variant]} ${widthStyle} px-6 py-4 text-lg hover:scale-[1.02] active:scale-[0.98] md:text-xl rounded ${className}`}
    >
      {children}
    </button>
  );
};
