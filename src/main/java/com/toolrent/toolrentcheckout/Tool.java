package com.toolrent.toolrentcheckout;

public class Tool {
    private final String toolCode;
    private final String brand;
    private final ToolType toolType;

    public Tool(String toolCode, String brand, ToolType toolType) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getBrand() {
        return brand;
    }

    public ToolType getToolType() {
        return toolType;
    }

}