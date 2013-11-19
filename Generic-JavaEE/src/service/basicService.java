package service;

import dao.BasicDao;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê8ÔÂ29ÈÕ       
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
 