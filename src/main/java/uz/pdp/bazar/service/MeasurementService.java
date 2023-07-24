package uz.pdp.bazar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.bazar.entity.Measurement;
import uz.pdp.bazar.exception.RecordAlreadyExistException;
import uz.pdp.bazar.exception.RecordNotFoundException;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.MeasurementDto;
import uz.pdp.bazar.repository.MeasurementRepository;

import java.util.List;

import static uz.pdp.bazar.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class MeasurementService implements BaseService<MeasurementDto, Integer> {

    private final MeasurementRepository measurementRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(MeasurementDto dto) {
        if (measurementRepository.existsByNameAndActiveTrue(dto.getName())) {
            throw new RecordAlreadyExistException(MEASUREMENT_ALREADY_EXIST);
        }
        Measurement measurement = Measurement.builder()
                .name(dto.getName())
                .active(true)
                .build();
        measurementRepository.save(measurement);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Measurement measurement = measurementRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        return new ApiResponse(measurement, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(MeasurementDto dto) {
        Measurement measurement = measurementRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        measurement.setName(dto.getName());
        measurementRepository.save(measurement);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Measurement measurement = measurementRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        measurement.setActive(false);
        measurementRepository.save(measurement);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll() {
        List<Measurement> measurements = measurementRepository.findAllByActiveTrue();
        return new ApiResponse(measurements, true);
    }

}
