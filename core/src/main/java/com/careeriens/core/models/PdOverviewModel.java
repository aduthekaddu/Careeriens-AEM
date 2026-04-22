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
public class PdOverviewModel {

    @ValueMapValue
    private String tabLabel;
    @ValueMapValue
    private String title;
    @ValueMapValue
    private String para1;
    @ValueMapValue
    private String para2;
    @ValueMapValue
    private String para3;
    @ValueMapValue
    private String highlightsTitle;

    @ChildResource
    private Resource highlights;

    private List<String> highlightList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (highlights != null) {
            for (Resource c : highlights.getChildren()) {
                String t = c.getValueMap().get("highlightText", "");
                if (!t.isEmpty())
                    highlightList.add(t);
            }
        }
    }

    public String getTabLabel() {
        return tabLabel != null ? tabLabel : "Overview";
    }

    public String getTitle() {
        return title;
    }

    public String getPara1() {
        return para1;
    }

    public String getPara2() {
        return para2;
    }

    public String getPara3() {
        return para3;
    }

    public String getHighlightsTitle() {
        return highlightsTitle != null ? highlightsTitle : "Program Highlights";
    }

    public List<String> getHighlightList() {
        return highlightList;
    }

    public boolean isHasHighlights() {
        return !highlightList.isEmpty();
    }

    public boolean isHasMore() {
        return para2 != null && !para2.isEmpty();
    }
}