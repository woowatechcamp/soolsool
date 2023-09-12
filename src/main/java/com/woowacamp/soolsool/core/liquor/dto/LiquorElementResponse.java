package com.woowacamp.soolsool.core.liquor.dto;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiquorElementResponse {

    private Long id;
    private String name;
    private String price;
    private String imageUrl;
    private Integer stock;

    public LiquorElementResponse(final Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getName();
        this.price = liquor.getPrice().toString();
        this.imageUrl = liquor.getImageUrl();
        this.stock = liquor.getTotalStock();
    }

    public static LiquorElementResponse from(final Liquor liquor) {
        return new LiquorElementResponse(
            liquor.getId(),
            liquor.getName(),
            liquor.getPrice().toString(),
            liquor.getImageUrl(),
            liquor.getTotalStock()
        );
    }

    public static List<LiquorElementResponse> from(final Page<Liquor> liquors) {
        return liquors.getContent().stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());
    }
}
