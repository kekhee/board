package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.service.BoardService;


@Controller
public class BoardController {
	//로그4j설정 

	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

	// 리스트
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
	
        // ADD START
        //log.debug("openBoardList");
        // ADD END
        
		ModelAndView mv = new ModelAndView("/board/boardList");
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);

		return mv;

	}

	// 글쓰기화면
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() {
		return "/board/boardWrite";
	}

	// 글쓴후 동작
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest mr) throws Exception {

		boardService.insertBoard(board, mr);
		return "redirect:/board/openBoardList.do";
	}

	// 글상세화면
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");

		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);

		return mv;
	}

	// 글 수정 + 파일업로드 
	
	@RequestMapping("board/updateBoard.do")
	public String updateBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardService.updateBoard(board, multipartHttpServletRequest);
		return "redirect:/board/openBoardList.do";
	}


	// 글 삭제
	@RequestMapping("/board/delBoard.do")
	public String delBoard(int boardIdx) {

		boardService.delBoard(boardIdx);

		return "redirect:/board/openBoardList.do";
	}

//파일정보 다운로드	
	@RequestMapping("board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx,
			HttpServletResponse response) throws Exception {
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			//
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8")+"\";");
			//"attachment; fileName" 단어 사이의 띄워쓰기 확인!! 다운안됨 
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	//파일삭제 
	@RequestMapping("board/deleteBoardFile.do")
	public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
		boardService.deleteBoardFile(idx, boardIdx);
		
		return "redirect:/board/openBoardDetail.do?boardIdx="+boardIdx;
	}
}
