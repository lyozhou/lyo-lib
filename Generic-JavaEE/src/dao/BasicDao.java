package dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年8月29日       
 *        
 * 
 *  Purpose: 基本dao操作，为什么要使用泛型，因为原本需要为每一个实体构建一个dao
 *           如果使用了泛型，则只需构建一个dao，就可以实现基本的增删改查，避免重复代码
 *
 *----------------------------------------------------------------*/

public class BasicDao<T> extends HibernateDaoSupport {
	public  Integer save(T entity){
		int result = 0 ; 
		try {
			result =  (Integer)getHibernateTemplate().save(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}finally{
			return result;
		}
	}
	
	public void update(T entity){
		getHibernateTemplate().update(entity);
	}
}
 