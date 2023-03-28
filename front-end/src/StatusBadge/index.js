import React from "react";
import { Badge } from "react-bootstrap";

const StatusBadge = (props) => {
  const { text } = props;
  function getColorOfBadge() {
    if (text === "Completed") return "success";
    else if (text === "Needs Update") return "danger";
    else return "info";
  }
  return (
    <Badge pill bg={getColorOfBadge()} style={{ fontSize: "1em" }}>
      {text}
    </Badge>
  );
};

export default StatusBadge;
