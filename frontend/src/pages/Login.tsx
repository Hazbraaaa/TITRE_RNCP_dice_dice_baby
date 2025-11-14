import { Button } from "../components/Button";

export default function Login() {
  return (
    <>
      <h1 className="text-2xl font-bold">Connexion au compte</h1>

      <form className="flex flex-col gap-2">
        <label className="flex flex-col">
          Email :
          <input type="email" name="email" required className="border rounded"/>
        </label>
        <label className="flex flex-col">
          Password :
          <input type="password" name="password" required className="border rounded"/>
        </label>
        <Button type="submit" onClick={()=> console.log("appel API")}>Se connecter</Button>
      </form>
    </>
  );
}