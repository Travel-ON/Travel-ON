package com.travel.travel_on.controller;

import com.travel.travel_on.auth.JwtTokenProvider;
import com.travel.travel_on.auth.JwtUserDetails;
import com.travel.travel_on.dto.UserAchievementDto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.dto.VisitationDto;
import com.travel.travel_on.entity.User;
import com.travel.travel_on.entity.UserAchievement;
import com.travel.travel_on.entity.Visitation;
import com.travel.travel_on.model.service.AlarmService;
import com.travel.travel_on.model.service.UserService;

import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation(value = "회원가입: 사용자 정보를 삽입한다")
    @PostMapping("/regist")
    @Transactional
    public ResponseEntity<?> regist(@RequestBody UserDto userDto) {
        try {
            boolean result = userService.insert(userDto);
            if(result) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "로그인: 사용자 정보를 조회한다")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> param) {
        try {
            UserDto userDto = userService.select(param.get("id"));
            if (userDto != null && passwordEncoder.matches(param.get("password"), userDto.getPassword())) {
                Map<String, Object> result=new HashMap<>();
                result.put("accessToken", JwtTokenProvider.getToken(param.get("id")));
                result.put("nickname", userDto.getNickname());
                result.put("userTitle", userDto.getUserTitle());
                result.put("adminFlag", userDto.isAdminFlag());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "아이디 중복검사: 회원 정보에 중복되는 {id}가 있는지 조회한다")
    @PostMapping("/idcheck")
    public ResponseEntity<?> idcheck(@RequestParam String id) {
        try {
            UserDto userDto = userService.select(id);
            if (userDto != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "닉네임 중복검사: 회원 정보에 중복되는 {nickname}이 있는지 조회한다")
    @PostMapping("/nickcheck")
    public ResponseEntity<?> nickcheck(@RequestParam String nickname) {
        try {
            UserDto userDto = userService.selectByNickname(nickname);
            if (userDto != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "비밀번호 체크: 사용자의 비밀번호가 올바른지 확인한다")
    @PostMapping("/check")
    public ResponseEntity<?> passwordCheck(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userPassword = userDetails.getPassword();
            if(passwordEncoder.matches(param.get("password"), userPassword)){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원정보 조회: 사용자 정보를 조회한다", response = UserDto.class)
    @GetMapping("/detail")
    public ResponseEntity<?> detail(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userDto.setPassword("");
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원정보 수정: 사용자 정보를 수정한다")
    @PutMapping("/modify")
    @Transactional
    public ResponseEntity<?> modify(@ApiIgnore Authentication authentication, @RequestBody UserDto modifyUser) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if(modifyUser.getNickname()!=null) {
                userDto.setNickname(modifyUser.getNickname());
            }
            if(modifyUser.getPassword()!=null) {
                userDto.setPassword(modifyUser.getPassword());
            }
            if(modifyUser.getEmail()!=null) {
                userDto.setEmail(modifyUser.getEmail());
            }
            if(modifyUser.getAddress()!=null) {
                userDto.setAddress(modifyUser.getAddress());
            }

            boolean result = userService.update(userDto);
            if(result) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원 탈퇴: 사용자 정보를 삭제한다")
    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<?> delete(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            boolean result = userService.delete(userId);
            if(result) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "칭호 변경: 사용자 칭호를 수정한다")
    @PutMapping("/title")
    @Transactional
    public ResponseEntity<?> modifyTitle(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userDto.setUserTitle(param.get("title"));
            boolean result = userService.update(userDto);
            if(result) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "칭호 조회: 사용자가 얻은 칭호리스트를 조회한다", response = List.class)
    @PostMapping("/title")
    public ResponseEntity<?> selectTitle(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<UserAchievement> list = userService.selectUserAchievement(userDto.toEntity(), param.get("sidoName"));
            List<UserAchievementDto> result = list.stream()
                    .map(r -> new UserAchievementDto(r))
                    .collect(Collectors.toList());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "비밀번호 찾기: 비밀번호를 초기화한 후 이메일로 전송한다")
    @PostMapping("/email")
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> param) {
        try {
            UserDto userDto = userService.select(param.get("id"));
            if(userDto!=null&&userDto.getEmail().equals(param.get("email"))){
//                String authKey = "A!"+Integer.toString( ThreadLocalRandom.current().nextInt(100000, 1000000) )+"CT@";
                String authKey = userService.getRandomString(10,true);
                userDto.setPassword(authKey);
                boolean result = userService.update(userDto);
                if(result) {
                    userService.sendMail(userDto.getEmail(),
                            "[Travel-ON] 비밀번호 초기화",
                            "안녕하세요 Travel-ON 입니다\n" +
                                    "인증번호는 " + authKey + " 입니다.\n" +
                                    "로그인 후 비밀번호를 변경해주세요.\n"+
                                    "Travel-ON을 이용해주셔서 감사합니다."
                    );
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }else{
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "여행횟수 조회: 사용자의 지역별 여행횟수를 조회한다", response = List.class)
    @GetMapping("/trophy")
    public ResponseEntity<?> selectTrophy(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Visitation> list = userService.selectVisitation(userDto.toEntity());
            List<VisitationDto> result = list.stream()
                    .map(r -> new VisitationDto(r))
                    .collect(Collectors.toList());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "여행횟수 업데이트: 사용자의 위치인증한 지역의 여행횟수를 1 증가시킨다")
    @PostMapping("/trophy")
    @Transactional
    public ResponseEntity<?> updateTrophy(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            String sidoName = param.get("sidoName");
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            User user = userDto.toEntity();
            int count = userService.updateVisitation(userDto.toEntity(), sidoName);
            String title = userService.selectAchievement(count);
            if (title != null) {
                UserAchievement userAchievement = UserAchievement.builder()
                        .user(user)
                        .sidoName(sidoName)
                        .title(title)
                        .build();
                userService.insertUserAchievement(userAchievement);
                alarmService.insert(user,"칭호획득: ["+sidoName+" "+title+"]");
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "신규알림 여부 조회: 신규알림이 생성됐는지 여부를 확인한다", response = Boolean.class)
    @GetMapping("/alarm")
    public ResponseEntity<?> alarmCheck(@ApiIgnore Authentication authentication) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Boolean>(userDto.isAlarmFlag(), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
