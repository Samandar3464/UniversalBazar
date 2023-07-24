package uz.pdp.bazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.MeasurementDto;
import uz.pdp.bazar.service.MeasurementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody MeasurementDto measurementDto) {
        return measurementService.create(measurementDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return measurementService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody MeasurementDto measurementDto) {
        return measurementService.update(measurementDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return measurementService.delete(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll() {
        return measurementService.getAll();
    }

}
