package com.careeriens.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(
    adaptables = SlingHttpServletRequest.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class EnrollmentGatewayModel {

    @ValueMapValue private String subtext;
    @ValueMapValue private String razorpayKeyId;
    @ValueMapValue private String termsText;

    @ChildResource
    private Resource galleryImages;

    // Injected via constructor for request access
    private final SlingHttpServletRequest request;

    private String courseName;
    private String courseFee;
    private final List<String> imageList = new ArrayList<>();

    public EnrollmentGatewayModel(SlingHttpServletRequest request) {
        this.request = request;
    }

    @PostConstruct
    protected void init() {
        // Read from URL query params — e.g. ?course=DBA&fee=£4500
        courseName = request.getParameter("course");
        courseFee  = request.getParameter("fee");

        // Fallbacks if params missing
        if (courseName == null || courseName.isEmpty()) courseName = "N/A";
        if (courseFee  == null || courseFee.isEmpty())  courseFee  = "TBD";

        if (galleryImages != null) {
            for (Resource img : galleryImages.getChildren()) {
                String ref = img.getValueMap().get("imageRef", "");
                if (!ref.isEmpty()) imageList.add(ref);
            }
        }
    }

    public String getCourseName()      { return courseName; }
    public String getCourseFee()       { return courseFee; }
    public String getSubtext()         { return subtext; }
    public String getRazorpayKeyId()   { return razorpayKeyId; }
    public String getTermsText()       { return termsText; }
    public List<String> getImageList() { return imageList; }
    public boolean isHasImages()       { return !imageList.isEmpty(); }
    public boolean isHasSubtext()      { return subtext != null && !subtext.isEmpty(); }
    public boolean isHasTerms()        { return termsText != null && !termsText.isEmpty(); }
}