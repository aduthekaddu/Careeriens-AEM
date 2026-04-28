package com.careeriens.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Model(
    adaptables = SlingHttpServletRequest.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SlotChecker {

    @Self
    private SlingHttpServletRequest request;

    private final Map<String, Boolean> slotStatus = new HashMap<>();

    @PostConstruct
    protected void init() {
        Resource currentResource = request.getResource();
        // Check all 6 slots
        for (int i = 1; i <= 6; i++) {
            slotStatus.put("slot" + i, hasAuthoredContent(currentResource, "slot" + i));
        }
    }

    private boolean hasAuthoredContent(Resource parent, String slotName) {
        Resource slot = parent.getChild(slotName);
        if (slot == null) return false;

        for (Resource child : slot.getChildren()) {
            String name = child.getName();
            String type = child.getValueMap().get("sling:resourceType", "");

            // Skip parsys-internal nodes
            if (name.equals("par") || name.equals("new_section")
                    || type.contains("foundation/components/parsys")
                    || name.startsWith("jcr:")) {
                continue;
            }
            return true; // real authored component found
        }
        return false;
    }

    public boolean isSlot1() { return slotStatus.getOrDefault("slot1", false); }
    public boolean isSlot2() { return slotStatus.getOrDefault("slot2", false); }
    public boolean isSlot3() { return slotStatus.getOrDefault("slot3", false); }
    public boolean isSlot4() { return slotStatus.getOrDefault("slot4", false); }
    public boolean isSlot5() { return slotStatus.getOrDefault("slot5", false); }
    public boolean isSlot6() { return slotStatus.getOrDefault("slot6", false); }
}