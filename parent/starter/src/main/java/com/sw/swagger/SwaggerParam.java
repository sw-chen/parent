package com.sw.swagger;

import java.util.ArrayList;
import java.util.List;

public class SwaggerParam {
    private String name;
    private String description;
    private String defaultValue;
    private boolean required;
    private boolean allowMultiple;
    private String modelRef = "string";
    private RangeValue allowableRangeValues;
    private ListValue allowableListValues;
    private String paramType = "header";
    private String paramAccess;
    private boolean hidden;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isAllowMultiple() {
        return allowMultiple;
    }

    public void setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public String getModelRef() {
        return modelRef;
    }

    public void setModelRef(String modelRef) {
        this.modelRef = modelRef;
    }

    public RangeValue getAllowableRangeValues() {
        return allowableRangeValues;
    }

    public void setAllowableRangeValues(RangeValue allowableRangeValues) {
        this.allowableRangeValues = allowableRangeValues;
    }

    public ListValue getAllowableListValues() {
        return allowableListValues;
    }

    public void setAllowableListValues(ListValue allowableListValues) {
        this.allowableListValues = allowableListValues;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamAccess() {
        return paramAccess;
    }

    public void setParamAccess(String paramAccess) {
        this.paramAccess = paramAccess;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public static class RangeValue {
        private String min;
        private String max;
        private boolean exclusiveMin;
        private boolean exclusiveMax;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public boolean isExclusiveMin() {
            return exclusiveMin;
        }

        public void setExclusiveMin(boolean exclusiveMin) {
            this.exclusiveMin = exclusiveMin;
        }

        public boolean isExclusiveMax() {
            return exclusiveMax;
        }

        public void setExclusiveMax(boolean exclusiveMax) {
            this.exclusiveMax = exclusiveMax;
        }


    }

    public static class ListValue {
        private List<String> values = new ArrayList();
        private String valueType = "string";

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public String getValueType() {
            return valueType;
        }

        public void setValueType(String valueType) {
            this.valueType = valueType;
        }

    }

}
