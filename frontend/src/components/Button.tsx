import React from "react";

type ButtonProps = {
  onClick?: () => void;
  children: React.ReactNode;
  className?: string;
  type?: "button" | "submit" | "reset";
};

export const Button = ({
  onClick,
  children,
  className = "",
  type = "button",
}: ButtonProps) => {
  return (
    <button
      type={type}
      onClick={onClick}
      className={`px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition ${className}`}
    >
      {children}
    </button>
  );
};
