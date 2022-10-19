package mercury.shareDomain.events;
import java.util.Date;

public class Backlog {
    private Long saleID;
    private Double total;
    private Date dateTime = new Date();
    
    public Backlog(Long saleID, Double total) {
        this.saleID = saleID;
        this.total = total;
    }

    public Long getSaleID() {
        return saleID;
    }

    public Double getTotal() {
        return total;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Backlog [saleID=" + saleID + ", total=" + total + ", dateTime=" + dateTime + "]";
    }


}
