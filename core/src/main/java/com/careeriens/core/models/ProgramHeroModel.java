package com.careeriens.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProgramHeroModel {

    @ValueMapValue
    private String backLinkText;

    @ValueMapValue
    private String backLinkHref;

    @ValueMapValue
    private String categoryBadge;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String heroImageRef;

    @ValueMapValue
    private String accreditedLabel;

    @ValueMapValue
    private String accreditedIconRef;

    @ValueMapValue
    private String primaryCtaText;

    @ValueMapValue
    private String primaryCtaLink;

    @ValueMapValue
    private String secondaryCtaText;

    @ValueMapValue
    private String secondaryCtaLink;

    @ValueMapValue
    private String bgColor;

    @ChildResource
    private Resource features;

    private List<String> featureList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (features != null) {
            for (Resource child : features.getChildren()) {
                String text = child.getValueMap().get("featureText", "");
                if (!text.isEmpty())
                    featureList.add(text);
            }
        }
    }

    public String getBackLinkText() {
        return backLinkText != null ? backLinkText : "Back to Programs";
    }

    public String getBackLinkHref() {
        return backLinkHref != null ? backLinkHref : "#";
    }

    public String getCategoryBadge() {
        return categoryBadge;
    }

    public String getTitle() {
        return title;
    }

    public String getHeroImageRef() {
        return heroImageRef;
    }

    public String getAccreditedLabel() {
        return accreditedLabel;
    }

    public String getAccreditedIconRef() {
        return accreditedIconRef;
    }

    public String getPrimaryCtaText() {
        return primaryCtaText != null ? primaryCtaText : "Enroll Now";
    }

    public String getPrimaryCtaLink() {
        return primaryCtaLink != null ? primaryCtaLink : "#";
    }

    public String getSecondaryCtaText() {
        return secondaryCtaText != null ? secondaryCtaText : "Enquire Now";
    }

    public String getSecondaryCtaLink() {
        return secondaryCtaLink != null ? secondaryCtaLink : "#";
    }

    public String getBgColor() {
        return bgColor != null ? bgColor : "#1e3d2a";
    }

    public String getBgStyle() {
        return "background-color:" + getBgColor();
    }

    public List<String> getFeatureList() {
        return featureList;
    }

    public boolean isHasFeatures() {
        return !featureList.isEmpty();
    }

    public boolean isImageConfigured() {
        return heroImageRef != null && !heroImageRef.trim().isEmpty();
    }

    public boolean isAccredited() {
        return accreditedLabel != null && !accreditedLabel.trim().isEmpty();
    }

    public boolean isBackLinkVisible() {
        return backLinkText != null && !backLinkText.trim().isEmpty();
    }

    public boolean isIconConfigured() {
        return accreditedIconRef != null && !accreditedIconRef.trim().isEmpty();
    }
}