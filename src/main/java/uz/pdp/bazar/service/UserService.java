package uz.pdp.bazar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.bazar.config.jwtConfig.JwtGenerate;
import uz.pdp.bazar.entity.User;
import uz.pdp.bazar.exception.RecordAlreadyExistException;
import uz.pdp.bazar.exception.RecordNotFoundException;
import uz.pdp.bazar.exception.UserNotFoundException;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.*;
import uz.pdp.bazar.model.response.NotificationMessageResponse;
import uz.pdp.bazar.model.response.TokenResponse;
import uz.pdp.bazar.model.response.UserResponseDto;
import uz.pdp.bazar.model.response.UserResponseListForAdmin;
import uz.pdp.bazar.repository.BranchRepository;
import uz.pdp.bazar.repository.RoleRepository;
import uz.pdp.bazar.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static uz.pdp.bazar.enums.Constants.*;


@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserRegisterDto, Integer> {

    private final SmsService smsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final FireBaseMessagingService fireBaseMessagingService;
    private final PasswordEncoder passwordEncoder;


    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse create(UserRegisterDto dto) {
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RecordAlreadyExistException(USER_ALREADY_EXIST);
        }
        User user = User.from(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(roleRepository.findByName(dto.getRomeName()).orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND)));
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse login(UserDto loginDto) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(), loginDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            User user = (User) authenticate.getPrincipal();
            return new ApiResponse(new TokenResponse(JwtGenerate.generateAccessToken(user), UserResponseDto.from(user)), true);
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return new ApiResponse(UserResponseDto.from(user), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(UserRegisterDto dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (!user.getPhoneNumber().equals(dto.getPhoneNumber())) {
            if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
                throw new RecordAlreadyExistException(USER_ALREADY_EXIST);
            }
        }
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setGender(dto.getGender());
        user.setBirthDate(dto.getBirthDate());
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer id) {
        User optional = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        optional.setDeleted(true);
        optional.setBlocked(false);
        userRepository.save(optional);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse verify(UserVerifyDto userVerifyRequestDto) {
        User user = userRepository.findByPhoneNumberAndVerificationCode(userVerifyRequestDto.getPhoneNumber(), userVerifyRequestDto.getVerificationCode())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setVerificationCode(0);
        user.setBlocked(true);
        userRepository.save(user);
        return new ApiResponse(USER_VERIFIED_SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse forgetPassword(String number) {
        System.out.println(number);
        User user = checkByNumber(number);
        user.setVerificationCode(verificationCodeGenerator());
        userRepository.save(user);
//        sendSms(user.getPhoneNumber(), verificationCodeGenerator());
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse addBlockUserByID(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setBlocked(false);
        userRepository.save(user);
//        sendNotificationByToken(user, BLOCKED);
        return new ApiResponse(BLOCKED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse openToBlockUserByID(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setBlocked(true);
        userRepository.save(user);
//        sendNotificationByToken(user, OPEN);
        return new ApiResponse(OPEN, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse saveFireBaseToken(FireBaseTokenRegisterDto fireBaseTokenRegisterDto) {
        User user = userRepository.findById(fireBaseTokenRegisterDto.getUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setFireBaseToken(fireBaseTokenRegisterDto.getFireBaseToken());
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse changePassword(String number, String password) {
        User user = checkByNumber(number);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true, new TokenResponse(JwtGenerate.generateAccessToken(user), UserResponseDto.from(user)));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getUserList(Integer page, Integer size) {
        Page<User> all = userRepository.findAll( PageRequest.of(page, size));
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        all.getContent().forEach(user -> userResponseDtoList.add(UserResponseDto.from(user)));
        return new ApiResponse(new UserResponseListForAdmin(userResponseDtoList, all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse removeUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        } else if (authentication != null && authentication.getName().equals(checkByNumber(((User) authentication.getPrincipal()).getPhoneNumber()).getPhoneNumber())) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse reSendSms(String number) {
        sendSms(number, verificationCodeGenerator());
        return new ApiResponse(SUCCESSFULLY, true);
    }


    private Integer verificationCodeGenerator() {
        Random random = new Random();
        return random.nextInt(1000, 9999);
    }

    private User checkByNumber(String number) {
        return userRepository.findByPhoneNumber(number).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    private void sendNotificationByToken(User user, String message) {
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(user.getFireBaseToken(), message, new HashMap<>());
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
    }

    private void sendSms(String phoneNumber, Integer verificationCode) {
        smsService.sendSms(SmsModel.builder()
                .mobilePhone(phoneNumber)
                .message("Cambridge school " + verificationCode + ".")
                .from(4546)
                .callBackUrl("http://0000.uz/test.php")
                .build());
    }

    private void updateUser(UserRegisterDto dto, User user) {
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setGender(dto.getGender());
        user.setBirthDate(dto.getBirthDate());
    }
}


