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
public class ProgramDetailModel {

    // ── Sidebar ──────────────────────────────────────
    @ValueMapValue private String sidebarDurationLabel;
    @ValueMapValue private String sidebarDuration;
    @ValueMapValue private String sidebarInfo1;
    @ValueMapValue private String sidebarInfo2;
    @ValueMapValue private String sidebarCtaText;
    @ValueMapValue private String sidebarCtaLink;
    @ValueMapValue private String admissionTitle;
    @ValueMapValue private String admissionDesc;
    @ValueMapValue private String admissionLinkText;
    @ValueMapValue private String admissionLinkHref;

    @ChildResource
    private Resource tabs;

    private List<Tab> tabList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (tabs != null) {
            for (Resource tabRes : tabs.getChildren()) {
                ValueMap p = tabRes.getValueMap();
                String contentType = p.get("contentType", "overview");
                Tab tab = new Tab();
                tab.setTabLabel(p.get("tabLabel", "Tab"));
                tab.setContentType(contentType);

                switch (contentType) {
                    case "overview":
                        tab.setTitle(p.get("title", ""));
                        tab.setPara1(p.get("para1", ""));
                        tab.setPara2(p.get("para2", ""));
                        tab.setPara3(p.get("para3", ""));
                        tab.setHighlightsTitle(p.get("highlightsTitle", "Program Highlights"));
                        Resource hlRes = tabRes.getChild("highlights");
                        if (hlRes != null) {
                            for (Resource c : hlRes.getChildren()) {
                                String t = c.getValueMap().get("highlightText", "");
                                if (!t.isEmpty()) tab.getHighlights().add(t);
                            }
                        }
                        break;

                    case "whychoose":
                        tab.setTitle(p.get("title", ""));
                        tab.setTitleHighlight(p.get("titleHighlight", ""));
                        Resource wcRes = tabRes.getChild("whyCards");
                        if (wcRes != null) {
                            for (Resource c : wcRes.getChildren()) {
                                ValueMap cp = c.getValueMap();
                                WhyCard card = new WhyCard(
                                    cp.get("iconRef",    ""),
                                    cp.get("cardTitle",  ""),
                                    cp.get("cardDesc",   ""),
                                    cp.get("accentColor","blue")
                                );
                                tab.getWhyCards().add(card);
                            }
                        }
                        break;

                    case "curriculum":
                        tab.setTitle(p.get("title", ""));
                        Resource phRes = tabRes.getChild("phases");
                        if (phRes != null) {
                            for (Resource ph : phRes.getChildren()) {
                                ValueMap pp = ph.getValueMap();
                                Phase phase = new Phase(
                                    pp.get("phaseTitle",    ""),
                                    pp.get("phaseSubtitle", "")
                                );
                                Resource modRes = ph.getChild("modules");
                                if (modRes != null) {
                                    for (Resource m : modRes.getChildren()) {
                                        ValueMap mp = m.getValueMap();
                                        phase.getModules().add(new Module(
                                            mp.get("moduleCode",  ""),
                                            mp.get("moduleTitle", ""),
                                            mp.get("credits",     "")
                                        ));
                                    }
                                }
                                tab.getPhases().add(phase);
                            }
                        }
                        break;

                    case "studyplan":
                        tab.setTitle(p.get("title", ""));
                        Resource spRes = tabRes.getChild("planItems");
                        if (spRes != null) {
                            for (Resource c : spRes.getChildren()) {
                                ValueMap cp = c.getValueMap();
                                tab.getPlanItems().add(new PlanItem(
                                    cp.get("weekRange",  ""),
                                    cp.get("itemTitle",  ""),
                                    cp.get("itemDesc",   "")
                                ));
                            }
                        }
                        break;

                    case "dissertation":
                        tab.setTitle(p.get("title", ""));
                        Resource dpRes = tabRes.getChild("pathways");
                        if (dpRes != null) {
                            for (Resource c : dpRes.getChildren()) {
                                ValueMap cp = c.getValueMap();
                                tab.getPathways().add(new Pathway(
                                    cp.get("pathTitle", ""),
                                    cp.get("pathDesc",  "")
                                ));
                            }
                        }
                        break;

                    case "faq":
                        tab.setTitle(p.get("title", ""));
                        Resource faqRes = tabRes.getChild("faqs");
                        if (faqRes != null) {
                            for (Resource c : faqRes.getChildren()) {
                                ValueMap cp = c.getValueMap();
                                tab.getFaqs().add(new Faq(
                                    cp.get("question", ""),
                                    cp.get("answer",   "")
                                ));
                            }
                        }
                        break;
                }
                tabList.add(tab);
            }
        }
    }

    public String getSidebarDurationLabel() { return sidebarDurationLabel != null ? sidebarDurationLabel : "Total Duration"; }
    public String getSidebarDuration()      { return sidebarDuration; }
    public String getSidebarInfo1()         { return sidebarInfo1; }
    public String getSidebarInfo2()         { return sidebarInfo2; }
    public String getSidebarCtaText()       { return sidebarCtaText != null ? sidebarCtaText : "Start Application"; }
    public String getSidebarCtaLink()       { return sidebarCtaLink != null ? sidebarCtaLink : "#"; }
    public String getAdmissionTitle()       { return admissionTitle != null ? admissionTitle : "Admission Support"; }
    public String getAdmissionDesc()        { return admissionDesc; }
    public String getAdmissionLinkText()    { return admissionLinkText != null ? admissionLinkText : "Talk to Counselor →"; }
    public String getAdmissionLinkHref()    { return admissionLinkHref != null ? admissionLinkHref : "#"; }
    public List<Tab> getTabList()           { return tabList; }
    public boolean isHasTabs()              { return !tabList.isEmpty(); }

    // ── Inner classes ────────────────────────────────

    public static class Tab {
        private String tabLabel, contentType, title, para1, para2, para3;
        private String highlightsTitle, titleHighlight;
        private List<String>   highlights = new ArrayList<>();
        private List<WhyCard>  whyCards   = new ArrayList<>();
        private List<Phase>    phases     = new ArrayList<>();
        private List<PlanItem> planItems  = new ArrayList<>();
        private List<Pathway>  pathways   = new ArrayList<>();
        private List<Faq>      faqs       = new ArrayList<>();

        public void setTabLabel(String v)        { this.tabLabel = v; }
        public void setContentType(String v)     { this.contentType = v; }
        public void setTitle(String v)           { this.title = v; }
        public void setPara1(String v)           { this.para1 = v; }
        public void setPara2(String v)           { this.para2 = v; }
        public void setPara3(String v)           { this.para3 = v; }
        public void setHighlightsTitle(String v) { this.highlightsTitle = v; }
        public void setTitleHighlight(String v)  { this.titleHighlight = v; }

        public String getTabLabel()        { return tabLabel; }
        public String getContentType()     { return contentType; }
        public String getTitle()           { return title; }
        public String getPara1()           { return para1; }
        public String getPara2()           { return para2; }
        public String getPara3()           { return para3; }
        public String getHighlightsTitle() { return highlightsTitle; }
        public String getTitleHighlight()  { return titleHighlight; }

        public List<String>   getHighlights() { return highlights; }
        public List<WhyCard>  getWhyCards()   { return whyCards; }
        public List<Phase>    getPhases()     { return phases; }
        public List<PlanItem> getPlanItems()  { return planItems; }
        public List<Pathway>  getPathways()   { return pathways; }
        public List<Faq>      getFaqs()       { return faqs; }

        public boolean isOverview()      { return "overview".equals(contentType); }
        public boolean isWhychoose()     { return "whychoose".equals(contentType); }
        public boolean isCurriculum()    { return "curriculum".equals(contentType); }
        public boolean isStudyplan()     { return "studyplan".equals(contentType); }
        public boolean isDissertation()  { return "dissertation".equals(contentType); }
        public boolean isFaq()           { return "faq".equals(contentType); }

        public boolean isHasHighlights() { return !highlights.isEmpty(); }
        public boolean isHasWhyCards()   { return !whyCards.isEmpty(); }
        public boolean isHasPhases()     { return !phases.isEmpty(); }
        public boolean isHasPlanItems()  { return !planItems.isEmpty(); }
        public boolean isHasPathways()   { return !pathways.isEmpty(); }
        public boolean isHasFaqs()       { return !faqs.isEmpty(); }
    }

    public static class WhyCard {
        private final String iconRef, cardTitle, cardDesc, accentColor;
        public WhyCard(String i, String t, String d, String a) {
            iconRef = i; cardTitle = t; cardDesc = d;
            accentColor = a.isEmpty() ? "blue" : a;
        }
        public String getIconRef()     { return iconRef; }
        public String getCardTitle()   { return cardTitle; }
        public String getCardDesc()    { return cardDesc; }
        public String getAccentColor() { return accentColor; }
        public boolean isIconConfigured() { return !iconRef.trim().isEmpty(); }
        public String getCardClass()   { return "pd-why-card pd-why-card--" + accentColor; }
    }

    public static class Phase {
        private final String phaseTitle, phaseSubtitle;
        private final List<Module> modules = new ArrayList<>();
        public Phase(String t, String s) { phaseTitle = t; phaseSubtitle = s; }
        public String getPhaseTitle()    { return phaseTitle; }
        public String getPhaseSubtitle() { return phaseSubtitle; }
        public List<Module> getModules() { return modules; }
        public boolean isHasModules()    { return !modules.isEmpty(); }
    }

    public static class Module {
        private final String moduleCode, moduleTitle, credits;
        public Module(String c, String t, String cr) {
            moduleCode = c; moduleTitle = t; credits = cr;
        }
        public String getModuleCode()  { return moduleCode; }
        public String getModuleTitle() { return moduleTitle; }
        public String getCredits()     { return credits; }
    }

    public static class PlanItem {
        private final String weekRange, itemTitle, itemDesc;
        public PlanItem(String w, String t, String d) {
            weekRange = w; itemTitle = t; itemDesc = d;
        }
        public String getWeekRange() { return weekRange; }
        public String getItemTitle() { return itemTitle; }
        public String getItemDesc()  { return itemDesc; }
    }

    public static class Pathway {
        private final String pathTitle, pathDesc;
        public Pathway(String t, String d) { pathTitle = t; pathDesc = d; }
        public String getPathTitle() { return pathTitle; }
        public String getPathDesc()  { return pathDesc; }
    }

    public static class Faq {
        private final String question, answer;
        public Faq(String q, String a) { question = q; answer = a; }
        public String getQuestion() { return question; }
        public String getAnswer()   { return answer; }
    }
}