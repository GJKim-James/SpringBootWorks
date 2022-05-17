package kopo.poly.persistance.redis;

import kopo.poly.dto.MelonDTO;

import java.util.List;

public interface IMelonCacheMapper {

    /**
     * 멜론 Top100 노래 리스트 저장하기
     *
     * @param pList 저장할 데이터
     * @param redisKey 저장할 키
     * @return 저장 결과
     */
    int insertSong(List<MelonDTO> pList, String redisKey) throws Exception;

    /**
     * RedisDB에 특정 키(key)가 존재하는지 안하는지 여부 확인하기
     *
     * @param redisKey 저장된 키(key) 이름
     * @return key 존재 여부(true, false)
     */
    boolean getExistKey(String redisKey) throws Exception;

    /**
     * RedisDB에서 멜론 Top100 노래 리스트 가져오기
     *
     * @param redisKey 가져올 RedisKey
     * @return 노래 리스트
     */
    List<MelonDTO> getSongList(String redisKey) throws Exception;
}
