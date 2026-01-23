import React from "react";
import { Link } from "react-router-dom";

type ButtonLinkProps = {
  to: string;
  children: React.ReactNode;
  disabled?: boolean;
  className?: string;
};

export const ButtonLink = ({
  to,
  children,
  disabled = false,
  className = "",
}: ButtonLinkProps) => {
  const baseStyles = "flex items-center justify-center text-center font-heading uppercase tracking-wider transition-all duration-200 shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] active:translate-y-1 active:shadow-none";
  
  const colorStyles = disabled 
    ? "bg-snow-ashes text-frost-white cursor-not-allowed opacity-80" 
    : "bg-polar-blue text-frost-white hover:bg-midnight-ice";

  return (
    <Link
      to={disabled ? "#" : to}
      onClick={(e) => disabled && e.preventDefault()}
      className={`${baseStyles} ${colorStyles} rounded-sm px-6 py-4 text-lg md:text-xl lg:text-2xl ${className}`}
    >
      {children}
    </Link>
  );
};