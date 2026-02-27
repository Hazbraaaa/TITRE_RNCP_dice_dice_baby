import React from "react";
import { Link } from "react-router-dom";
import { buttonVariants } from "../styles/buttonStyles";

type ButtonLinkProps = {
  to: string;
  children: React.ReactNode;
  disabled?: boolean;
  className?: string;
  variant?: keyof typeof buttonVariants.colors;
  fullWidth?: boolean;
};

export const ButtonLink = ({
  to,
  children,
  disabled = false,
  className = "",
  variant = "primary",
  fullWidth = false,
}: ButtonLinkProps) => {
  
  const currentVariant = disabled ? buttonVariants.colors.disabled : buttonVariants.colors[variant];
  const widthStyle = fullWidth ? "w-full" : "";

  return (
    <Link
      to={disabled ? "#" : to}
      onClick={(e) => disabled && e.preventDefault()}
      className={`${buttonVariants.base} ${currentVariant} ${widthStyle} px-6 py-4 text-lg md:text-xl rounded ${className}`}
    >
      {children}
    </Link>
  );
};