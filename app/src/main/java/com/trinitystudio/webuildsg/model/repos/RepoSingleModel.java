package com.trinitystudio.webuildsg.model.repos;

/**
 * Created by liccowee on 6/14/16.
 */
public class RepoSingleModel
{
    private String name;
    private String html_url;
    private String description;
    private String formatted_time;
    private String language;

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
}
