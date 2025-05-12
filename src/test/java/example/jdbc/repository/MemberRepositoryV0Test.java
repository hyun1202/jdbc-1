package example.jdbc.repository;

import example.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void save() throws SQLException {
        Member member = new Member("memberV100", 10000);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertEquals(findMember, member);

        //update
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertEquals(updatedMember.getMoney(), 20000);

        //delete
        repository.delete(member.getMemberId());
        assertThrows(NoSuchElementException.class, () -> repository.findById(member.getMemberId()));
    }

}