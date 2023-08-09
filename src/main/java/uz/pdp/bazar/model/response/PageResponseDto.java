package uz.pdp.bazar.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PageResponseDto<T> {

    private List<T> list;
    private long allSize;
    private int allPage;
    private int currentPage;
}
