package com.swfinal.register;

import com.swfinal.exception.CustomException;
import com.swfinal.register.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    private final RegisterMapper mapper;

    @Transactional(readOnly = false)
    public Map<String, Object> register(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            resultMap.put("REPL_CD", "0000");
            resultMap.put("REPL_MSG", "정상");
            resultMap.put("REPL_PAGE_MSG", "회원가입 성공");

            String userId = (String) params.get("userId");
            String userPw = (String) params.get("userPw");
            String userName = (String) params.get("userName");
            String userEmail = (String) params.get("userEmail");

            if (userId == null || userId.isEmpty()) {
                throw new CustomException("1000", "회원가입 오류", "아이디를 입력하세요");
            }

            log.info("파라미터 {}", params);
            int duplCnt = mapper.selectMemberDuplicateCount(params);

            log.info("겹치는 회원 수 : {}", duplCnt);
            if (duplCnt > 0) {
                throw new CustomException("REPL_CD", "회원가입 도중 오류 발생", "중복된 아이디입니다.");
            }

            if (userPw == null || userPw.isEmpty()) {
                throw new CustomException("1002", "회원가입 오류", "비밀번호를 입력하세요");
            }

            if (userName == null || userName.isEmpty()) {
                throw new CustomException("1003", "회원가입 오류", "이름을 입력하세요");
            }

            if (userEmail == null || userEmail.isEmpty()) {
                throw new CustomException("1004", "회원가입 오류", "이메일을 입력하세요");
            }

            int tmp = mapper.insertMember(params);

            if (tmp < 0) {
                throw new CustomException("RE006", "회원가입 오류", "회원가입 도중 오류가 발생했습니다.");
            }
        } catch (CustomException customException) {

            resultMap.put("REPL_CD", customException.getREPL_CD());
            resultMap.put("REPL_MSG", customException.getREPL_MSG());
            resultMap.put("REPL_PAGE_MSG", customException.getREPL_PAGE_MSG());

            log.error("오류발생 : {}", customException.getREPL_PAGE_MSG());
        } catch (Exception e) {
            resultMap.put("REPL_CD", "9999");
            resultMap.put("REPL_MSG", "오류발생");
            resultMap.put("REPL_PAGE_MSG", "알수없는 에러가 발생했습니다.");
        }
        return resultMap;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getUser(String id) {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            Map<String, Object> mapperMap = new HashMap<>();

            resultMap.put("REPL_CD", "0000");
            resultMap.put("REPL_MSG", "정상");
            resultMap.put("REPL_PAGE_MSG", "회원조회 성공");

            mapperMap.put("userId", id);
            Map<String, Object> memberInfo = mapper.selectMemberInfo(mapperMap);

            if (memberInfo == null) {
                throw new CustomException("1005", "회원 조회 실패", "회원 정보를 찾지 못했습니다.");
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
        }
        return resultMap;
    }

    @Transactional(readOnly = false)
    public Map<String, Object> deleteMember(Map<String, Object> parmas) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            Map<String, Object> mapperMap = new HashMap<>();

            resultMap.put("REPL_CD", "0000");
            resultMap.put("REPL_MSG", "정상");
            resultMap.put("REPL_PAGE_MSG", "회원삭제 성공");

            Map<String, Object> memberInfo = mapper.selectMemberInfo(parmas);

            log.info("회원 정보 : {}", memberInfo);

            String userSeqString = (String) memberInfo.get("USER_SEQ");

            int userSeq = Integer.parseInt(userSeqString);

            log.info("회원 고유 PK : {}", userSeq);

            mapperMap.put("userSeq", userSeq);

            int tmp = mapper.deleteMember(mapperMap);

            if (tmp < 0) {
                throw new CustomException("1006", "회원삭제 오류", "회원삭제중 오류 발생");
            }
        } catch (CustomException customException) {

            resultMap.put("REPL_CD", customException.getREPL_CD());
            resultMap.put("REPL_MSG", customException.getREPL_MSG());
            resultMap.put("REPL_PAGE_MSG", customException.getREPL_PAGE_MSG());

            log.error("오류발생 : {}", customException.getREPL_PAGE_MSG());
        } catch (Exception e) {
            resultMap.put("REPL_CD", "9999");
            resultMap.put("REPL_MSG", "오류발생");
            resultMap.put("REPL_PAGE_MSG", "알수없는 에러가 발생했습니다.");

            log.error(e.getMessage());
        }
        return resultMap;
    }
}
