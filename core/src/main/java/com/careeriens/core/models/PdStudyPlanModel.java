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
public class PdStudyPlanModel {

  @ValueMapValue
  private String tabLabel;
  @ValueMapValue
  private String title;

  @ChildResource
  private Resource phases;

  private final List<Phase> phaseList = new ArrayList<>();

  @PostConstruct
  protected void init() {
    if (phases == null)
      return;
    for (Resource phaseRes : phases.getChildren()) {
      ValueMap pp = phaseRes.getValueMap();
      Phase phase = new Phase(
          pp.get("phaseTitle", ""),
          pp.get("phaseSubtitle", ""));
      Resource topicsRes = phaseRes.getChild("topics");
      if (topicsRes != null) {
        for (Resource t : topicsRes.getChildren()) {
          String topic = t.getValueMap().get("topic", "");
          if (!topic.isEmpty())
            phase.getTopics().add(topic);
        }
      }
      phaseList.add(phase);
    }
  }

  public String getTabLabel() {
    return tabLabel != null ? tabLabel : "Study Plan";
  }

  public String getTitle() {
    return title;
  }

  public List<Phase> getPhaseList() {
    return phaseList;
  }

  public boolean isHasPhases() {
    return !phaseList.isEmpty();
  }

  public static class Phase {
    private final String phaseTitle, phaseSubtitle;
    private final List<String> topics = new ArrayList<>();

    public Phase(String t, String s) {
      this.phaseTitle = t;
      this.phaseSubtitle = s;
    }

    public String getPhaseTitle() {
      return phaseTitle;
    }

    public String getPhaseSubtitle() {
      return phaseSubtitle;
    }

    public List<String> getTopics() {
      return topics;
    }

    public boolean isHasSubtitle() {
      return phaseSubtitle != null && !phaseSubtitle.isEmpty();
    }

    public boolean isHasTopics() {
      return !topics.isEmpty();
    }
  }
}