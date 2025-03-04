package com.example.miniboard.service;

import com.example.miniboard.dao.BoardDao;
import com.example.miniboard.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;
    public void addBoard(int userId,String title, String content) {
        boardDao.addBoard(userId,title, content);
    }

    public List<Board> getBoards(int page) {
        return boardDao.getBoards(page);
    }

    public Board getBoard(int boardId) {
        boardDao.updateViewCnt(boardId);
        return getOnlyBoard(boardId);
    }

    public Board getOnlyBoard(int boardId) {
        return boardDao.getBoard(boardId);
    }

    public void updateBoard(int boardId, String title, String content) {
        boardDao.updateBoard(boardId, title, content);
    }

    public void delete(int boardId) {
        boardDao.delete(boardId);
    }

    public int getTotalCount() {
        return boardDao.getTotalCount(
        );
    }
}
