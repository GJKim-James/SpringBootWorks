package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

// 변수에 저장된 값이 기본값이 아닌 경우 제외
// jackson 라이브러리는 int와 같은 숫자 데이터 타입에 데이터가 저장되지 않아 발생하는 Null 오류를 방지하기 위해 값이 없으면 0으로 저장함
// Include.NON_NULL은 값이 NULL인 것만 방지하기 때문에 숫자 타입은 출력되는 문제가 있음
// 클래스에 int가 있으면 NON_DEFAULT, int가 없으면 NON_NULL
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
// @JsonInclude(JsonInclude.Include.NON_NULL)은 저장된 결과를 출력할 때 활용됨
// 만약 출력되는 결과에 DTO 존재하는 변수 중 collectionTime 변수를 사용하지 않는다면, 스프링 프레임워크는 null로 변환하여 보여줌
// DTO 변수 중 null은 제외하고 JSON으로 변환함
@Data // DTO에 적용 가능한 함수, 생성자 생성
// Getter, Setter, 생성자, 빌더, toString() 등 생성
// Getter, Setter 둘 다 사용한다면 @Data 사용
// 단, @Data는 ORM 기반의 JPA 사용한다면 절대 사용하지 말것!
public class MelonDTO {

    String collectTime; // 수집 시간
    String song; // 노래 제목
    String singer; // 가수
    int singerCnt; // 차트에 등록된 가수별 노래 수

}
