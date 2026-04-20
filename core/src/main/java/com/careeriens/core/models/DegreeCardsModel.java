package com.careeriens.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Collections;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DegreeCardsModel {

    @ValueMapValue
    private String sectionTitle;

    @ValueMapValue
    private String sectionSubtitle;

    @ChildResource
    private List<DegreeCard> cards;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public String getSectionSubtitle() {
        return sectionSubtitle;
    }

    public List<DegreeCard> getCards() {
        return cards != null ? cards : Collections.emptyList();
    }

    public boolean isHasCards() {
        return cards != null && !cards.isEmpty();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class DegreeCard {

        @ValueMapValue
        private String imageRef;

        @ValueMapValue
        private String badgeText;

        @ValueMapValue
        private String badgeColor;

        @ValueMapValue
        private String iconRef;

        @ValueMapValue
        private String duration;

        @ValueMapValue
        private String title;

        @ValueMapValue
        private String description;

        @ValueMapValue
        private String knowMoreLink;

        @ValueMapValue
        private String applyLink;

        @ChildResource
        private List<FeatureItem> features;

        public String getImageRef() {
            return imageRef;
        }

        public String getBadgeText() {
            return badgeText;
        }

        public String getBadgeColor() {
            return badgeColor != null ? badgeColor : "green";
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
            return knowMoreLink != null ? knowMoreLink : "#";
        }

        public String getApplyLink() {
            return applyLink != null ? applyLink : "#";
        }

        public List<FeatureItem> getFeatures() {
            return features != null ? features : Collections.emptyList();
        }

        public boolean isHasFeatures() {
            return features != null && !features.isEmpty();
        }
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class FeatureItem {

        @ValueMapValue
        private String featureText;

        public String getFeatureText() {
            return featureText;
        }
    }
}