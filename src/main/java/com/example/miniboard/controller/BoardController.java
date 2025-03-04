package com.example.miniboard.controller;

import com.example.miniboard.dto.Board;
import com.example.miniboard.dto.LoginInfo;
import com.example.miniboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String list(@RequestParam(name = "page",defaultValue = "1")int page, Model model, HttpSession httpSession) {
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if (loginInfo != null) {
            model.addAttribute("loginInfo", loginInfo);
        }
        int count = boardService.getTotalCount();
        int pageCount = count / 10;
        if (count % 10>0) {
            pageCount++;
        }
        List<Board> list = boardService.getBoards(page);
        model.addAttribute("list", list);
        model.addAttribute("pageCount", pageCount);

        return "list";
    }

    @GetMapping("/writeForm")
    public String writeForm(HttpSession httpSession, Model model) {
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if (loginInfo == null) {
            return "redirect:/loginform";
        }
        model.addAttribute("loginInfo", loginInfo);
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(HttpSession httpSession, @RequestParam String title, @RequestParam String content) {
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        boardService.addBoard(loginInfo.getUserId(), title, content);
        return "redirect:/";
    }

    @GetMapping("/board")
    public String board(@RequestParam int boardId, Model model) {
        Board board = boardService.getBoard(boardId);
        model.addAttribute("board", board);
        return "board";
    }

    @GetMapping("/updateform")
    public String updateForm(@RequestParam int boardId, Model model, HttpSession httpSession) {
        Board board = boardService.getOnlyBoard(boardId);
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if (loginInfo == null) {
            return "redirect:/loginform";
        }
        model.addAttribute("loginInfo", loginInfo);
        model.addAttribute("board", board);
        return "updateForm";
    }

    @PostMapping("/update")
    public String update(@RequestParam int boardId, @RequestParam String title, @RequestParam String content, HttpSession httpSession) {
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if (loginInfo == null) {
            return "redirect:/loginform";
        }
        boardService.updateBoard(boardId, title, content);
        return "redirect:/board?boardId=" + boardId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int boardId, HttpSession httpSession) {
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if (loginInfo == null) {
            return "redirect:/loginform";
        }
        boardService.delete(boardId);
        return "redirect:/";
    }
}
