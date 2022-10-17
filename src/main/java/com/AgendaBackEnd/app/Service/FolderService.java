package com.AgendaBackEnd.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AgendaBackEnd.app.Model.Folder;
import com.AgendaBackEnd.app.Model.Task;
import com.AgendaBackEnd.app.Repository.IFolderRepository;

@Service
public class FolderService implements IFolderService{

	@Autowired
	private IFolderRepository folderRepository;

	@Override
	@Transactional
	public void save(Folder folder) {

		folderRepository.save(folder);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Folder> findAll() {

		return (List<Folder>) folderRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Folder findById(Long id) {
	
		return folderRepository.findById(id).orElseThrow();
		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {

		folderRepository.deleteById(id);
		
	}
	

}
