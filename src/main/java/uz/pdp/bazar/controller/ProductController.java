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
    public ApiResponse create(@ModelAttribute ProductDto productDto) {
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

    @GetMapping("/getAllByMarketIdForOwners")
    public ApiResponse getAllByMarketIdForOwners(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "id") Integer id) {
        return productService.getAllByMarketIdForOwners(page, size, id);
    }

    @GetMapping("/getAllByMarketId")
    public ApiResponse getByMarketIdIdForUsers(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "id") Integer id) {
        return productService.getAllByMarketIdForUsers(page, size, id);
    }

    @GetMapping("/getNeactive")
    public ApiResponse getNeActive(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllNeActivatedProducts(page, size);
    }

    @GetMapping("/activate/{id}")
    public ApiResponse activate(@PathVariable Integer id) {
        return productService.activateProduct(id);
    }


    @GetMapping("/getAll")
    public ApiResponse getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAll(page, size);
    }
}
