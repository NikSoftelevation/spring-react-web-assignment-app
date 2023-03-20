import "./App.css";
import { useEffect } from "react";
import { useLocalState } from "./util/useLocalStorage";
import { Route, Routes } from "react-router-dom";
import Dashboard from "./Dashboard";
import HomePage from "./Homepage";

function App() {
  const [jwt, setJwt] = useLocalState("", "jwt");

  useEffect(() => {
    if (!jwt) console.log("Hello");

    const reqBody = {
      username: "NikhilAdmin",
      password: "Nikhil@07@18",
    };

    fetch("api/auth/login", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) => Promise.all([response.json(), response.headers]))
      .then(([body, headers]) => {
        setJwt(headers.get("authorization"));
      });
  }, []);
  useEffect(() => {
    console.log(`JWT is : ${jwt}`);
  }, [jwt]);

  return (
    <Routes>
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/" element={<HomePage />} />
    </Routes>
  );
}
export default App;
