package service;

import dao.BasicDao;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013��8��29��       
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class basicService<T> {
	private BasicDao<T> basicDao;

	public BasicDao<T> getBasicDao() {
		return basicDao;
	}

	public void setBasicDao(BasicDao<T> basicDao) {
		this.basicDao = basicDao;
	}
	
	public int Add(T entity) {
		return basicDao.save(entity);
	}
}
 