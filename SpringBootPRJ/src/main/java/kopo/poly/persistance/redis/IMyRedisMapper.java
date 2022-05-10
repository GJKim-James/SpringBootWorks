package kopo.poly.persistance.redis;

import kopo.poly.dto.RedisDTO;

import java.util.List;
import java.util.Set;

public interface IMyRedisMapper {

    /**
     * RedisDB에 String 타입 저장하기
     *
     * @param redisKey Redis 저장 키
     * @param pDTO 저장할 정보
     * @return 저장 성공 여부
     */
    int saveRedisString(String redisKey, RedisDTO pDTO) throws Exception;

    /**
     * RedisDB에서 String 타입 가져오기
     *
     * @param redisKey 가져올 redisKey
     * @return 결과값
     */
    RedisDTO getRedisString(String redisKey) throws Exception;

    /**
     * RedisDB에 String 타입의 JSON 형태로 저장하기
     *
     * @param redisKey Redis 저장 키
     * @param pDTO 저장할 정보
     * @return 결과값
     */
    int saveRedisStringJSON(String redisKey, RedisDTO pDTO) throws Exception;

    /**
     * RedisDB에 String 타입의 JSON 형태로 저장된 데이터 가져오기
     *
     * @param redisKey 가져올 RedisKey
     * @return 결과값
     */
    RedisDTO getRedisStringJSON(String redisKey) throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장하기(동기화)
     *
     * @param redisKey Redis 저장 키
     * @param pList 저장할 정보들
     * @return 저장 성공 여부
     */
    int saveRedisList(String redisKey, List<RedisDTO> pList) throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장된 데이터 가져오기
     *
     * @param redisKey 가져올 RedisKey
     * @return 결과값
     */
    List<String> getRedisList(String redisKey) throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장하기(동기화)
     *
     * @param redisKey Redis 저장 키
     * @param pList 저장할 정보들
     * @return 저장 성공 여부
     */
    int saveRedisListJSON(String redisKey, List<RedisDTO> pList) throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장된 데이터 가져오기
     *
     * @param redisKey 가져올 RedisKey
     * @return 결과값
     */
    List<RedisDTO> getRedisListJSON(String redisKey) throws Exception;

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기(비동기화)
     *
     * @param redisKey Redis 저장 키
     * @param pList 저장할 정보들
     * @return 저장 성공 여부
     */
    int saveRedisListJSONLambda(String redisKey, List<RedisDTO> pList) throws Exception;

    /**
     * 키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장하기
     *
     * @param redisKey Redis 저장 키
     * @param pDTO 저장할 정보들
     * @return 저장 성공 여부
     */
    int saveRedisHash(String redisKey, RedisDTO pDTO) throws Exception;

    /**
     * 키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장된 데이터 가져오기
     *
     * @param redisKey 가져올 RedisKey
     * @return 결과값
     */
    RedisDTO getRedisHash(String redisKey) throws Exception;

    /**
     * 키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기
     *
     * @param redisKey Redis 저장 키
     * @param pSet 저장할 정보들
     * @return 저장 성공 여부
     */
    int saveRedisSetJSONLambda(String redisKey, Set<RedisDTO> pSet) throws Exception;

    /**
     * 키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장한 데이터 가져오기
     *
     * @param redisKey 가져올 RedisKey
     * @return 결과값
     */
    Set<RedisDTO> getRedisSetJSONLambda(String redisKey) throws Exception;

}
