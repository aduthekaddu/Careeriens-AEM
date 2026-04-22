package com.careeriens.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PdSidebarModel {

    @ValueMapValue
    private String durationLabel;
    @ValueMapValue
    private String duration;
    @ValueMapValue
    private String info1;
    @ValueMapValue
    private String info2;
    @ValueMapValue
    private String ctaText;
    @ValueMapValue
    private String ctaLink;
    @ValueMapValue
    private String admissionTitle;
    @ValueMapValue
    private String admissionDesc;
    @ValueMapValue
    private String admissionLinkText;
    @ValueMapValue
    private String admissionLinkHref;

    public String getDurationLabel() {
        return durationLabel != null ? durationLabel : "Total Duration";
    }

    public String getDuration() {
        return duration;
    }

    public String getInfo1() {
        return info1;
    }

    public String getInfo2() {
        return info2;
    }

    public String getCtaText() {
        return ctaText != null ? ctaText : "Start Application";
    }

    public String getCtaLink() {
        return ctaLink != null ? ctaLink : "#";
    }

    public String getAdmissionTitle() {
        return admissionTitle;
    }

    public String getAdmissionDesc() {
        return admissionDesc;
    }

    public String getAdmissionLinkText() {
        return admissionLinkText != null ? admissionLinkText : "Talk to Counselor →";
    }

    public String getAdmissionLinkHref() {
        return admissionLinkHref != null ? admissionLinkHref : "#";
    }

    public boolean isHasDuration() {
        return duration != null && !duration.isEmpty();
    }

    public boolean isHasInfo1() {
        return info1 != null && !info1.isEmpty();
    }

    public boolean isHasInfo2() {
        return info2 != null && !info2.isEmpty();
    }

    public boolean isHasAdmission() {
        return admissionTitle != null && !admissionTitle.isEmpty();
    }

    public boolean isHasCta() {
        return ctaText != null && !ctaText.isEmpty();
    }
}