package uz.pdp.bazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.ProductDto;
import uz.pdp.bazar.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @GetMapping("/getByBranchId")
    public ApiResponse getByBranchId(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "id") Integer id) {
        return productService.getByBranchId(page, size, id);
    }
}
