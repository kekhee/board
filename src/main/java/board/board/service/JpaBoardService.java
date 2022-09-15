package board.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;

public interface JpaBoardService {

	//리스트
	List<BoardEntity> selectBoardList();	

	//저장
	void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	//상세
	BoardEntity selectBoardDetail(int boardIdx);

	//삭제
	void deleteBoard(int boardIdx);
	
	//파일
	BoardFileEntity selectBoardFileInformation(int boardIdx, int idx);
	

}
