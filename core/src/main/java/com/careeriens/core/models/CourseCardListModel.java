package com.careeriens.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CourseCardListModel {

    // Section heading e.g. "Our Courses"
    @org.apache.sling.models.annotations.injectorspecific.ValueMapValue
    private String sectionTitle;

    // Section sub-heading
    @org.apache.sling.models.annotations.injectorspecific.ValueMapValue
    private String sectionSubtitle;

    @ChildResource
    private Resource cards;

    private List<CardItem> cardList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (cards != null) {
            for (Resource child : cards.getChildren()) {
                ValueMap p = child.getValueMap();
                cardList.add(new CardItem(
                        p.get("fileReference", ""),
                        p.get("categoryLabel", ""),
                        p.get("badgeColor", "green"),
                        p.get("title", ""),
                        p.get("description", ""),
                        p.get("price", ""),
                        p.get("ctaText", "View Program"),
                        p.get("ctaLink", "#")));
            }
        }
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public String getSectionSubtitle() {
        return sectionSubtitle;
    }

    public List<CardItem> getCardList() {
        return cardList;
    }

    public static class CardItem {
        private final String fileReference, categoryLabel, badgeColor;
        private final String title, description, price, ctaText, ctaLink;

        public CardItem(String fileReference, String categoryLabel, String badgeColor,
                String title, String description, String price,
                String ctaText, String ctaLink) {
            this.fileReference = fileReference;
            this.categoryLabel = categoryLabel;
            this.badgeColor = badgeColor.isEmpty() ? "green" : badgeColor;
            this.title = title;
            this.description = description;
            this.price = price;
            this.ctaText = ctaText.isEmpty() ? "View Program" : ctaText;
            this.ctaLink = ctaLink.isEmpty() ? "#" : ctaLink;
        }

        public boolean isImageConfigured() {
            return !fileReference.trim().isEmpty();
        }

        public String getFileReference() {
            return fileReference;
        }

        public String getCategoryLabel() {
            return categoryLabel;
        }

        public String getBadgeColor() {
            return badgeColor;
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

        public String getCtaText() {
            return ctaText;
        }

        public String getCtaLink() {
            return ctaLink;
        }
    }
}
