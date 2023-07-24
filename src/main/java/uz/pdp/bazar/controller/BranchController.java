package uz.pdp.bazar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.BranchDto;
import uz.pdp.bazar.service.BranchService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody @Valid BranchDto branch) {
        return branchService.create(branch);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return branchService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Validated BranchDto branch) {
        return branchService.update(branch);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return branchService.delete(id);
    }

    @GetMapping("/getByBusinessId/{id}")
    public ApiResponse getByBusinessId(@PathVariable Integer id) {
        return branchService.getByUserId(id);
    }


    @GetMapping("/getAll")
    public ApiResponse getAllBranches(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return branchService.getAll(page, size);
    }
}
