 ## 책 '스프링 부트와 AWS로 혼자 구현하는 웹 서비스'

 #### chaper1. intellij 세팅, 깃 연결
 - JetBrains의 ToolBox 이용
 #### chaper2. TDD 기반 테스트 코드 작성, 롬복 설치 및 의존성 등록
   - @RestController: 결과를 JSON으로 반환하는 컨트롤러, 즉 메서드마다 선언했던 @ResponseBody를 클래스 단위에 붙여 한 번에 사용할 수 있도록 한다  
   - JUnit5 사용을 위해 @RunWith(SpringRunner.class) -> @ExtendWith(SpringExtension.class): 스프링 부트 테스트와 JUnit을 연결한다 
   - @WebMvcTest: 스프링 테스트 애너테이션 중 Web(SpringMVC)에 집중할 수 있는 애너테이션, 컨트롤러에만 사용 가능
   - MockMvc: HTTP GET, POST 등에 대한 API 테스트 할 수 있다
     - mvc.perform(get("/..)): MockMvc를 통해 특정 주소로 HTTP GET 요청
     - .andExpect(status().isOK()): 헤더의 status 검증
     - .andExpect(content().string(..)): 응답 본문의 내용 검증
  - @RequiredArgsConstructor: 필드 중 final 키워드가 붙은 필드들을 포함한 생성자 생성
#### chapter3. Spring Data JPA 예제 코드, 테스트 코드 작성
