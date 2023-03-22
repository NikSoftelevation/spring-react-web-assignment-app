import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ajax from "../Services/fetchService";
import { useLocalState } from "../util/useLocalStorage";

const Dashboard = () => {
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [assignments, setAssignments] = useState(null);
  useEffect(() => {
    ajax("api/assignments", "GET", jwt).then((assignmentsData) => {
      setAssignments(assignmentsData);
    });
  }, []);

  function createAssignment() {
    ajax("api/assignments", "POST", jwt).then((assignment) => {
      window.location.href = `/assignments/${assignment.id}`;
    });
  }
  return (
    <div style={{ margin: "2cm" }}>
      {assignments ? (
        assignments.map((assignment) => (
          <div key={assignment.id}>
            <Link to={`/assignments/${assignment.id}`}>
              Assignment ID:{assignment.id}
            </Link>
          </div>
        ))
      ) : (
        <></>
      )}

      <button onClick={() => createAssignment()}>Submit New Assignment</button>
    </div>
  );
};

export default Dashboard;
