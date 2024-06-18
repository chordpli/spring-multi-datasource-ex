# Multi Database Test

## 개요
- 이 레파지토리는 Multi Database 구조를 테스트할 수 있습니다.

## 목적
- 동일한 컬럼을 가지고 있는 엔티티를 다중 DB 커넥션을 진행하여 Read를 테스트 하는 데 목적을 둡니다.

## 설계 고려사항
- Multi Database 구조를 유지하기 위해 각 데이터베이스에 대해 별도의 `Repository`와 `EntityManager`를 설정하고, `Type` 값에 따라 적절한 `Repository`를 선택하는 전략 패턴을 사용합니다.
- 만약 View Table을 사용할 수 있다면, Multi Database 구조를 유지하지 않고도 동일한 결과를 얻을 수 있습니다.
    - View Table에 대한 선택지도 고려 및 검토 부탁드립니다. (이게 더 편할수도..)

## 주요 기능
- Type 값에 따라 적절한 Repository를 선택하여 데이터베이스를 조회
- 비동기 처리 및 병렬 처리 기능을 통해 성능 최적화
- 다양한 데이터베이스에 대한 확장 가능한 설계

---

## Check
- DataSource를 한 곳으로 모았으나 실패
    - 한 곳으로 모았을 경우, Primary 어노테이션 등 기타 설정으로 인하여 DB Session이 Repository별로 분리되는 것이 아닌 하나의 Session으로 동작하는 문제가 발생
    - 아래처럼 basePackages를 통해 Repository를 분리하는 방법을 사용하였음
```java
@EnableJpaRepositories(
        basePackages = "com.pli.springmultidatasourceex.domain.user.repository.kor",
        entityManagerFactoryRef = "korEntityManagerFactory",
        transactionManagerRef = "korTransactionManager")
```

- entity package 여러개일 때
```java
   @Bean
   public LocalContainerEntityManagerFactoryBean korEntityManagerFactory(
           EntityManagerFactoryBuilder builder, @Qualifier("korDataSource") DataSource dataSource) {
       return builder
               .dataSource(dataSource)
               .packages("com.example.entity", "com.example.entity2", "com.example.entity3")
               .persistenceUnit("kor")
               .build();
   }
```
