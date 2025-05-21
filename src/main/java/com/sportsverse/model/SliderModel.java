package com.sportsverse.model;

/**
 * SliderModel represents a slider for the homepage or promotional sections in SportsVerse.
 * It contains an image URL, text, subtext, order for display, an active status, and a link.
 */
public class SliderModel {

    // Unique identifier for the slider
    private int sliderId;

    // The URL of the image to be displayed in the slider
    private String sliderImageUrl;

    // The main text for the slider
    private String sliderText;

    // The subtext associated with the slider
    private String sliderSubText;

    // The order in which the slider is displayed
    private int sliderOrder;

    // Indicates whether the slider is active or not
    private boolean active;

    // The URL for the call-to-action link
    private String sliderLink;

    // Default constructor
    public SliderModel() {
    }

    /**
     * Parameterized constructor to initialize all fields.
     */
    public SliderModel(int sliderId, String sliderImageUrl, String sliderText, String sliderSubText, 
            int sliderOrder, boolean active, String sliderLink) {
        this.sliderId = sliderId;
        this.sliderImageUrl = sliderImageUrl;
        this.sliderText = sliderText;
        this.sliderSubText = sliderSubText;
        this.sliderOrder = sliderOrder;
        this.active = active;
        this.sliderLink = sliderLink;
    }

    // Getters and Setters
    public int getSliderId() {
        return sliderId;
    }

    public void setSliderId(int sliderId) {
        this.sliderId = sliderId;
    }

    public String getSliderImageUrl() {
        return sliderImageUrl;
    }

    public void setSliderImageUrl(String sliderImageUrl) {
        this.sliderImageUrl = sliderImageUrl;
    }

    public String getSliderText() {
        return sliderText;
    }

    public void setSliderText(String sliderText) {
        this.sliderText = sliderText;
    }

    public String getSliderSubText() {
        return sliderSubText;
    }

    public void setSliderSubText(String sliderSubText) {
        this.sliderSubText = sliderSubText;
    }

    public int getSliderOrder() {
        return sliderOrder;
    }

    public void setSliderOrder(int sliderOrder) {
        this.sliderOrder = sliderOrder;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSliderLink() {
        return sliderLink;
    }

    public void setSliderLink(String sliderLink) {
        this.sliderLink = sliderLink;
    }
}