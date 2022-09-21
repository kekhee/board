package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.service.BoardService;


@Controller
public class RestBoardController {
	//로그4j설정 

	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

	// 리스트
	@RequestMapping(value="/board", method = RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
	
        // ADD START
        //log.debug("openBoardList");
        // ADD END
        
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);

		return mv;

	}

	// 글쓰기화면
	@RequestMapping( value="/board/write", method = RequestMethod.GET)
	public String openBoardWrite() {
		return "/board/restBoardWrite";
	}

	// 글쓴후 동작
	@RequestMapping(value="/board/write", method = RequestMethod.POST)
	public String insertBoard(BoardDto board, MultipartHttpServletRequest mr) throws Exception {

		boardService.insertBoard(board, mr);
		return "redirect:/board";
	}

	// 글상세화면
	@RequestMapping(value="/board/{boardIdx}", method = RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");

		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);

		return mv;
	}

	// 글 수정 
	@RequestMapping(value="/board/{boardIdx}", method = RequestMethod.PUT)
	public String updateBoard(BoardDto board) {

		boardService.updateBoard(board);

		return "redirect:/board";
	}

	// 글 삭제
	@RequestMapping(value="/board/{boardIdx}", method = RequestMethod.DELETE)
	public String delBoard(int boardIdx) throws Exception {
//@PathVariable("boardIdx")
		boardService.delBoard(boardIdx);

		return "redirect:/board";
	}

	//파일
	@RequestMapping(value="/board/file", method = RequestMethod.GET)
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
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
}
