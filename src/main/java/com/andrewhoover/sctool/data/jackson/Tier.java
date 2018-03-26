package com.andrewhoover.sctool.data.jackson;

import com.andrewhoover.sctool.data.jackson.Division;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by andrew on 3/24/2018.
 */
public class Tier {

    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "min_rating")
    private Integer min_rating;
    @JsonProperty(value = "max_rating")
    private Integer max_rating;
    @JsonProperty(value = "division")
    private List<Division> division;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMinRating(Integer min_rating) {
        this.min_rating = min_rating;
    }

    public void setMaxRating(Integer max_rating) {
        this.max_rating = max_rating;
    }

    public void setDivisions(List<Division> division) {
        this.division = division;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMinRating() {
        return min_rating;
    }

    public Integer getMaxRating() {
        return max_rating;
    }

    public List<Division> getDivisions() {
        return division;
    }
}
