package com.woowacamp.soolsool.core.liquor.controller;

import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_CREATED;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_DELETED;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_LIST_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_UPDATED;

import com.woowacamp.soolsool.core.liquor.code.LiquorResultCode;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorListRequest;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.request.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.response.PageLiquorResponse;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.global.aop.RequestLogging;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.auth.dto.Vendor;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/liquors")
@RequiredArgsConstructor
public class LiquorController {

    private final LiquorService liquorService;

    @Vendor
    @RequestLogging
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveLiquor(
        @RequestBody final LiquorSaveRequest liquorSaveRequest
    ) {
        final Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);

        return ResponseEntity.created(URI.create("/liquors/" + saveLiquorId))
            .body(ApiResponse.from(LIQUOR_CREATED));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<LiquorDetailResponse>> liquorDetail(
        @PathVariable final Long liquorId
    ) {
        final LiquorDetailResponse response = liquorService.liquorDetail(liquorId);

        return ResponseEntity.ok(ApiResponse.of(LiquorResultCode.LIQUOR_DETAIL_FOUND, response));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/first")
    public ResponseEntity<ApiResponse<PageLiquorResponse>> getLiquorFirstList(
        @PageableDefault final Pageable pageable
    ) {
        final PageRequest sortPageable = getSortedPageable(pageable);

        final PageLiquorResponse response = liquorService.getFirstPage(sortPageable);

        return ResponseEntity.ok(ApiResponse.of(LIQUOR_LIST_FOUND, response));
    }

    @NoAuth
    @RequestLogging
    @GetMapping
    public ResponseEntity<ApiResponse<PageLiquorResponse>> liquorList(
        @ModelAttribute final LiquorListRequest liquorListRequest,
        @PageableDefault final Pageable pageable
    ) {
        final PageRequest sortPageable = getSortedPageable(pageable);

        final PageLiquorResponse response = liquorService
            .liquorListByLatest(liquorListRequest,sortPageable);

        return ResponseEntity.ok(ApiResponse.of(LIQUOR_LIST_FOUND, response));
    }

    @NoAuth
    @RequestLogging
    @GetMapping("/click")
    public ResponseEntity<ApiResponse<PageLiquorResponse>> liquorListByClick(
        @ModelAttribute final LiquorListRequest liquorListRequest,
        @PageableDefault final Pageable pageable
    ) {
        final PageRequest sortPageable = getSortedPageable(pageable);

        final PageLiquorResponse response = liquorService
            .liquorListByClick(liquorListRequest, sortPageable);

        return ResponseEntity.ok(ApiResponse.of(LIQUOR_LIST_FOUND, response));
    }

    private  PageRequest getSortedPageable(Pageable pageable) {
        return PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by("createdAt").descending()
        );
    }

    @Vendor
    @RequestLogging
    @PutMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<Void>> modifyLiquor(
        @PathVariable final Long liquorId,
        @RequestBody final LiquorModifyRequest liquorModifyRequest
    ) {
        liquorService.modifyLiquor(liquorId, liquorModifyRequest);

        return ResponseEntity.ok(ApiResponse.from(LIQUOR_UPDATED));
    }

    @Vendor
    @RequestLogging
    @DeleteMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<Void>> deleteLiquor(
        @PathVariable final Long liquorId
    ) {
        liquorService.deleteLiquor(liquorId);

        return ResponseEntity.ok().body(ApiResponse.from(LIQUOR_DELETED));
    }
}
