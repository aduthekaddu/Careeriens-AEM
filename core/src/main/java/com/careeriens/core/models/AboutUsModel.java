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
public class AboutUsModel {

    @ValueMapValue
    private String eyebrow;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String bodyText1;

    @ValueMapValue
    private String bodyText2;

    @ValueMapValue
    private String imageRef1;

    @ValueMapValue
    private String imageRef2;

    @ValueMapValue
    private String badgeNumber;

    @ValueMapValue
    private String badgeLabel;

    @ValueMapValue
    private String missionSectionTitle;

    @ValueMapValue
    private String missionImageRef1;

    @ValueMapValue
    private String missionImageRef2;

    @ChildResource
    private Resource valueItems;

    private List<ValueItem> valueList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (valueItems != null) {
            for (Resource child : valueItems.getChildren()) {
                ValueMap p = child.getValueMap();
                valueList.add(new ValueItem(
                        p.get("itemIconRef", ""),
                        p.get("itemTitle", ""),
                        p.get("itemDescription", "")));
            }
        }
    }

    public String getEyebrow() {
        return eyebrow;
    }

    public String getTitle() {
        return title;
    }

    public String getBodyText1() {
        return bodyText1;
    }

    public String getBodyText2() {
        return bodyText2;
    }

    public String getImageRef1() {
        return imageRef1;
    }

    public String getImageRef2() {
        return imageRef2;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public String getBadgeLabel() {
        return badgeLabel;
    }

    public String getMissionSectionTitle() {
        return missionSectionTitle;
    }

    public String getMissionImageRef1() {
        return missionImageRef1;
    }

    public String getMissionImageRef2() {
        return missionImageRef2;
    }

    public List<ValueItem> getValueList() {
        return valueList;
    }

    public boolean isHasValues() {
        return !valueList.isEmpty();
    }

    public static class ValueItem {
        private final String iconRef, itemTitle, description;

        public ValueItem(String iconRef, String itemTitle, String description) {
            this.iconRef = iconRef;
            this.itemTitle = itemTitle;
            this.description = description;
        }

        public String getIconRef() {
            return iconRef;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public String getDescription() {
            return description;
        }

        public boolean isIconConfigured() {
            return !iconRef.trim().isEmpty();
        }
    }
}