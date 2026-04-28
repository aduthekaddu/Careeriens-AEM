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
public class PdFaqModel {

    @ValueMapValue private String tabLabel;
    @ValueMapValue private String title;

    @ChildResource
    private Resource faqs;

    private final List<FaqItem> faqList = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (faqs == null) return;
        for (Resource faq : faqs.getChildren()) {
            ValueMap p = faq.getValueMap();
            faqList.add(new FaqItem(
                p.get("question", ""),
                p.get("answer",   "")
            ));
        }
    }

    public String getTabLabel()          { return tabLabel != null ? tabLabel : "FAQs"; }
    public String getTitle()             { return title; }
    public List<FaqItem> getFaqList()    { return faqList; }
    public boolean isHasFaqs()           { return !faqList.isEmpty(); }

    public static class FaqItem {
        private final String question, answer;

        public FaqItem(String q, String a) {
            this.question = q;
            this.answer   = a;
        }

        public String getQuestion() { return question; }
        public String getAnswer()   { return answer; }
    }
}