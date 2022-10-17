package com.AgendaBackEnd.app.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.AgendaBackEnd.app.Model.Folder;

@Repository
public interface IFolderRepository extends CrudRepository<Folder, Long>{

}
