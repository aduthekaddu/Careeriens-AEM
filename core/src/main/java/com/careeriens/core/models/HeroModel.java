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
public class HeroModel {

    @ValueMapValue
    private String badgeText;
    @ValueMapValue
    private String headingPart1;
    @ValueMapValue
    private String headingPart2;
    @ValueMapValue
    private String subtext;
    @ValueMapValue
    private String primaryCtaText;
    @ValueMapValue
    private String primaryCtaLink;
    @ValueMapValue
    private String secondaryCtaText;
    @ValueMapValue
    private String secondaryCtaLink;

    // Slideshow images multifield
    @ChildResource
    private Resource heroImages;

    // Stats multifield
    @ChildResource
    private Resource stats;

    private List<String> imageList = new ArrayList<>();
    private List<StatItem> statList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (primaryCtaText == null || primaryCtaText.isEmpty())
            primaryCtaText = "Explore Degrees";
        if (secondaryCtaText == null || secondaryCtaText.isEmpty())
            secondaryCtaText = "Talk to Counselor";
        if (primaryCtaLink == null || primaryCtaLink.isEmpty())
            primaryCtaLink = "#";
        if (secondaryCtaLink == null || secondaryCtaLink.isEmpty())
            secondaryCtaLink = "#";

        // Parse slideshow images
        if (heroImages != null) {
            for (Resource child : heroImages.getChildren()) {
                String ref = child.getValueMap().get("imageRef", "");
                if (!ref.trim().isEmpty())
                    imageList.add(ref.trim());
            }
        }

        // Parse stats
        if (stats != null) {
            for (Resource child : stats.getChildren()) {
                ValueMap p = child.getValueMap();
                statList.add(new StatItem(
                        p.get("statIconRef", ""),
                        p.get("statValue", ""),
                        p.get("statLabel", "")));
            }
        }
    }

    public String getBadgeText() {
        return badgeText;
    }

    public String getHeadingPart1() {
        return headingPart1;
    }

    public String getHeadingPart2() {
        return headingPart2;
    }

    public String getSubtext() {
        return subtext;
    }

    public String getPrimaryCtaText() {
        return primaryCtaText;
    }

    public String getPrimaryCtaLink() {
        return primaryCtaLink;
    }

    public String getSecondaryCtaText() {
        return secondaryCtaText;
    }

    public String getSecondaryCtaLink() {
        return secondaryCtaLink;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public boolean isImagesConfigured() {
        return !imageList.isEmpty();
    }

    // First image used as initial background (no flash on load)
    public String getFirstImage() {
        return imageList.isEmpty() ? "" : imageList.get(0);
    }

    public List<StatItem> getStatList() {
        return statList;
    }

    public static class StatItem {
        private final String iconRef, value, label;

        public StatItem(String iconRef, String value, String label) {
            this.iconRef = iconRef;
            this.value = value;
            this.label = label;
        }

        public String getIconRef() {
            return iconRef;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public boolean isIconConfigured() {
            return !iconRef.trim().isEmpty();
        }
    }
}