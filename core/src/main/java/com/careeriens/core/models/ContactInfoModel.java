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
public class ContactInfoModel {

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String breadcrumbRootLabel;

    @ValueMapValue
    private String breadcrumbRootLink;

    @ValueMapValue
    private String breadcrumbCurrentLabel;

    @ChildResource
    private Resource infoCards;

    private List<InfoCard> cardList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (infoCards != null) {
            for (Resource child : infoCards.getChildren()) {
                ValueMap p = child.getValueMap();
                cardList.add(new InfoCard(
                        p.get("iconRef", ""),
                        p.get("cardLabel", ""),
                        p.get("cardValue", "")));
            }
        }
    }

    public List<InfoCard> getCardList() {
        return cardList;
    }

    public boolean isHasCards() {
        return !cardList.isEmpty();
    }

    public String getHeading() {
        return heading != null && !heading.trim().isEmpty() ? heading : "Contact Us";
    }

    public String getBreadcrumbRootLabel() {
        return breadcrumbRootLabel != null && !breadcrumbRootLabel.trim().isEmpty() ? breadcrumbRootLabel : "Home";
    }

    public String getBreadcrumbRootLink() {
        return breadcrumbRootLink != null && !breadcrumbRootLink.trim().isEmpty() ? breadcrumbRootLink : "#";
    }

    public String getBreadcrumbCurrentLabel() {
        return breadcrumbCurrentLabel != null && !breadcrumbCurrentLabel.trim().isEmpty() ? breadcrumbCurrentLabel
                : getHeading();
    }

    public static class InfoCard {
        private final String iconRef, cardLabel, cardValue;

        public InfoCard(String iconRef, String cardLabel, String cardValue) {
            this.iconRef = iconRef;
            this.cardLabel = cardLabel;
            this.cardValue = cardValue;
        }

        public String getIconRef() {
            return iconRef;
        }

        public String getCardLabel() {
            return cardLabel;
        }

        public String getCardValue() {
            return cardValue;
        }

        public boolean isIconConfigured() {
            return !iconRef.trim().isEmpty();
        }
    }
}