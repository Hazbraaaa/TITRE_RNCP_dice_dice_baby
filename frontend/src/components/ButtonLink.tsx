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
  return (
    <Link
      to={disabled ? "#" : to}
      className={`inline-block px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition ${className}`}
    >
      {children}
    </Link>
  );
};
