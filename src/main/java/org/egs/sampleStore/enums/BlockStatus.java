package org.egs.sampleStore.enums;

public enum BlockStatus {
    BLOCKED, UNBLOCKED;

    public String getStatus() {
        return name();
    }
}
