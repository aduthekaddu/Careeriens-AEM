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
public class FooterModel {

    @ValueMapValue
    private String brandPart1;
    @ValueMapValue
    private String brandPart2;
    @ValueMapValue
    private String tagline;
    @ValueMapValue
    private String copyrightText;

    @ValueMapValue
    private String facebookUrl;
    @ValueMapValue
    private String whatsappUrl;
    @ValueMapValue
    private String twitterUrl;
    @ValueMapValue
    private String linkedinUrl;

    @ValueMapValue
    private String phone;
    @ValueMapValue
    private String email;
    @ValueMapValue
    private String address1;
    @ValueMapValue
    private String address2;

    @ChildResource
    private Resource degreeLinks;
    @ChildResource
    private Resource quickLinks;

    private List<FooterLink> degreeLinkList = new ArrayList<>();
    private List<FooterLink> quickLinkList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (brandPart1 == null || brandPart1.isEmpty())
            brandPart1 = "Career";
        if (brandPart2 == null || brandPart2.isEmpty())
            brandPart2 = "iens";
        if (tagline == null || tagline.isEmpty())
            tagline = "Where Ambitions Take Flight.";
        if (copyrightText == null || copyrightText.isEmpty())
            copyrightText = "© 2025 Careeriens, All Rights Reserved";

        degreeLinkList = parseLinks(degreeLinks);
        quickLinkList = parseLinks(quickLinks);
    }

    private List<FooterLink> parseLinks(Resource res) {
        List<FooterLink> list = new ArrayList<>();
        if (res != null) {
            for (Resource child : res.getChildren()) {
                ValueMap p = child.getValueMap();
                list.add(new FooterLink(
                        p.get("linkLabel", ""),
                        p.get("linkUrl", "#")));
            }
        }
        return list;
    }

    public String getBrandPart1() {
        return brandPart1;
    }

    public String getBrandPart2() {
        return brandPart2;
    }

    public String getTagline() {
        return tagline;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public String getFacebookUrl() {
        return facebookUrl != null ? facebookUrl : "#";
    }

    public String getWhatsappUrl() {
        return whatsappUrl != null ? whatsappUrl : "#";
    }

    public String getTwitterUrl() {
        return twitterUrl != null ? twitterUrl : "#";
    }

    public String getLinkedinUrl() {
        return linkedinUrl != null ? linkedinUrl : "#";
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public List<FooterLink> getDegreeLinks() {
        return degreeLinkList;
    }

    public List<FooterLink> getQuickLinks() {
        return quickLinkList;
    }

    public static class FooterLink {
        private final String label, url;

        public FooterLink(String label, String url) {
            this.label = label;
            this.url = url;
        }

        public String getLabel() {
            return label;
        }

        public String getUrl() {
            return url;
        }
    }
}