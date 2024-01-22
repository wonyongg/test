package com.example.bulktest.repository;

import com.example.bulktest.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BatchInsertRepository {

    private final JdbcTemplate jdbcTemplate;

    public void memberSaveAll(List<Member> memberList, int batchSize) {
        String sql = "INSERT INTO member (name, member_grade, mobile, etc) VALUES (?, ?, ?, ?)";

        int totalSize = memberList.size();
        int fromIdx = 0;

        while (fromIdx < totalSize) {
            int toIdx = Math.min(fromIdx + batchSize, totalSize); // batchSize 만큼 끊고 맨 마지막에 남은게 있으면 totalSize로
            List<Member> batchMemberList = memberList.subList(fromIdx, toIdx);

            BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Member member = batchMemberList.get(i);
                    ps.setString(1, member.getName());
                    ps.setString(2, member.getMemberGrade());
                    ps.setString(3, member.getMobile());
                    ps.setString(4, member.getEtc());
                }

                @Override
                public int getBatchSize() {
                    return batchMemberList.size();
                }
            };

            // jdbcTemplate의 batchUpdate 메서드를 사용하여 일괄 삽입을 수행합니다.
            jdbcTemplate.batchUpdate(sql, setter);

            log.info("### fromIdx : " + fromIdx);
            fromIdx = toIdx;
        }
    }

}

//        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                Member member = members.get(i);
//                ps.setString(1, member.getName());
//                ps.setString(2, member.getMemberGrade());
//                ps.setString(3, member.getMobile());
//                ps.setString(4, member.getEtc());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return members.size();
//            }
//        };
//
//        // jdbcTemplate의 batchUpdate 메서드를 사용하여 일괄 삽입을 수행합니다.
//        jdbcTemplate.batchUpdate(sql, setter);
//    }
//}
