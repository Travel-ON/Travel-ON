package com.travel.travel_on.controller;

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
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService usvc;

    @Autowired
    private AlarmService asvc;

    @ApiOperation(value = "회원가입: 사용자 정보를 삽입한다")
    @PostMapping("/regist")
    @Transactional
    public ResponseEntity<?> regist(@RequestBody UserDto userDto) {
        try {
            boolean result = usvc.insert(userDto);
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
            UserDto userDto = usvc.select(param.get("id"));
            if (userDto != null && userDto.getPassword().equals(param.get("password"))) {
                Map<String, Object> result=new HashMap<>();
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
            UserDto userDto = usvc.select(id);
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
            UserDto userDto = usvc.selectByNickname(nickname);
            if (userDto != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원정보 조회: 사용자 정보를 조회한다", response = UserDto.class)
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        try {
            UserDto userDto = usvc.select(id);
            if(userDto==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원정보 수정: 사용자 정보를 수정한다")
    @PutMapping("/modify")
    @Transactional
    public ResponseEntity<?> modify(@RequestBody UserDto modifyUser) {
        try {
            UserDto userDto = usvc.select(modifyUser.getId());
            if(userDto==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if(modifyUser.getNickname()!=null) userDto.setNickname(modifyUser.getNickname());
            if(modifyUser.getPassword()!=null) userDto.setPassword(modifyUser.getPassword());
            if(modifyUser.getEmail()!=null) userDto.setEmail(modifyUser.getEmail());
            if(modifyUser.getAddress()!=null) userDto.setAddress(modifyUser.getAddress());

            boolean result = usvc.update(userDto);
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
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            boolean result = usvc.delete(id);
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
    public ResponseEntity<?> modifyTitle(@RequestBody Map<String, String> param) {
        try {
            UserDto userDto = usvc.select(param.get("id"));
            if(userDto==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            userDto.setUserTitle(param.get("title"));
            boolean result = usvc.update(userDto);
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
    public ResponseEntity<?> selectTitle(@RequestBody Map<String, String> param) {
        try {
            UserDto userDto = usvc.select(param.get("id"));
            if(userDto==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<UserAchievement> list = usvc.selectUserAchievement(userDto.toEntity(), param.get("sidoName"));
            List<UserAchievementDto> result = list.stream()
                    .map(r -> new UserAchievementDto(r))
                    .collect(Collectors.toList());
            log.info("UserAchievementList : {}", result.toString());
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
            UserDto userDto = usvc.select(param.get("id"));
            if(userDto!=null&&userDto.getEmail().equals(param.get("email"))){
                // 인증키 6자리 랜덤으로 생성 후 초기화
                String authKey = Integer.toString( ThreadLocalRandom.current().nextInt(100000, 1000000) );
                userDto.setPassword(authKey);
                boolean result = usvc.update(userDto);
                if(result) {
                    usvc.sendMail(userDto.getEmail(),
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
    @GetMapping("/trophy/{id}")
    public ResponseEntity<?> selectTrophy(@PathVariable String id) {
        try {
            UserDto userDto = usvc.select(id);
            if(userDto==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<Visitation> list = usvc.selectVisitation(userDto.toEntity());
            List<VisitationDto> result = list.stream()
                    .map(r -> new VisitationDto(r))
                    .collect(Collectors.toList());
            log.info("VisitationList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "여행횟수 업데이트: 사용자의 위치인증한 지역의 여행횟수를 1 증가시킨다")
    @PostMapping("/trophy")
    @Transactional
    public ResponseEntity<?> updateTrophy(@RequestBody Map<String, String> param) {
        try {
            UserDto userDto = usvc.select(param.get("id"));
            String sidoName = param.get("sidoName");
            if(userDto==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            User user = userDto.toEntity();
            // 여행횟수 업데이트
            int count = usvc.updateVisitation(userDto.toEntity(), sidoName);
            // 업적 기준 확인
            String title = usvc.selectAchievement(count);
            // 칭호 획득
            if (title != null) {
                // 사용자 테이블에 칭호 넣기, 알림 업데이트
                UserAchievement userAchievement = UserAchievement.builder()
                        .user(user)
                        .sidoName(sidoName)
                        .title(title)
                        .build();
                usvc.insertUserAchievement(userAchievement);
                // 알림 내용 추가 (알린 내용 수정)
                asvc.insert(user,"칭호획득: ["+sidoName+" "+title+"]");
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
