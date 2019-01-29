package com.aomc.coop.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.aomc.coop.model.User;
import com.aomc.coop.service.UserService;
import com.aomc.coop.util.SHA256;

@RestController
// response를 json으로 주겠다
// Spring 4 에서 Rest API 또는 Web API를 개발하기 위해 등장한 애노테이션.
// 이전 버전의 @Controller와 @ResponseBody를 포함합니다.
// 컨트롤러의 메소드에서는 JSON으로 변환될 객체를 반환합니다.
// jackson 라이브러리를 추가할 경우 객체를 JSON으로 변환하는 메시지 컨버터가 사용되도록 @EnableWebMvc에서 기본으로 설정되어 있습니다.
// jackson 라이브러리를 추가하지 않으면 JSON메시지로 변환할 수 없어 500오류가 발생합니다.
// 사용자가 임의의 메시지 컨버터(MessageConverter)를 사용하도록 하려면 WebMvcConfigurerAdapter의 configureMessageConverters메소드를 오버라이딩 하도록 합니다.
@RequestMapping("/api")
public class RegisterController {

    // ***** 초대 : 이메일 인증
    // ***** 초대 할 유저들 배열 받기 -> redis에 저장 -> (redis에 저장이 안 된 회원이면, 회원가입이 필요하다고 에러메시지와 함께)redis 토큰 줌 -> user_has_channel user_has_team 둘 다에 정보를 넣어주고, 둘 다의 certification을 1로 바꿔야 함(초대 했다는 뜻)
    @Autowired // 주입 대상이되는 bean을 컨테이너에 찾아 주입하는 어노테이션
    private UserService userService; // 접근 진행 순서 : Controller -> Service -> Mapper

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @CrossOrigin // 웹 서버 도메인간 액세스 제어 기능을 제공, 도메인간 데이터 전송을 가능하게 함
    public ResponseEntity<Void> insertUser(@RequestBody User user) throws NoSuchAlgorithmException
    {   // ResponseEntity : Response body와 함께 Http status를 전송하기 위해 사용
        // cf) @ResponseBody : 자바 객체를 Http response body로 변환
        // @RequestBody : Http request body를 java 객체로 변환
        // Annotation : 소스코드에 메타 데이터를 삽입하는 것, 비즈니스 로직과 별도로 시스템 설정과 관련된 사항들을 @에게 위임할 수 있음
        // 개발자는 비즈니스 로직 구현에 집중 가능
        // NoSuchAlgorithmException : This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment
        // <Generic>
        // 클래스 내부에서 사용할 데이터 타입을 외부에서 지정하는 기법
        // 컴파일 단계에서 오류가 검출된다.
        // 중복의 제거와 타입 안전성을 동시에 추구할 수 있게 되었다.

        // 1. (@RequestBody User user)가 Http request를 자바 객체 User로 변환시켜줌
        System.out.println("Register");

        // 2. random 문자열을 통해 salt 생성
        SecureRandom secRan = SecureRandom.getInstance("SHA1PRNG"); //random 문자 길이를 담은 객체
        int numLength = 16;
        String salt = "";
        for (int i = 0; i < numLength; ++i) {
            salt += secRan.nextInt(10);
        }
        System.out.println("salt : "+ salt);

        // 3. salt를 통해 hash된 password를 구하기
        String newPassword = salt + user.getPwd();
        String hashPassword = (SHA256.getInstance()).encodeSHA256(newPassword);

        // 4. salt와 hash된 password를 user 데이터에 저장, user role까지 설정
        user.setSalt(salt);
        user.setPwd(hashPassword);
        if(user.getUid().equals("admin")) {
            user.setRole("admin_role");
        }else {
            user.setRole("user_role");
        }
        // 5. 유저가 탈퇴하면 disable로 설정하기 때문에 필요
        user.setStatus("able");

        // 6. 정보 입력을 끝내고 user를 db에 INSERT
        if (userService.insertUser(user)) {
            System.out.println("Successfully Registered");
        // 7. Http Response를 줌
            return new ResponseEntity<Void>(HttpStatus.OK);
// 이 return 값은, ex) Chat.vue -> created().get.then.(response)의 response로 감 // 토큰도 ResponseEntity에 담아서 보낼것
        } else {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
// setter로 설정되지 않은 이외의 User 클래스 데이터들 -> (@RequestBody User user)를 통해 프론트에서 입력한 정보들이 자동으로 넘어와서 저장됨

    }
}
