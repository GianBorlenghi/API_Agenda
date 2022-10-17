package com.AgendaBackEnd.app.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AgendaBackEnd.app.Model.Rol;

@Repository
public interface IRolRepository extends CrudRepository<Rol, Long> {

	@Query(value = "select GROUP_CONCAT(role_name) as roles from rol as r inner join user_role as ur on r.id_rol = ur.id_rol where id_user=:id_user",nativeQuery = true)
	public String getRolById(@Param("id_user") Long id);
	
	@Query(value = "select * from rol where role_name =:rol_name",nativeQuery=true)
	public Rol getIdRolByRolName(@Param("rol_name") String rol_name);

}
