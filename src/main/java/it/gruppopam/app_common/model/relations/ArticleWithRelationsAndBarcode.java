package it.gruppopam.app_common.model.relations;

import androidx.room.Relation;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.gruppopam.app_common.model.entity.Barcode;
import it.gruppopam.app_common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;

@Getter
@Setter
public class ArticleWithRelationsAndBarcode extends ArticleWithRelations {

    @Relation(entity = Barcode.class, parentColumn = ARTICLE_ID, entityColumn = ARTICLE_ID)
    private List<Barcode> activeAndFutureBarcodes;

    public List<Barcode> getActiveAndFutureBarcodes() {
        Barcode activeBarcode = getActiveBarcode();
        Barcode futureBarcode = getFutureBarcode();
        List<Barcode> barcodes = new ArrayList<>();
        if (activeBarcode != null) {
            barcodes.add(activeBarcode);
        }
        if (futureBarcode != null) {
            barcodes.add(futureBarcode);
        }
        return barcodes;
    }

    public Barcode getActiveBarcode() {
        boolean hasPromotions = !isEmpty(activeAndFutureBarcodes);
        return hasPromotions ? getActive(activeAndFutureBarcodes) : null;
    }

    private Barcode getActive(List<Barcode> barcodes) {
        for (Barcode b : barcodes) {
            if (b.isActive()) {
                return b;
            }
        }
        return null;
    }

    public Barcode getFutureBarcode() {
        Collections.sort(activeAndFutureBarcodes, (lhs, rhs) -> lhs.getStartDate().compareTo(rhs.getStartDate()));
        List<Barcode> futureBarcodes = filterFutureBarcodes(activeAndFutureBarcodes);
        return futureBarcodes.size() > 0 ? futureBarcodes.get(0) : null;
    }

    private List<Barcode> filterFutureBarcodes(List<Barcode> sortedPBarcodes) {
        List<Barcode> futureBarcodes = new ArrayList<>();
        Date today = DateTime.now().withTimeAtStartOfDay().toDate();
        for (Barcode barcode : sortedPBarcodes) {
            Date startDate = DateUtils.truncateTime(barcode.getStartDate());
            if (startDate.after(today)) {
                futureBarcodes.add(barcode);
            }
        }
        return futureBarcodes;
    }

}
