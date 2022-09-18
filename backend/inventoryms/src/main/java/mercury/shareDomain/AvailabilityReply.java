package mercury.shareDomain;

import java.io.Serializable;

public class AvailabilityReply implements Serializable {
    private boolean isAvailable;

    public AvailabilityReply() {
        this.isAvailable = false;
    }

    public AvailabilityReply(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
