package com.woowacamp.soolsool.core.liquor.controller;

import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_CREATED;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_DELETED;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_LIST_FOUND;
import static com.woowacamp.soolsool.core.liquor.code.LiquorResultCode.LIQUOR_UPDATED;

import com.woowacamp.soolsool.core.liquor.code.LiquorResultCode;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.dto.LiquorDetailResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorModifyRequest;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSaveRequest;
import com.woowacamp.soolsool.core.liquor.dto.PageLiquorResponse;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.auth.dto.Vendor;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/liquors")
@RequiredArgsConstructor
public class LiquorController {

    private final LiquorService liquorService;

    @Vendor
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveLiquor(
        final HttpServletRequest httpServletRequest,
        @RequestBody final LiquorSaveRequest liquorSaveRequest
    ) {
        log.info("{} {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(), liquorSaveRequest);

        final Long saveLiquorId = liquorService.saveLiquor(liquorSaveRequest);

        return ResponseEntity.created(URI.create("/liquors/" + saveLiquorId))
            .body(ApiResponse.from(LIQUOR_CREATED));
    }

    @NoAuth
    @GetMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<LiquorDetailResponse>> liquorDetail(
        final HttpServletRequest httpServletRequest,
        @PathVariable final Long liquorId
    ) {
        log.info("{} {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath());

        final LiquorDetailResponse response = liquorService.liquorDetail(liquorId);

        return ResponseEntity.ok(ApiResponse.of(LiquorResultCode.LIQUOR_DETAIL_FOUND, response));
    }

    @NoAuth
    @GetMapping("/first")
    public ResponseEntity<ApiResponse<PageLiquorResponse>> getLiquorFirstList(
        final HttpServletRequest httpServletRequest,
        @PageableDefault final Pageable pageable
    ) {
        log.info("{} {} |  Pageable : {}", httpServletRequest.getMethod(),
            httpServletRequest.getServletPath(), pageable);

        final PageRequest sortPageable = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by("createdAt").descending()
        );

        final PageLiquorResponse response = liquorService.getFirstPage(sortPageable);

        return ResponseEntity.ok(ApiResponse.of(LIQUOR_LIST_FOUND, response));
    }

    @NoAuth
    @GetMapping
    public ResponseEntity<ApiResponse<PageLiquorResponse>> liquorList(
        final HttpServletRequest httpServletRequest,
        @RequestParam("brew") @Nullable final LiquorBrewType brew,
        @RequestParam("region") @Nullable final LiquorRegionType region,
        @RequestParam("status") @Nullable final LiquorStatusType status,
        @RequestParam("brand") @Nullable final String brand,
        @RequestParam @Nullable final Long cursorId,
        @PageableDefault final Pageable pageable
    ) {
        log.info("{} {} | brew : {} | region : {} | status : {} | brand : {} | Pageable : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(),
            brew, region, status, brand, pageable);

        final PageRequest sortPageable = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by("createdAt").descending()
        );

        final PageLiquorResponse response = liquorService
            .liquorList(brew, region, status, brand, sortPageable, cursorId);

        return ResponseEntity.ok(ApiResponse.of(LIQUOR_LIST_FOUND, response));
    }

    @Vendor
    @PutMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<Void>> modifyLiquor(
        final HttpServletRequest httpServletRequest,
        @PathVariable final Long liquorId,
        @RequestBody final LiquorModifyRequest liquorModifyRequest
    ) {
        log.info("{} {} | request : {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath(),
            liquorModifyRequest);

        liquorService.modifyLiquor(liquorId, liquorModifyRequest);

        return ResponseEntity.ok(ApiResponse.from(LIQUOR_UPDATED));
    }

    @Vendor
    @DeleteMapping("/{liquorId}")
    public ResponseEntity<ApiResponse<Void>> deleteLiquor(
        final HttpServletRequest httpServletRequest,
        @PathVariable final Long liquorId
    ) {
        log.info("{} {}",
            httpServletRequest.getMethod(), httpServletRequest.getServletPath());

        liquorService.deleteLiquor(liquorId);

        return ResponseEntity.ok().body(ApiResponse.from(LIQUOR_DELETED));
    }
}
