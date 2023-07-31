package uz.pdp.bazar.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.bazar.entity.Market;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketResponseListForAdmin {

    private List<Market> marketResponseDtoList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
