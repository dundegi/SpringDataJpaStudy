package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy(); //By 뒤에 아무것도 붙이지 않으면 전체 조회

    @Query(name = "Member.findByUsername") //없어도 된다. 상속받은 클래스랑 메소드이름을 스프링부트에서 찾아준다.  //우선순위 = 네임드 -> 메소드 순, 실무에서는 잘 사용 안 한다.
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();//찾고자하는 타입 넣어준다.

    //new오퍼레이션, dto를 불러올 때 사용
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUserName(String name); //컬렉션
    Member findMemberByUserName(String username); //단건
    Optional<Member> findOptionalByUsername(String name) //단건 Optional

}
