import { createBrowserRouter } from "react-router-dom"
import App from "../App.tsx"
import Home from "../pages/Home"
import PartyLauncher from "../pages/PartyLauncher"
import Game from "../pages/Game"

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: "party-launcher", element: <PartyLauncher /> },
      { path: "game", element: <Game /> },
    ]
  }
])