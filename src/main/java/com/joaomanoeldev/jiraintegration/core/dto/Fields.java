package com.joaomanoeldev.jiraintegration.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Fields{
    public Project project;
    public String summary;
    public String description;
    public Issuetype issuetype;
}
