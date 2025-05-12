package example.jdbc.repository;

import example.jdbc.connection.DBConnectionUtil;
import example.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

// jdbc - DriverManager 사용
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        con = getConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error, e");
            throw e;
        } finally {
            // 리소스 정리 - 역순으로 정리해야한다. rs -> pstmt -> conn
            // pstmt close 중 exception이 일어나면 그 뒤의 con.close()가 실행이 되지 않게 된다.
//            pstmt.close();
//            con.close();
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
