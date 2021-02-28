package com.epam.cloud.data;

import com.epam.cloud.constant.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Health {
    private final Status status;
    private final Map<String, Object> details;

    private Health(Builder builder) {
        this.status = builder.status;
        this.details = Collections.unmodifiableMap(builder.details);
    }

    public static Builder up() {
        return status(Status.UP);
    }

    public static Builder down() {
        return status(Status.DOWN);
    }

    public static Builder status(Status status) {
        return new Builder(status);
    }

    public Status getStatus() {
        return this.status;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public Map<String, Object> getDetails() {
        return this.details;
    }

    @Override
    public String toString() {
        return getStatus() + " " + getDetails();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Health) {
            Health other = (Health) obj;
            return this.status.equals(other.status) && this.details.equals(other.details);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = this.status.hashCode();
        return 13 * hashCode + this.details.hashCode();
    }

    public static class Builder {
        private Status status;
        private Map<String, Object> details;

        public Builder(Status status) {
            this.status = status;
            this.details = new LinkedHashMap<>();
        }

        public Health.Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder withDetail(String key, Object value) {
            this.details.put(key, value);
            return this;
        }

        public Health build() {
            return new Health(this);
        }

    }
}
