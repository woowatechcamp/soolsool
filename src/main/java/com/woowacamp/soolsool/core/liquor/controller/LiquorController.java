package com.woowacamp.soolsool.core.liquor.controller;

import static com.woowacamp.soolsool.core.liquor.exception.LiquorResultCode.LIQUOR_CREATED;
import static com.woowacamp.soolsool.core.liquor.exception.LiquorResultCode.LIQUOR_DELETED;
import static com.woowacamp.soolsool.core.liquor.exception.LiquorResultCode.LIQUOR_UPDATED;

import com.woowacamp.soolsool.core.auth.dto.NoAuth;
import com.woowacamp.soolsool.core.auth.dto.Vendor;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.exception.LiquorResultCode;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.annotation.Nullable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/liquors")
public class LiquorController {

    private final LiquorService liquorService;

    @Vendor
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveLiquor(
        @RequestBody final LiquorSaveRequest liquorSaveRequest
    ) {
        final Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);

        return ResponseEntity.created(URI.create("/liquors/" + saveLiquorId))
            .body(ApiResponse.from(LIQUOR_CREATED));
    }

    @NoAuth
    @GetMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<LiquorDetailResponse>> liquorDetail(
        @PathVariable final Long liquorId
    ) {
        final LiquorDetailResponse response = liquorService.liquorDetail(liquorId);

        return ResponseEntity.ok(ApiResponse.of(LiquorResultCode.LIQUOR_DETAIL_FOUND, response));
    }

    @NoAuth
    @GetMapping
    public ResponseEntity<ApiResponse<List<LiquorElementResponse>>> liquorList(
        @RequestParam @Nullable final LiquorBrewType brew,
        @RequestParam @Nullable final LiquorRegionType region,
        @RequestParam @Nullable final LiquorStatusType status,
        @RequestParam @Nullable final String brand,
        @PageableDefault final Pageable pageable
    ) {
        final PageRequest sortPageable = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by("createdAt").descending()
        );

        final List<LiquorElementResponse> response = liquorService
            .liquorList(brew, region, status, brand, sortPageable);

        return ResponseEntity.ok(ApiResponse.of(LiquorResultCode.LIQUOR_LIST_FOUND, response));
    }

    @Vendor
    @PutMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<Void>> modifyLiquor(
        @PathVariable Long liquorId,
        @RequestBody final LiquorModifyRequest liquorModifyRequest
    ) {
        liquorService.modifyLiquor(liquorId, liquorModifyRequest);

        return ResponseEntity.ok(ApiResponse.from(LIQUOR_UPDATED));
    }

    @Vendor
    @DeleteMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<Void>> deleteLiquor(
        @PathVariable final Long liquorId
    ) {
        liquorService.deleteLiquor(liquorId);

        return ResponseEntity.ok().body(ApiResponse.from(LIQUOR_DELETED));
    }
}
