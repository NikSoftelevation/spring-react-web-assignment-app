package org.coderscampus.AssignmnetSubmissionApp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/api")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }
}