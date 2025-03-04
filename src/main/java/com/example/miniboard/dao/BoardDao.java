package com.example.miniboard.dao;

import com.example.miniboard.dto.Board;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BoardDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    public void addBoard(int userId, String title, String content) {
        String sql = "insert into board (title, content, user_id, regdate ) values (:title,:content,:userId,:regdate)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("content", content)
                .addValue("userId", userId)
                .addValue("regdate", LocalDateTime.now());

        jdbcTemplate.update(sql, params);
    }

    public List<Board> getBoards(int page) {
        int start = (page-1) * 10;
        String sql = "select b.board_id, b.title, b.content, b.regdate, b.view_cnt, b.user_id , u.name from board b,user u where u.user_id=b.user_id order by board_id desc limit :start,10";
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        List<Board> list = jdbcTemplate.query(sql, Map.of("start",start), rowMapper);
        return list;
    }

    public Board getBoard(int boardId) {
        String sql = "select b.board_id, b.title, b.content, b.regdate, b.view_cnt, b.user_id , u.name from board b,user u where  u.user_id=b.user_id and b.board_id=:boardId";
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        Board board = jdbcTemplate.queryForObject(sql, Map.of("boardId", boardId), rowMapper);
        return board;
    }

    public void updateViewCnt(int boardId) {
        String sql = "update board set view_cnt=view_cnt+1 where board_id=:boardId";
        jdbcTemplate.update(sql, Map.of("boardId", boardId));
    }

    public void updateBoard(int boardId, String title, String content) {
        String sql = "update board set title=:title , content=:content where board_id=:boardId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("content", content)
                .addValue("boardId", boardId);
        jdbcTemplate.update(sql, params);
    }

    public void delete(int boardId) {
        String sql = "delete from board where board_id=:boardId";
        jdbcTemplate.update(sql, Map.of("boardId", boardId));
    }

    public int getTotalCount() {
        String sql = "select count(*) from board";
        int count = jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
        return  count;
    }
}
