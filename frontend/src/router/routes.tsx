import { createBrowserRouter } from "react-router-dom"
import App from "../App.tsx"
import Home from "../pages/Home"
import PartyLauncher from "../pages/PartyLauncher"
import Login from "../pages/Login"
import Game from "../pages/Game"
import Register from "../pages/Register"

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: "party-launcher", element: <PartyLauncher /> },
      { path: "login", element: <Login /> },
      { path: "game", element: <Game /> },
      { path: "register", element: <Register /> }
    ]
  }
])