import "./App.css";
import { useEffect, useState } from "react";
import { useLocalState } from "./util/useLocalStorage";
import { Route, Routes } from "react-router-dom";
import Dashboard from "./Dashboard";
import HomePage from "./Homepage";
import Login from "./Login";
import jwt_decode from "...jwt-decode";
import PrivateRoute from "./PrivateRoute";
import AssignmentView from "./AssignmentView";
import "bootstrap/dist/css/bootstrap.min.css";
import CodeReviewerDashboard from "./CodeReviewerDashboard";
import CodeReviewAssignmentView from ".CodeReviewAssignmentview";

function App() {
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [roles, setRoles] = useState(getRolesFromJWT(jwt));

  function getRolesFromJWT() {
    //get role from jwt and assign via setRoles()
    if (jwt) {
      const decodedJwt = jwt_decode(jwt);
      return decodedJwt.authorities;
    }
    return [];
  }
  return (
    <Routes>
      <Route
        path="/dashboard"
        element={
          roles.find((role) => role === "ROLE_CODE_REVIEWER") ? (
            <PrivateRoute>
              <CodeReviewerDashboard />
            </PrivateRoute>
          ) : (
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          )
        }
      />
      <Route
        path="/assignments/:id"
        element={
          roles.find((role) => role === "ROLE_CODE_REVIEWER") ? (
            <PrivateRoute>
              <CodeReviewAssignmentView />
            </PrivateRoute>
          ) : (
            <PrivateRoute>
              <AssignmentView />
            </PrivateRoute>
          )
        }
      />
      <Route path="login" element={<Login />} />
      <Route path="/" element={<HomePage />} />
    </Routes>
  );
}
export default App;
