package service;

import java.util.List;

import model.dao.StudyDao;
import model.dao.StudyDaoImpl;
import model.dao.UserDao;
import model.dao.UserDaoImpl;
import model.dto.StudyDto;
import model.dto.UserDto;

public class StudyService {

    private static StudyService instance = new StudyService();
    private StudyDao studyDao = StudyDaoImpl.getInstance();
    private UserDao userDao = UserDaoImpl.getInstance();

    private StudyService() {}

    public static StudyService getInstance() {
        return instance;
    }

    /**
     * 스터디 등록 서비스 메서드
     *
     * @param studyDto 등록할 스터디 정보
     * @return 등록된 StudyDto 객체
     */
    public StudyDto addStudy(StudyDto studyDto) {
        return studyDao.addStudy(studyDto);
    }

    // 스터디원 추가
    public boolean insertStudyMember(int studyId, int userId) {
        return studyDao.insertStudyMember(studyId, userId);
    }

    // 스터디 검색
    public StudyDto searchStudy(String name) {
        return studyDao.searchStudy(name);
    }
    
    /**
     * 스터디 상세 조회 서비스 메서드
     *
     * @param studyId 스터디 ID
     * @return StudyDto 객체
     */
    public StudyDto getStudyById(int studyId) {
        return studyDao.getStudyById(studyId);
    }

    /**
     * 특정 스터디에 속한 모든 멤버를 조회하는 서비스 메서드
     *
     * @param studyId 스터디 ID
     * @return 멤버 ID 리스트
     */
    public List<UserDto> getUsersByStudyId(int studyId) {
        return studyDao.getUsersByStudyId(studyId);
    }
    
 // 사용자가 가입한 스터디 목록을 조회하는 서비스 메서드
    public List<Integer> getStudiesByUserId(int userId) {
        return studyDao.getStudiesByUserId(userId);
    }
    
    /**
     * 스터디 코드로 스터디 조회 서비스 메서드
     *
     * @param code 스터디 코드
     * @return StudyDto 객체
     */
    public StudyDto getStudyByCode(String code) {
        return studyDao.getStudyByCode(code);
    }
    
    /**
     * 유저 ID로 유저 정보 조회 서비스 메서드
     *
     * @param userId 유저 ID
     * @return UserDto 객체
     */
    public UserDto getUserById(int userId) {
        return userDao.searchUserById(userId); // StudyDao에 구현된 메서드를 호출
    }
}
