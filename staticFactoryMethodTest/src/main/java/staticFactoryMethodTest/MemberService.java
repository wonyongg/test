package staticFactoryMethodTest;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto.Response createMember(MemberDto.Post postDto) {

        Member member;
        if (postDto.country == null) {
//            member = new Member(postDto.name, postDto.age, LocalDateTime.now()); 생성자 메서드
            member = Member.southKoreaOf(postDto.name, postDto.age, LocalDateTime.now()); // 정적 팩토리 메서드
        } else {
//            member = new Member(postDto.name, postDto.age, postDto.country, LocalDateTime.now()); 생성자 메서드
            member = Member.anotherCountriesOf(postDto.name, postDto.age, postDto.country, LocalDateTime.now()); // 정적 팩토리 메서드
        }

        Member savedMember = memberRepository.save(member);


        MemberDto.Response response = MemberDto.Response.builder()
                .id(savedMember.getMemberId())
                .name(savedMember.getName())
                .age(savedMember.getAge())
                .country(savedMember.getCountry())
                .createdAt(savedMember.getCreatedAt()).build();

        return response;
    }
}
