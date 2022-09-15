package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {

	//리스트
	List<BoardDto> selectBoardList();
	
	//작성
	void insertBoard(BoardDto board);
	

	//상세
	BoardDto selectBoardDetail(int boardIdx);

	//조회수 증가
	void updateHitCount(int boardIdx);

	//수정
	void updateBoard(BoardDto board);
	//삭제
	void delBoard(int boardIdx);

	//파일추가 
	void insertBoardFileList(List<BoardFileDto> list);
	
	//파일리스트 	
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception ;

	//파일정보
	BoardFileDto selectBoardFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	
	//파일삭제 
	void deleteBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
}
