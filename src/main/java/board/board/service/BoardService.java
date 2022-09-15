package board.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;

public interface BoardService {

	//리스트
	List<BoardDto> selectBoardList();

	//글쓰기, 파일추가,예외처리
	void insertBoard(BoardDto board, MultipartHttpServletRequest mr) throws Exception;
	
	//글상세
	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	//수정
	void updateBoard(BoardDto board);

	//삭제
	void delBoard(int boardIdx);

	//파일정보 
	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
	//파일삭제 
	void deleteBoardFile(int idx, int boardIdx) throws Exception;
	//게시글파일 업로드 
	void updateBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
}
