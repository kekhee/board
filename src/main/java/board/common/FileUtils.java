package board.common;


import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.javassist.ClassPath;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardFileDto;
import board.board.entity.BoardFileEntity;

@Component
public class FileUtils {

	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest mr) throws Exception {
		if(ObjectUtils.isEmpty(mr)) {
			return null;
		}
		
		List<BoardFileDto> fileList = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		
		String path = "images/" + current.format(format);
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = mr.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = mr.getFiles(iterator.next());
			for(MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					//BoardFileEntity boardFile = new BoardFileEntity();
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
	
	public List<BoardFileEntity> parseFileInfoJpa(MultipartHttpServletRequest mr) throws Exception {
		if(ObjectUtils.isEmpty(mr)) {
			return null;
		}
		
		List<BoardFileEntity> fileList = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		
		String path = "images/" + current.format(format);
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = mr.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = mr.getFiles(iterator.next());
			for(MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					BoardFileEntity boardFile = new BoardFileEntity();
					//BoardFileDto boardFile = new BoardFileDto();
					//boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					boardFile.setCreatorId("admin");
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
}