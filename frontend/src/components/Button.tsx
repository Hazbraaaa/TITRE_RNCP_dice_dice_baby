import React from "react";
import { buttonVariants } from "../styles/buttonStyles";

type ButtonProps = {
  onClick?: () => void;
  children: React.ReactNode;
  className?: string;
  variant?: keyof typeof buttonVariants.colors;
  type?: "button" | "submit" | "reset";
  fullWidth?: boolean;
};

export const Button = ({
  onClick,
  children,
  className = "",
  variant = "primary",
  type = "button",
  fullWidth = false,
}: ButtonProps) => {

  const widthStyle = fullWidth ? "w-full" : "";

  return (
    <button
      type={type}
      onClick={onClick}
      className={`${buttonVariants.base} ${buttonVariants.colors[variant]} ${widthStyle} px-6 py-4 text-lg md:text-xl rounded ${className}`}
    >
      {children}
    </button>
  );
};