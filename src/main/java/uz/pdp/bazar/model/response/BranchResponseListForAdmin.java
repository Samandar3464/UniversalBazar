package uz.pdp.bazar.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.bazar.entity.Branch;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseListForAdmin {

    private List<Branch> branchResponseDtoList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
