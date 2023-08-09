package uz.pdp.bazar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.bazar.entity.Market;
import uz.pdp.bazar.exception.RecordNotFoundException;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.MarketDto;
import uz.pdp.bazar.model.response.MarketResponseDto;
import uz.pdp.bazar.model.response.PageResponseDto;
import uz.pdp.bazar.repository.MarketRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.pdp.bazar.enums.Constants.*;


@RequiredArgsConstructor
@Service
public class MarketService implements BaseService<MarketDto, Integer> {

    private final MarketRepository marketRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(MarketDto dto) {
        Optional<Market> byName = marketRepository.findByName(dto.getName());
        if (byName.isPresent()) {
            throw new RecordNotFoundException(MARKET_NAME_ALREADY_EXIST);
        }
        Market marketNew = Market.from(dto);
        marketRepository.save(marketNew);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        return new ApiResponse(market, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(MarketDto dto) {
        Market market = marketRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setName(dto.getName());
        market.setLongitude(dto.getLongitude());
        market.setLatitude(dto.getLatitude());
        market.setAddress(dto.getAddress());
        marketRepository.save(market);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setDelete(true);
        market.setActive(false);
        marketRepository.save(market);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByUserId(Integer integer) {
        Market market = marketRepository.findByIdAndDeleteFalse(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        return new ApiResponse(market, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Market> all = marketRepository.findAllByDeleteFalse(pageable);
        return new ApiResponse(new PageResponseDto(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllToDto() {
        List<Market> all = marketRepository.findAllByDeleteFalse();
        List<MarketResponseDto> responseDtoList = new ArrayList<>();
        all.forEach(market -> responseDtoList.add(MarketResponseDto.from(market)));
        return new ApiResponse(responseDtoList, true);
    }

    public ApiResponse deActive(Integer integer) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setActive(false);
        marketRepository.save(market);
        return new ApiResponse(market, true);
    }

    public ApiResponse activate(Integer integer, LocalDate newActiveDay) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setActive(true);
        market.setActiveDay(newActiveDay);
        marketRepository.save(market);
        return new ApiResponse(market, true);
    }
}
