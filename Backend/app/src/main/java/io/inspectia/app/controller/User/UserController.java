package io.inspectia.app.controller.User;

import io.inspectia.app.model.Domain.DTO.Entities.UserResponse;
import io.inspectia.app.model.Mapper.UserMapper;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.service.Auth.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) throws ExecutionException, InterruptedException {

        try{
            Optional<User> userDB = userService.getByDocument(userId);

            if(userDB.isPresent()){
                UserResponse userResponse = UserMapper.fromUser(userDB.get());

                return ResponseEntity.ok(userResponse);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
}
