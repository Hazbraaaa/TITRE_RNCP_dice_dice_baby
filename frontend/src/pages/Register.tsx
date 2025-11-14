import { Button } from "../components/Button";

export default function Register() {
  return (
    <>
      <h1 className="text-2xl font-bold">Création du compte Joueur</h1>

      <form className="flex flex-col gap-2">
        <label className="flex flex-col">
          Nom :
          <input type="text" name="nom" required className="border rounded"/>
        </label>
        <label className="flex flex-col">
          Email :
          <input type="email" name="email" required className="border rounded"/>
        </label>
        <label className="flex flex-col">
          Password :
          <input type="password" name="password" required className="border rounded"/>
        </label>
        <Button type="submit" onClick={()=> console.log("appel API")}>Valider</Button>
      </form>
    </>
  );
}