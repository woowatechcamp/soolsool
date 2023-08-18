package com.woowacamp.soolsool.core.liquor.controller;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.global.common.ApiResponse;
import com.woowacamp.soolsool.global.common.LiquorResultCode;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.annotation.Nullable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/liquors")
public class LiquorController {

    private final LiquorService liquorService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveLiquor(
        @RequestBody final SaveLiquorRequest saveLiquorRequest) {
        Long saveLiquorId = liquorService.saveLiquor(saveLiquorRequest);

        return ResponseEntity.created(URI.create("/liquors/" + saveLiquorId))
            .body(ApiResponse.of(LiquorResultCode.LIQUOR_CREATED, null));
    }

    @GetMapping("/{liquorId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<LiquorDetailResponse> liquorDetail(@PathVariable final Long liquorId) {
        return ApiResponse.of(LiquorResultCode.LIQUOR_DETAIL_FOUND, liquorService.liquorDetail(liquorId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<LiquorElementResponse>> liquorList(
            @RequestParam @Nullable final LiquorBrewType brewType,
            @RequestParam @Nullable final LiquorRegionType regionType,
            @RequestParam @Nullable final LiquorStatusType statusType,
            @RequestParam @Nullable final String brand,
            @PageableDefault final Pageable pageable
    ) {
        final PageRequest sortPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("createdAt").descending());

        final List<LiquorElementResponse> response = liquorService.liquorList(brewType, regionType, statusType,
                brand, sortPageable);

        return ApiResponse.of(LiquorResultCode.LIQUOR_LIST_FOUND, response);
    }
}
