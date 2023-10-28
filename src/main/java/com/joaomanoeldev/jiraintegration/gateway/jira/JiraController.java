package com.joaomanoeldev.jiraintegration.gateway.jira;

import com.joaomanoeldev.jiraintegration.usecase.JiraIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jira")
public class JiraController {

    @Autowired
    private JiraIssue jiraIssue;


    @PostMapping("/create")
    public ResponseEntity<ResponseEntity.BodyBuilder> createIssue() {

        //Post to create issue no jira
        jiraIssue.createIssue();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
