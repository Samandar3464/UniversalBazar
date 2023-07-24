package uz.pdp.bazar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.bazar.entity.Market;
import uz.pdp.bazar.entity.User;
import uz.pdp.bazar.exception.RecordNotFoundException;
import uz.pdp.bazar.exception.UserNotFoundException;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.MarketDto;
import uz.pdp.bazar.model.response.BranchResponseListForAdmin;
import uz.pdp.bazar.repository.MarketRepository;
import uz.pdp.bazar.repository.UserRepository;

import java.util.Optional;

import static uz.pdp.bazar.enums.Constants.*;


@RequiredArgsConstructor
@Service
public class MarketService implements BaseService<MarketDto, Integer> {

    private final MarketRepository marketRepository;
    private final UserRepository userRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(MarketDto branch) {
        Optional<Market> byName = marketRepository.findByName(branch.getName());
        if (byName.isPresent()) {
            throw new RecordNotFoundException(MARKET_NAME_ALREADY_EXIST);
        }
        Optional<Market> byBusinessIdAndName = marketRepository.findByUserIdAndName(branch.getUserId(),branch.getName());
        if (byBusinessIdAndName.isPresent()) {
            throw new RecordNotFoundException(THIS_USER_ALREADY_HAVE_MARKET);
        }
        User user = userRepository.findById(branch.getUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Market marketNew = Market.from(branch, user);
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
        Market market = marketRepository.findById(dto.getId()).orElseThrow(()-> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setName(market.getName());
        market.setActive(dto.isActive());
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
        Optional<Market> allByBusinessId = marketRepository.findAllByUserIdAndDeleteFalse(integer);
        if (allByBusinessId.isPresent()) {
            throw new RecordNotFoundException(MARKET_NOT_FOUND);
        }
        return new ApiResponse(allByBusinessId, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Market> all = marketRepository.findAllByDeleteFalse(pageable);
        return new ApiResponse(new BranchResponseListForAdmin(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }
}
