package service;

import java.util.List;

import model.dao.StudyDao;
import model.dao.StudyDaoImpl;
import model.dto.StudyDto;

public class StudyService {

    private static StudyService instance = new StudyService();
    private StudyDao studyDao = StudyDaoImpl.getInstance();

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
     * 특정 스터디에 속한 모든 멤버의 ID를 조회하는 서비스 메서드
     *
     * @param studyId 스터디 ID
     * @return 멤버 ID 리스트
     */
    public List<Integer> getUsersByStudyId(int studyId) {
        return studyDao.getUsersByStudyId(studyId);
    }
}
