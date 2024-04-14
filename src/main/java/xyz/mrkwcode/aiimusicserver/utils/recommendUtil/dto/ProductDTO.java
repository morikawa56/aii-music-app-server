package xyz.mrkwcode.aiimusicserver.utils.recommendUtil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer productId;

    private String productName;

    private String productPrice;

}
