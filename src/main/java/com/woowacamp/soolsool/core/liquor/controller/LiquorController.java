package com.woowacamp.soolsool.core.liquor.controller;

import static com.woowacamp.soolsool.global.common.LiquorResultCode.LIQUOR_CREATED;

import com.woowacamp.soolsool.core.liquor.dto.SaveLiquorRequest;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.global.common.ApiResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            .body(ApiResponse.of(LIQUOR_CREATED, null));
    }
}
