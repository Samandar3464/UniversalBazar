package uz.pdp.bazar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.MarketDto;
import uz.pdp.bazar.service.MarketService;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/market")
public class MarketController {

    private final MarketService marketService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody @Valid MarketDto branch) {
        return marketService.create(branch);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return marketService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Validated MarketDto branch) {
        return marketService.update(branch);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return marketService.delete(id);
    }

    @GetMapping("/getByBusinessId/{id}")
    public ApiResponse getByBusinessId(@PathVariable Integer id) {
        return marketService.getByUserId(id);
    }


    @GetMapping("/getAll")
    public ApiResponse getAllMarkets(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return marketService.getAll(page, size);
    }

    @GetMapping("/getAllToDto")
    public ApiResponse getAllToDto(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return marketService.getAllToDto(page, size);
    }

    @GetMapping("/activate")
    public ApiResponse activate(@RequestParam(name = "id") Integer id, @RequestParam(name = "day") LocalDate day) {
        return marketService.activate(id, day);
    }

    @GetMapping("/deactivate/{id}")
    public ApiResponse deactivate(@PathVariable Integer id) {
        return marketService.deActive(id);
    }


}
