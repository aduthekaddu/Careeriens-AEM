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
public class NavbarModel {

    @ValueMapValue
    private String fileReference;
    @ValueMapValue
    private String logoAlt;
    @ValueMapValue
    private String logoLink;
    @ValueMapValue
    private String brandPart1;
    @ValueMapValue
    private String brandPart2;

    @ChildResource
    private Resource navItems;

    private List<NavItem> navigationItems = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (logoAlt == null || logoAlt.isEmpty())
            logoAlt = "Careeriens";
        if (logoLink == null || logoLink.isEmpty())
            logoLink = "/";
        if (brandPart1 == null || brandPart1.isEmpty())
            brandPart1 = "Career";
        if (brandPart2 == null || brandPart2.isEmpty())
            brandPart2 = "iens";

        if (navItems != null) {
            for (Resource child : navItems.getChildren()) {
                navigationItems.add(buildNavItem(child));
            }
        }
    }

    private NavItem buildNavItem(Resource res) {
        ValueMap p = res.getValueMap();
        boolean hasDrop = p.get("hasDropdown", false);
        NavItem item = new NavItem(p.get("navLabel", ""), p.get("navLink", "#"), hasDrop);

        Resource dropRes = res.getChild("dropdownItems");
        if (dropRes != null && hasDrop) {
            for (Resource dropChild : dropRes.getChildren()) {
                item.addCategory(buildCategory(dropChild));
            }
        }
        return item;
    }

    private DropdownCategory buildCategory(Resource res) {
        ValueMap p = res.getValueMap();
        DropdownCategory cat = new DropdownCategory(
                p.get("dropLabel", ""), p.get("dropLink", "#"));

        Resource subRes = res.getChild("subItems");
        if (subRes != null) {
            for (Resource subChild : subRes.getChildren()) {
                ValueMap sp = subChild.getValueMap();
                cat.addSubItem(new SubItem(
                        sp.get("subLabel", ""), sp.get("subLink", "#")));
            }
        }
        return cat;
    }

    public String getFileReference() {
        return fileReference != null ? fileReference.trim() : "";
    }

    public boolean isLogoConfigured() {
        return !getFileReference().isEmpty();
    }

    public String getLogoAlt() {
        return logoAlt;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public String getBrandPart1() {
        return brandPart1;
    }

    public String getBrandPart2() {
        return brandPart2;
    }

    public List<NavItem> getNavigationItems() {
        return navigationItems;
    }

    public static class NavItem {
        private final String label, link;
        private final boolean hasDropdown;
        private final List<DropdownCategory> dropdownCategories = new ArrayList<>();

        public NavItem(String label, String link, boolean hasDropdown) {
            this.label = label;
            this.link = link;
            this.hasDropdown = hasDropdown;
        }

        public void addCategory(DropdownCategory c) {
            dropdownCategories.add(c);
        }

        public String getLabel() {
            return label;
        }

        public String getLink() {
            return link;
        }

        public boolean isHasDropdown() {
            return hasDropdown;
        }

        public List<DropdownCategory> getDropdownCategories() {
            return dropdownCategories;
        }
    }

    public static class DropdownCategory {
        private final String label, link;
        private final List<SubItem> subItems = new ArrayList<>();

        public DropdownCategory(String label, String link) {
            this.label = label;
            this.link = link;
        }

        public void addSubItem(SubItem s) {
            subItems.add(s);
        }

        public String getLabel() {
            return label;
        }

        public String getLink() {
            return link;
        }

        public List<SubItem> getSubItems() {
            return subItems;
        }

        public boolean isHasSubItems() {
            return !subItems.isEmpty();
        }
    }

    public static class SubItem {
        private final String label, link;

        public SubItem(String label, String link) {
            this.label = label;
            this.link = link;
        }

        public String getLabel() {
            return label;
        }

        public String getLink() {
            return link;
        }
    }
}