package com.example.miniboard.service;

import com.example.miniboard.domain.Board;
import com.example.miniboard.domain.User;
import com.example.miniboard.repository.BoardRepository;
import com.example.miniboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addBoard(int userId, String title, String content) {
        Board board = new Board();
        User user = userRepository.findById(userId).orElseThrow();
        board.setUser(user);
        board.setTitle(title);
        board.setContent(content);
        board.setRegdate(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public long getTotalCount() {
        return boardRepository.getBoardCount();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findByOrderByRegdateDesc(pageable).getContent();
    }

    @Transactional
    public Board getBoard(int boardId) {
        return getBoard(boardId, true);
    }

    @Transactional
    public Board getBoard(int boardId, boolean updateViewCnt) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (updateViewCnt) {
            board.setViewCnt(board.getViewCnt() + 1);
        }
        return board;
    }

    @Transactional
    public void deleteBoard(int userId, int boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (board.getUser().getUserId() == userId) {
            boardRepository.delete(board);
        }
    }

    @Transactional
    public void updateBoard(int boardId, String title, String content) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.setTitle(title);
        board.setContent(content);
    }

    @Transactional
    public void deleteBoard(int boardId) {
        boardRepository.deleteById(boardId);
    }
}
