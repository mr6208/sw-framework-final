package com.swfinal.login;

import com.swfinal.exception.CustomException;
import com.swfinal.login.mapper.LoginMapper;
import com.swfinal.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final EncryptUtil encryptUtil;
    private final LoginMapper mapper;

    @Transactional(readOnly = true)
    public Map<String, Object> login(Map<String, Object> parmas) {
        Map<String, Object> resultMap = new HashMap<>();

        try {

            log.info("파라미터 정보 : {}", parmas);

            resultMap.put("REPL_CD", "0000");
            resultMap.put("REPL_MSG", "정상");
            resultMap.put("REPL_PAGE_MSG", "로그인 성공");

            String userPw = (String) parmas.get("userPw");

            Map<String, Object> memberInfo = mapper.selectMemberInfo(parmas);

            log.info("회원정보 : {}", memberInfo);

            String encPw = encryptUtil.encryptSha256(userPw);  // 평문 -> 암호화

            log.info("회원정보 : {}", memberInfo);
            // 아이디 검증
            if (memberInfo == null) {
                throw new CustomException("2001", "로그인 오류", "존재하지 않는 정보입니다.");
            }

            String pw = (String) memberInfo.get("USER_PW");

            // 비밀번호 검증
            if (!pw.equals(encPw)) {
                throw new CustomException("2000", "비밀번호 오류", "회원 정보가 잘못되었습니다.");
            }
            resultMap.put("MEMBER_INFO", memberInfo);

        } catch (CustomException customException) {
            resultMap.put("REPL_CD", customException.getREPL_CD());
            resultMap.put("REPL_MSG", customException.getREPL_MSG());
            resultMap.put("REPL_PAGE_MSG", customException.getREPL_PAGE_MSG());

            log.error("오류발생 : {}", customException.getREPL_PAGE_MSG());
        } catch (Exception e) {
            resultMap.put("REPL_CD", "9999");
            resultMap.put("REPL_MSG", "오류발생");
            resultMap.put("REPL_PAGE_MSG", "알수없는 에러가 발생했습니다.");

            log.error("오류발생 : {}", e.getMessage());
        }
        return resultMap;
    }
}
