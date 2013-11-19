package dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013��8��29��       
 *        
 * 
 *  Purpose: ����dao������ΪʲôҪʹ�÷��ͣ���Ϊԭ����ҪΪÿһ��ʵ�幹��һ��dao
 *           ���ʹ���˷��ͣ���ֻ�蹹��һ��dao���Ϳ���ʵ�ֻ�������ɾ�Ĳ飬�����ظ�����
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
 