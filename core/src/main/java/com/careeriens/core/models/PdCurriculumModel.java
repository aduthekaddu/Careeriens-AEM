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
public class PdCurriculumModel {

    @ValueMapValue private String tabLabel;
    @ValueMapValue private String title;

    @ChildResource
    private Resource phases;

    private final List<Phase> phaseList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (phases == null) return;

        for (Resource phaseRes : phases.getChildren()) {
            ValueMap pp = phaseRes.getValueMap();
            Phase phase = new Phase(
                pp.get("phaseTitle",    ""),
                pp.get("phaseSubtitle", "")
            );

            Resource modulesRes = phaseRes.getChild("modules");
            if (modulesRes != null) {
                for (Resource modRes : modulesRes.getChildren()) {
                    ValueMap mp = modRes.getValueMap();
                    phase.getModules().add(new Module(
                        mp.get("moduleCode",  ""),
                        mp.get("moduleTitle", ""),
                        mp.get("credits",     "")
                    ));
                }
            }
            phaseList.add(phase);
        }
    }

    public String getTabLabel()        { return tabLabel != null ? tabLabel : "Curriculum"; }
    public String getTitle()           { return title; }
    public List<Phase> getPhaseList()  { return phaseList; }
    public boolean isHasPhases()       { return !phaseList.isEmpty(); }

    // ── Inner classes ──────────────────────────────────

    public static class Phase {
        private final String phaseTitle, phaseSubtitle;
        private final List<Module> modules = new ArrayList<>();

        public Phase(String t, String s) {
            this.phaseTitle    = t;
            this.phaseSubtitle = s;
        }

        public String getPhaseTitle()    { return phaseTitle; }
        public String getPhaseSubtitle() { return phaseSubtitle; }
        public List<Module> getModules() { return modules; }
        public boolean isHasModules()    { return !modules.isEmpty(); }
        public boolean isHasSubtitle()   { return phaseSubtitle != null && !phaseSubtitle.isEmpty(); }
    }

    public static class Module {
        private final String moduleCode, moduleTitle, credits;

        public Module(String c, String t, String cr) {
            this.moduleCode  = c;
            this.moduleTitle = t;
            this.credits     = cr;
        }

        public String getModuleCode()  { return moduleCode; }
        public String getModuleTitle() { return moduleTitle; }
        public String getCredits()     { return credits; }
        public boolean isHasCode()     { return moduleCode != null && !moduleCode.isEmpty(); }
        public boolean isHasCredits()  { return credits != null && !credits.isEmpty(); }
    }
}