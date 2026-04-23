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
  private Resource items;

  private final List<TimelineItem> itemList = new ArrayList<>();

  @PostConstruct
  protected void init() {
    if (items == null)
      return;
    for (Resource item : items.getChildren()) {
      ValueMap p = item.getValueMap();
      itemList.add(new TimelineItem(
          p.get("weekLabel", ""),
          p.get("itemTitle", ""),
          p.get("itemDesc", "")));
    }
  }

  public String getTabLabel() {
    return tabLabel != null ? tabLabel : "Study Plan";
  }

  public String getTitle() {
    return title;
  }

  public List<TimelineItem> getItemList() {
    return itemList;
  }

  public boolean isHasItems() {
    return !itemList.isEmpty();
  }

  public static class TimelineItem {
    private final String weekLabel, itemTitle, itemDesc;

    public TimelineItem(String w, String t, String d) {
      this.weekLabel = w;
      this.itemTitle = t;
      this.itemDesc = d;
    }

    public String getWeekLabel() {
      return weekLabel;
    }

    public String getItemTitle() {
      return itemTitle;
    }

    public String getItemDesc() {
      return itemDesc;
    }

    public boolean isHasWeekLabel() {
      return weekLabel != null && !weekLabel.isEmpty();
    }

    public boolean isHasDesc() {
      return itemDesc != null && !itemDesc.isEmpty();
    }
  }
}