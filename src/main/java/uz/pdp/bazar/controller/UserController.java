package uz.pdp.bazar.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.FireBaseTokenRegisterDto;
import uz.pdp.bazar.model.request.UserDto;
import uz.pdp.bazar.model.request.UserRegisterDto;
import uz.pdp.bazar.model.request.UserVerifyDto;
import uz.pdp.bazar.service.UserService;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        return userService.create(userRegisterDto);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Valid UserDto userLoginRequestDto) {
        return userService.login(userLoginRequestDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getUserById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Valid UserRegisterDto userUpdateDto) {
        return userService.update(userUpdateDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

    @PostMapping("/verify")
    public ApiResponse verify(@RequestBody UserVerifyDto userVerifyDto) {
        return userService.verify(userVerifyDto);
    }

    @PostMapping("/forgetPassword")
    public ApiResponse forgetPassword(@RequestParam String number) {
        return userService.forgetPassword(number);
    }

    @PutMapping("/block/{id}")
    public ApiResponse blockUserById(@PathVariable Integer id) {
        return userService.addBlockUserByID(id);
    }

    @PutMapping("/openBlock/{id}")
    public ApiResponse openBlockUserById(@PathVariable Integer id) {
        return userService.openToBlockUserByID(id);
    }

    @PostMapping("/setFireBaseToken")
    public ApiResponse setFireBaseToken(@RequestBody FireBaseTokenRegisterDto fireBaseTokenRegisterDto) {
        return userService.saveFireBaseToken(fireBaseTokenRegisterDto);
    }

    @PostMapping("/changePassword")
    public ApiResponse changePassword(@RequestParam String number, @RequestParam String password) {
        return userService.changePassword(number, password);
    }

    @GetMapping("/getUserList")
    public ApiResponse getUserList(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                   @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return userService.getUserList(page, size);
    }

    @GetMapping("/logout")
    public ApiResponse deleteUserFromContext() {
        return userService.removeUserFromContext();
    }

    @GetMapping("/reSendSms/{phone}")
    public ApiResponse reSendSms(@PathVariable String phone) {
        return userService.reSendSms(phone);
    }

}
