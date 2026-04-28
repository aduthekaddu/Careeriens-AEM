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

@Model(
    adaptables = { SlingHttpServletRequest.class, Resource.class },
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PdDissertationModel {

    @ValueMapValue private String tabLabel;
    @ValueMapValue private String title;

    @ChildResource
    private Resource pathways;

    private final List<Pathway> pathwayList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (pathways == null) return;
        for (Resource p : pathways.getChildren()) {
            ValueMap vm = p.getValueMap();
            pathwayList.add(new Pathway(
                vm.get("pathwayTitle", ""),
                vm.get("pathwayDesc",  "")
            ));
        }
    }

    public String getTabLabel()              { return tabLabel != null ? tabLabel : "Dissertation"; }
    public String getTitle()                 { return title; }
    public List<Pathway> getPathwayList()    { return pathwayList; }
    public boolean isHasPathways()           { return !pathwayList.isEmpty(); }

    public static class Pathway {
        private final String pathwayTitle, pathwayDesc;

        public Pathway(String t, String d) {
            this.pathwayTitle = t;
            this.pathwayDesc  = d;
        }

        public String getPathwayTitle() { return pathwayTitle; }
        public String getPathwayDesc()  { return pathwayDesc; }
    }
}