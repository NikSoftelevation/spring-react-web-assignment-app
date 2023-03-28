import React, { useEffect, useRef, useState } from "react";
import {
  Button,
  ButtonGroup,
  Col,
  Container,
  Dropdown,
  DropdownButton,
  Row,
  Form,
} from "react-bootstrap";
import { Link } from "react-router-dom";
import ajax from "../Services/fetchService";
import StatusBadge from "../StatusBadge";
import { useLocalState } from "../util/useLocalStorage";
import { useNavigate, useParams } from "react-router-dom";
import { Navbar } from "../Navbar";
import CommentContainer from "../CommentContainer";
const AssignmentView = () => {
  let navigate = useNavigate();
  const [jwt, setJwt] = useLocalState("", "jwt");
  const assignmentId = window.location.href.split("/assignments/")[1];
  const [assignment, setAssignment] = useState({
    branch: "",
    githubUrl: "",
    number: null,
    status: null,
  });

  const [assignmentEnums, setAssignmentEnums] = useState([]);
  const [assignmentStatuses, setAssignmentStatuses] = useState([]);

  const previousAssignmentValue = useRef(assignment);

  function updateAssignment(prop, value) {
    const newAssignment = { ...assignment };
    newAssignment[prop] = value;
    setAssignment(newAssignment);
  }

  function save() {
    //this implies that the student is submitting the assignment for the first time
    if (assignment.status === assignmentStatuses[0].status) {
      updateAssignment("status", assignmentStatuses[1].status);
    } else {
      persist();
    }
  }
  function persist() {
    ajax(`/api/assignments/${assignmentId}`, "PUT", jwt, assignment).then(
      (assignmentData) => {
        setAssignment(assignmentData);
      }
    );
  }

  useEffect(() => {
    if (previousAssignmentValue.current.status !== assignment.status) {
      persist();
    }
    previousAssignmentValue.current = assignment;
  }, [assignment]);

  useEffect(() => {
    ajax(`/api/assignments/${assignmentId}`, "GET", jwt).then(
      (assignmentResponse) => {
        let assignmentData = assignmentResponse.assignment;
        if (assignmentData.branch === null) assignmentData.branch = "";
        if (assignmentData.githubUrl === null) assignmentData.githubUrl = "";
        setAssignment(assignmentData);
        setAssignmentEnums(assignmentResponse.assignmentEnums);
        setAssignmentStatuses(assignmentResponse.statusEnums);
      }
    );
  }, []);

  return (
    <>
      <Navbar />
      <Container className="mt-5">
        <Row className="d-flex align-items-center">
          <Col>
            {assignment && assignment.number && assignmentEnums.length > 0 ? (
              <>
                <h1>Assignment {assignment.number}</h1>
                <h4>{assignmentEnums[assignment.number - 1].assignmentName}</h4>
              </>
            ) : (
              <></>
            )}
          </Col>
          <Col>
            {assignment ? <StatusBadge text={assignment.status} /> : <></>}
          </Col>
        </Row>

        {assignment ? (
          <>
            <Form.Group as={Row} className="my-3" controlId="assignmentName">
              <Form.Label column sm="3" md="2">
                Assignment number :
              </Form.Label>

              <Col sm="9" md="8" lg="6">
                <DropdownButton
                  as={ButtonGroup}
                  variant={"info"}
                  title={
                    assignment.number
                      ? `Assignment ${assignment.number}`
                      : "Select an Assignment"
                  }
                  onSelect={(selectedElement) => {
                    updateAssignment("number", selectedElement);
                  }}
                >
                  {assignmentEnums.map((assignmentEnum) => (
                    <Dropdown.Item
                      key={assignmentEnum.assignmentNum}
                      eventKey={assignmentEnum.assignmentNum}
                    >
                      {assignmentEnum.assignmentNum}
                    </Dropdown.Item>
                  ))}
                </DropdownButton>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="my-3" controlId="githubUrl">
              <Form.Label column sm="3" md="2">
                GitHub Url:
              </Form.Label>
              <Col sm="9" md="8" lg="6">
                <Form.Control
                  id="githubUrl"
                  onChange={(e) =>
                    updateAssignment("githubUrl", e.target.value)
                  }
                  type="url"
                  value={assignment.githubUrl}
                  placeholder="https://github.com/username/repo-name"
                />
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" controlId="branch">
              <Form.Label column sm="3" md="2">
                Branch:
              </Form.Label>
              <Col sm="9" md="8" lg="6">
                <Form.Control
                  id="branch"
                  onChange={(e) => updateAssignment("branch", e.target.value)}
                  type="text"
                  value={assignment.branch}
                  placeholder="example_branch_name"
                />
              </Col>
            </Form.Group>

            <div className="d-flex gap-5">
              {getButtonsByStatusAndRole(assignment.status, "student").map(
                (btn) => (
                  <Button
                    size="lg"
                    key={btn.text}
                    variant={btn.variant}
                    onClick={() => {
                      if (btn.nextStatus === "Same") persist();
                      else save(btn.nextStatus);
                    }}
                  >
                    {btn.text}
                  </Button>
                )
              )}
            </div>
            <CommentContainer assignmentId={assignmentId} />
          </>
        ) : (
          <></>
        )}
      </Container>
    </>
  );
};

export default AssignmentView;
