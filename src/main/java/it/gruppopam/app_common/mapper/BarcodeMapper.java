package it.gruppopam.app_common.mapper;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import it.gruppopam.app_common.dto.BarcodeDto;
import it.gruppopam.app_common.model.entity.Barcode;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;

public class BarcodeMapper implements BaseMapper<BarcodeDto, Barcode> {

    @Inject
    public BarcodeMapper() {
    }

    @Override
    public BarcodeDto mapToDto(Barcode article) {
        return null;
    }

    @Override
    public List<BarcodeDto> mapToDto(List<Barcode> list) {
        return null;
    }

    @Override
    public Barcode mapToModel(BarcodeDto barcodeDto) {
        Barcode barcode = new Barcode();
        barcode.setArticleId(barcodeDto.getArticleId());
        barcode.setBarcodeString(barcodeDto.getBarcodeString());
        barcode.setEndDate(barcodeDto.getEndDate());
        barcode.setStartDate(barcodeDto.getStartDate());
        barcode.setType(barcodeDto.getType());
        barcode.setSequenceNumber(barcodeDto.getSequenceNumber());
        return barcode;
    }

    @Override
    public List<Barcode> mapToModel(List<BarcodeDto> barcodeDtos) {
        if (isEmpty(barcodeDtos)) {
            return null;
        }
        return Stream.of(barcodeDtos).map(this::mapToModel).collect(toList());
    }
}
