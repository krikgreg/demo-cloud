package com.epam.cloud.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Status {

    private final String code;

    public static final Status UP =  new Status("UP");
    public static final Status DOWN =  new Status("DOWN");

    public Status(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(code, status.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Status{" +
                "code='" + code + '\'' +
                '}';
    }

    @JsonProperty("status")
    public String getCode() {
        return this.code;
    }
}

