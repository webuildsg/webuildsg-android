package com.trinitystudio.webuildsg.model.repos;

import java.io.Serializable;

/**
 * Created by liccowee on 6/14/16.
 */
public class RepoSingleModel implements Serializable
{
    private String name;
    private String html_url;
    private String description;
    private String formatted_time;
    private String language;
    private int stargazers_count;
    private int forks_count;
    private int open_issues_count;

    public String getDescription() {
        return description;
    }

    public String getFormatted_time() {
        return formatted_time;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public int getForks_count() {
        return forks_count;
    }

    public int getOpen_issues_count() {
        return open_issues_count;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }
}
