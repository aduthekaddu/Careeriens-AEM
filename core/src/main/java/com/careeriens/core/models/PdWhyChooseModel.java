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
public class PdWhyChooseModel {

    @ValueMapValue
    private String tabLabel;
    @ValueMapValue
    private String title;
    @ValueMapValue
    private String titleHighlight;

    @ChildResource
    private Resource cards;

    private final List<Card> cardList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (cards != null) {
            for (Resource c : cards.getChildren()) {
                ValueMap p = c.getValueMap();
                cardList.add(new Card(
                        p.get("iconRef", ""),
                        p.get("cardTitle", ""),
                        p.get("cardDesc", ""),
                        p.get("accentColor", "blue")));
            }
        }
    }

    public String getTabLabel() {
        return tabLabel != null ? tabLabel : "Why Choose";
    }

    public String getTitle() {
        return title;
    }

    public String getTitleHighlight() {
        return titleHighlight;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public boolean isHasCards() {
        return !cardList.isEmpty();
    }

    public static class Card {
        private final String iconRef, cardTitle, cardDesc, accentColor;

        public Card(String i, String t, String d, String a) {
            this.iconRef = i;
            this.cardTitle = t;
            this.cardDesc = d;
            this.accentColor = (a == null || a.isEmpty()) ? "blue" : a;
        }

        public String getIconRef() {
            return iconRef;
        }

        public String getCardTitle() {
            return cardTitle;
        }

        public String getCardDesc() {
            return cardDesc;
        }

        public String getCardClass() {
            return "pd-why-card pd-why-card--" + accentColor;
        }

        public boolean isHasIcon() {
            return iconRef != null && !iconRef.trim().isEmpty();
        }
    }
}