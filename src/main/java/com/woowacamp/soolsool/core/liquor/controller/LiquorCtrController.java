package com.woowacamp.soolsool.core.liquor.controller;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrResultCode;
import com.woowacamp.soolsool.core.liquor.dto.response.LiquorCtrDetailResponse;
import com.woowacamp.soolsool.core.liquor.service.LiquorCtrService;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/liquor-ctr")
@RequiredArgsConstructor
public class LiquorCtrController {

    private final LiquorCtrService liquorCtrService;

    @NoAuth
    @GetMapping
    public ResponseEntity<ApiResponse<LiquorCtrDetailResponse>> findLiquorCtr(
        @RequestParam final Long liquorId
    ) {
        return ResponseEntity.ok(
            ApiResponse.of(LiquorCtrResultCode.FIND_LQUOR_CTR_SUCCESS,
                new LiquorCtrDetailResponse(liquorCtrService.getLiquorCtrByLiquorId(liquorId)))
        );
    }
}
