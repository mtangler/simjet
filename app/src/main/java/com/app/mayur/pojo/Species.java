package com.app.mayur.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Species {

    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("next")
    @Expose
    public String next;
    @SerializedName("previous")
    @Expose
    public String previous;
    @SerializedName("results")
    @Expose
    public List<Result> results = new ArrayList<>();

    public int getCount() {
        if (count == null) return 0;
        return count;
    }

    public List<Result> getResults() {
        return results;
    }

    public class Result {

        // bussiness logic
        transient public boolean isExtinct = true;

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("classification")
        @Expose
        public String classification;
        @SerializedName("designation")
        @Expose
        public String designation;

        public boolean isExtinct() {
            return isExtinct;
        }

        public void setExtinct(boolean extinct) {
            isExtinct = extinct;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassification() {
            return classification;
        }

        public String getDesignation() {
            return designation;
        }

    }
}