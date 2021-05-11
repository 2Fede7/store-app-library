package it.gruppopam.app_common.mapper;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import it.gruppopam.app_common.dto.SupplierDto;
import it.gruppopam.app_common.model.DistributionMode;
import it.gruppopam.app_common.model.entity.Supplier;
import it.gruppopam.app_common.model.entity.SupplierDistributionMode;
import it.gruppopam.app_common.model.relations.SupplierAnagraphicWithDistributionMode;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;


public class SupplierMapper implements BaseMapper<SupplierDto, SupplierAnagraphicWithDistributionMode> {

    @Inject
    public SupplierMapper() {
    }

    @Override
    public SupplierDto mapToDto(SupplierAnagraphicWithDistributionMode article) {
        return null;
    }

    @Override
    public List<SupplierDto> mapToDto(List<SupplierAnagraphicWithDistributionMode> list) {
        return null;
    }

    @Override
    public SupplierAnagraphicWithDistributionMode mapToModel(SupplierDto supplierDto) {
        SupplierAnagraphicWithDistributionMode supplierAnagraphic = new SupplierAnagraphicWithDistributionMode();
        supplierAnagraphic.setSupplier(mapSupplierDtoToModel(supplierDto));
        supplierAnagraphic.setDistributionModes(mapDistributionModesDtoToModel(supplierDto.getDistributionModes(), supplierDto.getSupplierId()));
        return supplierAnagraphic;
    }

    @Override
    public List<SupplierAnagraphicWithDistributionMode> mapToModel(List<SupplierDto> supplierDtos) {
        if (isEmpty(supplierDtos)) {
            return null;
        }
        return Stream.of(supplierDtos).map(this::mapToModel).collect(toList());
    }

    private Supplier mapSupplierDtoToModel(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setActive(supplierDto.getActive());
        supplier.setSequenceNumber(supplierDto.getSequenceNumber());
        supplier.setSupplierId(supplierDto.getSupplierId());
        supplier.setSupplierName(supplierDto.getSupplierName());
        return supplier;
    }

    private List<SupplierDistributionMode> mapDistributionModesDtoToModel(List<DistributionMode> distributionModes, Long supplierId) {
        return Stream.of(distributionModes).map(dm -> new SupplierDistributionMode(supplierId, dm)).collect(toList());
    }
}
