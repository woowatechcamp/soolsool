package com.woowacamp.soolsool.core.liquor.service;

import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_BREW_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_REGION_TYPE_FOUND;
import static com.woowacamp.soolsool.global.exception.LiquorErrorCode.NOT_LIQUOR_STATUS_TYPE_FOUND;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import com.woowacamp.soolsool.global.exception.LiquorErrorCode;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;
    private final LiquorStatusRepository liquorStatusRepository;
    private final LiquorRegionRepository liquorRegionRepository;
    private final LiquorTypeRepository liquorTypeRepository;

    public Long saveLiquor(final SaveLiquorRequest request) {
        LiquorType liquorType = liquorTypeRepository
            .findByType(LiquorBrewType.findType(request.getTypeName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_BREW_TYPE_FOUND));

        LiquorRegion liquorRegion = liquorRegionRepository
            .findByType(LiquorRegionType.findType(request.getRegionName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_REGION_TYPE_FOUND));

        LiquorStatus liquorStatus = liquorStatusRepository
            .findByType(LiquorStatusType.findType(request.getStatusName()))
            .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_STATUS_TYPE_FOUND));

        Liquor liquor = Liquor.of(
            liquorType, liquorRegion,
            liquorStatus, request
        );
        return liquorRepository.save(liquor).getId();
    }

    public LiquorDetailResponse liquorDetail(final Long liquorId) {
        final Liquor liquor = liquorRepository.findById(liquorId)
                .orElseThrow(() -> new SoolSoolException(LiquorErrorCode.NOT_LIQUOR_FOUND));

        return new LiquorDetailResponse(
                liquor.getId(),
                liquor.getName().getName(),
                liquor.getPrice().getPrice().toString(),
                liquor.getBrand().getBrand(),
                liquor.getImageUrl().getImageUrl(),
                liquor.getStock().getStock(),
                liquor.getAlcohol().getAlcohol(),
                liquor.getVolume().getVolume()
        );
    }

    public List<LiquorElementResponse> liquorList(final Pageable pageable) {
        Page<Liquor> liquors = liquorRepository.findAll(pageable);

        return liquors.getContent().stream()
                .map(liquor -> new LiquorElementResponse(
                        liquor.getId(),
                        liquor.getName().getName(),
                        liquor.getPrice().getPrice().toString(),
                        liquor.getImageUrl().getImageUrl(),
                        liquor.getStock().getStock()
                ))
                .collect(Collectors.toList());
    }
}
