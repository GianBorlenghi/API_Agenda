package com.AgendaBackEnd.app.Service;

import java.util.List;

import com.AgendaBackEnd.app.Model.Folder;

public interface IFolderService {
	
	public void save(Folder folder);
	
	public List<Folder> findAll();
	
	public Folder findById(Long id);
	
	public void deleteById(Long id);

}
