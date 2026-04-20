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
public class DegreeCardsModel {

    @ValueMapValue
    private String sectionTitle;

    @ValueMapValue
    private String sectionSubtitle;

    @ChildResource
    private Resource cards;

    private List<DegreeCard> cardList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (cards != null) {
            for (Resource cardResource : cards.getChildren()) {
                ValueMap p = cardResource.getValueMap();

                List<String> features = new ArrayList<>();
                Resource featuresNode = cardResource.getChild("features");
                if (featuresNode != null) {
                    for (Resource featChild : featuresNode.getChildren()) {
                        String text = featChild.getValueMap().get("featureText", "");
                        if (!text.isEmpty())
                            features.add(text);
                    }
                }

                cardList.add(new DegreeCard(
                        p.get("imageRef", ""),
                        p.get("badgeText", ""),
                        p.get("badgeColor", "blue"),
                        p.get("iconRef", ""),
                        p.get("duration", ""),
                        p.get("title", ""),
                        p.get("description", ""),
                        p.get("knowMoreLink", "#"),
                        p.get("applyLink", "#"),
                        features));
            }
        }
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public String getSectionSubtitle() {
        return sectionSubtitle;
    }

    public List<DegreeCard> getCardList() {
        return cardList;
    }

    public boolean isHasCards() {
        return !cardList.isEmpty();
    }

    public static class DegreeCard {
        private final String imageRef, badgeText, badgeColor;
        private final String iconRef, duration, title, description;
        private final String knowMoreLink, applyLink;
        private final List<String> features;

        public DegreeCard(String imageRef, String badgeText, String badgeColor,
                String iconRef, String duration, String title,
                String description, String knowMoreLink, String applyLink,
                List<String> features) {
            this.imageRef = imageRef;
            this.badgeText = badgeText;
            this.badgeColor = badgeColor.isEmpty() ? "blue" : badgeColor;
            this.iconRef = iconRef;
            this.duration = duration;
            this.title = title;
            this.description = description;
            this.knowMoreLink = knowMoreLink.isEmpty() ? "#" : knowMoreLink;
            this.applyLink = applyLink.isEmpty() ? "#" : applyLink;
            this.features = features;
        }

        public boolean isImageConfigured() {
            return !imageRef.trim().isEmpty();
        }

        public boolean isIconConfigured() {
            return !iconRef.trim().isEmpty();
        }

        public boolean isHasFeatures() {
            return !features.isEmpty();
        }

        public String getImageRef() {
            return imageRef;
        }

        public String getBadgeText() {
            return badgeText;
        }

        public String getBadgeColor() {
            return badgeColor;
        }

        public String getCardClass() {
            return "dc-card dc-card--" + badgeColor;
        }

        public String getIconRef() {
            return iconRef;
        }

        public String getDuration() {
            return duration;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getKnowMoreLink() {
            return knowMoreLink;
        }

        public String getApplyLink() {
            return applyLink;
        }

        public List<String> getFeatures() {
            return features;
        }
    }
}