package com.andrewhoover.sctool.data.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by andrew on 3/24/2018.
 */
public class Division {

    @JsonProperty(value="id")
    private Integer id;
    @JsonProperty(value="ladder_id")
    private Integer ladder_id;
    @JsonProperty(value="member_count")
    private Integer member_count;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLadderId(Integer ladder_id) {
        this.ladder_id = ladder_id;
    }

    public void setMemberCount(Integer member_count) {
        this.member_count = member_count;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLadderId() {
        return ladder_id;
    }

    public Integer getMemberCount() {
        return member_count;
    }
}
