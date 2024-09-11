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
- JPA: 객체지향 프로그래밍 언어와 관계형 데이터베이스를 중간에서 패러다임 일치를 시켜주기 위한 기술
  - 개발자는 객체지향적으로 프로그래밍을 하고, JPA가 이를 관계형 데이터베이스에 맞게 SQL을 대신 생성해서 실행
- Spring Data JPA: 자바 표준명세서인 JPA의 구현체(Hibernate, Eclipse Link 등)를 직접 다루지 않고 추상화시킨 기술
  ex. JPA ← Hibernate ← Spring Data JPA
  - 구현체 교체의 용이성 ... Spring Data JPA 내부에서 구현체 매핑을 지원
  - 저장소 교체의 용이성 가짐 ... 기본적인 CRUD 인터페이스가 같아 save(), findAll(), findOne() 등을 가지고 있음
- 도메인: 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제 영역
- @Entity: 실제 DB 테이블과 매칭될 클래스
  - **Entity 클래스에서는 절대 setter 메서드를 사용하지 않는다.**
- @Column: 테이블의 칼럼 나타냄, 선언하지 않아도 Entity 클래스의 필드는 모두 칼럼이 되는데, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
  ex. @Column(ColumnDefinition = "TEXT", nullable = false)
- @Builder: 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함, 어떤 필드에 어떤 값을 채울 지 명확하게 인지 가능
  ex. Example.builder()
             .a(a)
             .b(b)
             .build();
- JUnit5 사용을 위해 @After -> @AfterEach: 단위 테스트가 끝날 때마다 수행되는 메서드 지정
- save(): 테이블에 insert/update 쿼리를 실행한다
- @RequestBody: 요청 메시지의 바디 부분에 담긴 json 데이터를 자바 객체로 매핑할 때 사용 (cf. @ResponseBody)
   
  
  
