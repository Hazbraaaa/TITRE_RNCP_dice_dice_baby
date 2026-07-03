import React from 'react';
import { buttonVariants } from '../styles/buttonStyles';

type ButtonProps = {
  onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
  disabled?: boolean;
  children: React.ReactNode;
  className?: string;
  variant?: keyof typeof buttonVariants.colors;
  size?: keyof typeof buttonVariants.sizes;
  type?: 'button' | 'submit' | 'reset';
  fullWidth?: boolean;
};

export const Button = ({
  onClick,
  disabled = false,
  children,
  className = '',
  variant = 'primary',
  size = 'lg',
  type = 'button',
  fullWidth = false,
}: ButtonProps) => {
  const widthStyle = fullWidth ? 'w-full' : '';

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`${buttonVariants.base} ${buttonVariants.colors[variant]} ${buttonVariants.sizes[size]} ${widthStyle} hover:scale-[1.02] active:scale-[0.98] rounded ${className}`}
    >
      {children}
    </button>
  );
};
