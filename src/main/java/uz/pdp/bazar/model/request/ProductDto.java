package uz.pdp.bazar.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDto {


    private Integer id;

    private String name;

    private double quantity;

    private String description;

    private Integer measurementId;

    private Integer marketId;

    private Integer categoryId;

    private double price;

    private List<MultipartFile> photos;
}
