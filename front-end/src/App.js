import "./App.css";
import { useEffect } from "react";
import { useLocalState } from "./util/useLocalStorage";
import { Route, Routes } from "react-router-dom";
import Dashboard from "./Dashboard";
import HomePage from "./Homepage";
import Login from "./Login";
import PrivateRoute from "./PrivateRoute";

function App() {
  const [jwt, setJwt] = useLocalState("", "jwt");
  return (
    <Routes>
      <Route
        path="/dashboard"
        element={
          <PrivateRoute>
            <Dashboard />
          </PrivateRoute>
        }
      />
      <Route path="login" element={<Login />} />
      <Route path="/" element={<HomePage />} />
    </Routes>
  );
}
export default App;
