package com.joaomanoeldev.jiraintegration.usecase.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.joaomanoeldev.jiraintegration.core.dto.Fields;
import com.joaomanoeldev.jiraintegration.core.dto.Issuetype;
import com.joaomanoeldev.jiraintegration.core.dto.Project;
import com.joaomanoeldev.jiraintegration.core.dto.Root;
import com.joaomanoeldev.jiraintegration.usecase.JiraIssue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Slf4j
@Service
public class JiraIssueImpl implements JiraIssue {

    private RestTemplate restTemplate;

    private final String urlJira = "https://joao-manoel.atlassian.net";
    private final String endpoint = "/rest/api/2/issue";

    @Override
    public void createIssue() {
        restTemplate = new RestTemplate();

        Issuetype issuetype = Issuetype.builder()
                .id("10001")//id of issue
                .build();

        Project project = Project.builder()
                .key("")//project on jira
                .build();

        Fields fields = Fields.builder()
                .summary("Testing create issue on jira")
                .description("Description of jira issue")
                .issuetype(issuetype)
                .project(project)
                .build();

        Root root = Root.builder()
                .fields(fields)
                .build();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(root);
        } catch (Exception e) {
            log.error("Error: " + e);
        }

        HttpEntity<String> entity = new HttpEntity<>(json, authorizationHeaders());

        try {
            restTemplate.exchange(urlJira + endpoint, HttpMethod.POST, entity, String.class);
        } catch (RestClientException e) {
            log.error("Error: ", e);
            throw new RestClientException("Error: ", e);
        }

    }

    private HttpHeaders authorizationHeaders() {

        String plainCreds = "email:token";
        String base64Creds = Base64.getEncoder().encodeToString(plainCreds.getBytes());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Basic " + base64Creds);
        return httpHeaders;
    }
}
