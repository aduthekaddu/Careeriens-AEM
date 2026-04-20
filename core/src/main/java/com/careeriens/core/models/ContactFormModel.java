package com.careeriens.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactFormModel {

    @ValueMapValue
    private String formTitle;

    @ValueMapValue
    private String formSubtitle;

    @ValueMapValue
    private String submitButtonText;

    @ValueMapValue
    private String formAction;

    @ValueMapValue
    private String successMessage;

    @ValueMapValue
    private String phName;

    @ValueMapValue
    private String phEmail;

    @ValueMapValue
    private String phPhone;

    @ValueMapValue
    private String phSubject;

    @ValueMapValue
    private String phMessage;

    @ValueMapValue
    private String labelName;

    @ValueMapValue
    private String labelEmail;

    @ValueMapValue
    private String labelPhone;

    @ValueMapValue
    private String labelSubject;

    @ValueMapValue
    private String labelMessage;

    public String getFormTitle() {
        return formTitle != null ? formTitle : "Drop Us A Line";
    }

    public String getFormSubtitle() {
        return formSubtitle;
    }

    public String getSubmitButtonText() {
        return submitButtonText != null ? submitButtonText : "Submit Message";
    }

    public String getFormAction() {
        return formAction != null ? formAction : "#";
    }

    public String getSuccessMessage() {
        return successMessage != null ? successMessage : "Thank you! We'll be in touch soon.";
    }

    public String getPhName() {
        return phName != null ? phName : "John Doe";
    }

    public String getPhEmail() {
        return phEmail != null ? phEmail : "john@example.com";
    }

    public String getPhPhone() {
        return phPhone != null ? phPhone : "+91...";
    }

    public String getPhSubject() {
        return phSubject != null ? phSubject : "Inquiry about...";
    }

    public String getPhMessage() {
        return phMessage != null ? phMessage : "How can we help you?";
    }

    public String getLabelName() {
        return labelName != null ? labelName : "Your Name*";
    }

    public String getLabelEmail() {
        return labelEmail != null ? labelEmail : "Email Address*";
    }

    public String getLabelPhone() {
        return labelPhone != null ? labelPhone : "Phone No*";
    }

    public String getLabelSubject() {
        return labelSubject != null ? labelSubject : "Subject";
    }

    public String getLabelMessage() {
        return labelMessage != null ? labelMessage : "Message Here*";
    }
}