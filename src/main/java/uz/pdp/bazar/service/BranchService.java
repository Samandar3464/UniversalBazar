package uz.pdp.bazar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.bazar.entity.Branch;
import uz.pdp.bazar.entity.User;
import uz.pdp.bazar.exception.RecordNotFoundException;
import uz.pdp.bazar.exception.UserNotFoundException;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.BranchDto;
import uz.pdp.bazar.model.response.BranchResponseListForAdmin;
import uz.pdp.bazar.repository.BranchRepository;
import uz.pdp.bazar.repository.UserRepository;

import java.util.Optional;

import static uz.pdp.bazar.enums.Constants.*;


@RequiredArgsConstructor
@Service
public class BranchService implements BaseService<BranchDto, Integer> {

    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(BranchDto branch) {
        Optional<Branch> byName = branchRepository.findByName(branch.getName());
        if (byName.isPresent()) {
            throw new RecordNotFoundException(MARKET_NAME_ALREADY_EXIST);
        }
        Optional<Branch> byBusinessIdAndName = branchRepository.findByUserIdAndName(branch.getUserId(),branch.getName());
        if (byBusinessIdAndName.isPresent()) {
            throw new RecordNotFoundException(THIS_USER_ALREADY_HAVE_MARKET);
        }
        User user = userRepository.findById(branch.getUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Branch branchNew = Branch.from(branch, user);
        branchRepository.save(branchNew);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Branch branch = branchRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        return new ApiResponse(branch, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(BranchDto dto) {
        Branch branch = branchRepository.findById(dto.getId()).orElseThrow(()-> new RecordNotFoundException(MARKET_NOT_FOUND));
        branch.setName(branch.getName());
        branch.setActive(dto.isActive());
        branchRepository.save(branch);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Branch branch = branchRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        branch.setDelete(true);
        branch.setActive(false);
        branchRepository.save(branch);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByUserId(Integer integer) {
        Optional<Branch> allByBusinessId = branchRepository.findAllByUserIdAndDeleteFalse(integer);
        if (allByBusinessId.isPresent()) {
            throw new RecordNotFoundException(MARKET_NOT_FOUND);
        }
        return new ApiResponse(allByBusinessId, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Branch> all = branchRepository.findAllByDeleteFalse(pageable);
        return new ApiResponse(new BranchResponseListForAdmin(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }
}
