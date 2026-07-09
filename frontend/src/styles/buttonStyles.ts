export const buttonVariants = {
  base: 'flex items-center justify-center text-center font-heading uppercase tracking-wider transition-all duration-200 shadow-[4px_4px_0px_0px_rgba(1,54,89,1)] active:translate-y-0.5 active:translate-x-0.5 active:shadow-none',

  colors: {
    primary: 'bg-polar-blue text-frost-white hover:bg-midnight-ice',
    secondary: 'bg-green-success text-frost-white hover:bg-green-success-hover',
    warning:
      'bg-red-alert hover:bg-red-alert-hover text-frost-white transition-colors',
    outlined:
      'border-2 border-polar-blue text-polar-blue bg-transparent shadow-none hover:bg-polar-blue hover:text-frost-white',
    disabled:
      'bg-snow-ashes text-frost-white cursor-not-allowed opacity-80 shadow-none border-midnight-ice',
  },

  sizes: {
    sm: 'px-3 py-1.5 text-sm',
    md: 'px-4 py-2 text-base',
    lg: 'px-6 py-4 text-lg md:text-xl',
  },
};
