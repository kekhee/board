package board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;
import board.common.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Service

public class BoardSerivceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;  
	
	// 리스트
	@Override
	public List<BoardDto> selectBoardList() {

		return boardMapper.selectBoardList();
	}

	// 작성,파일추가예외처리
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest mr) throws Exception{
		
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), mr);
		if(CollectionUtils.isEmpty(list) == false){
			boardMapper.insertBoardFileList(list);
		}
	}
	
	
	// 상세
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		// 조회수 증가
		boardMapper.updateHitCount(boardIdx);
		//int i = 10 / 0; //추가
	
		return board;
	}

	// 수정
	@Override
	public void updateBoard(BoardDto board) {
		boardMapper.updateBoard(board);

	}

	// 삭제
	@Override
	public void delBoard(int boardIdx) {
		boardMapper.delBoard(boardIdx);

	}

	//파일정보 
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
	
	//파일삭제 
	@Override
	public void deleteBoardFile(int idx, int boardIdx) throws Exception {
		boardMapper.deleteBoardFile(idx, boardIdx);
		
	}
	
	@Override
	public void updateBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.updateBoard(board);
		
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
	}
}
