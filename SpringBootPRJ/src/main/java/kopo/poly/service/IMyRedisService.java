package kopo.poly.service;

import kopo.poly.dto.RedisDTO;

import java.util.List;
import java.util.Set;

public interface IMyRedisService {

    /**
     * RedisDB에 String 타입 저장하기
     */
    int saveRedisString() throws Exception;

    /**
     * RedisDB에서 String 타입 가져오기
     */
    RedisDTO getRedisString() throws Exception;

    /**
     * RedisDB에 String 타입의 JSON 형태로 저장하기
     */
    int saveRedisStringJSON() throws Exception;

    /**
     * RedisDB에 String 타입의 JSON 형태로 저장된 데이터 가져오기
     */
    RedisDTO getRedisStringJSON() throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장하기(동기화)
     */
    int saveRedisList() throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장된 데이터 가져오기
     */
    List<String> getRedisList() throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장하기(동기화)
     */
    int saveRedisListJSON() throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장된 데이터 가져오기
     */
    List<RedisDTO> getRedisListJSON() throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기(비동기화)
     */
    int saveRedisListJSONLambda() throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장한 데이터 가져오기
     *
     * 람다식을 사용해서 저장한 Redis 키(key) 이름(myRedis_List_JSON_Lambda)이 달라서 함수 별도로 생성
     * Mapper 호출은 앞서 만든 'getRedisListJSON' 호출함
     */
    List<RedisDTO> getRedisListJSONLambda() throws Exception;

    /**
     * 키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장하기
     */
    int saveRedisHash() throws Exception;

    /**
     * 키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장된 데이터 가져오기
     */
    RedisDTO getRedisHash() throws Exception;

    /**
     * 키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기
     */
    int saveRedisSetJSONLambda() throws Exception;

    /**
     * 키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장한 데이터 가져오기
     */
    Set<RedisDTO> getRedisSetJSONLambda() throws Exception;

    /**
     * 키(key)는 ZSet 타입으로, 값(value)은 JSON 형태로 저장하기
     */
    int saveRedisZSetJSON() throws Exception;

    /**
     * 키(key)는 ZSet 타입으로, 값(value)은 JSON 형태로 저장된 데이터 가져오기
     */
    Set<RedisDTO> getRedisZSetJSON() throws Exception;

    /**
     * RedisDB에 JSON 구조로 저장된 데이터 삭제하기
     */
    boolean deleteJSONData() throws Exception;

    /**
     * RedisDB에 String 구조로 저장된 데이터 삭제하기
     */
    boolean deleteStringData() throws Exception;

}
