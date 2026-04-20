package com.careeriens.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CourseCardModel {

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String categoryLabel;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String price;

    @ValueMapValue
    private String ctaLink;

    @ValueMapValue
    private String ctaText;

    @ValueMapValue
    private String badgeColor;

    @PostConstruct
    protected void init() {
        if (ctaText == null || ctaText.isEmpty())
            ctaText = "View Program";
        if (badgeColor == null || badgeColor.isEmpty())
            badgeColor = "green";
        if (ctaLink == null || ctaLink.isEmpty())
            ctaLink = "#";
    }

    public String getFileReference() {
        return fileReference != null ? fileReference.trim() : "";
    }

    public boolean isImageConfigured() {
        return !getFileReference().isEmpty();
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCtaLink() {
        return ctaLink;
    }

    public String getCtaText() {
        return ctaText;
    }

    public String getBadgeColor() {
        return badgeColor;
    }
}