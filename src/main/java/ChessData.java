import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChessData {
    double SRCSEQ;
    double SRCDAT;

    public double getSRCSEQ() {
        return SRCSEQ;
    }

    public void setSRCSEQ(double SRCSEQ) {
        this.SRCSEQ = SRCSEQ;
    }

    public double getSRCDAT() {
        return SRCDAT;
    }

    public void setSRCDAT(double SRCDAT) {
        this.SRCDAT = SRCDAT;
    }

    public String getSRCDTA() {
        return SRCDTA;
    }

    public void setSRCDTA(String SRCDTA) {
        this.SRCDTA = SRCDTA;
    }

    String SRCDTA;

}
