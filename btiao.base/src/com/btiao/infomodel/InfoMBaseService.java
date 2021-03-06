package com.btiao.infomodel;

import java.util.Collection;

import com.btiao.base.exp.BTiaoExp;
import com.btiao.base.model.BTiaoRoot;
import com.btiao.base.utils.BTiaoLog;

public abstract class InfoMBaseService {
	static public synchronized InfoMBaseService instance() {
		if (inst == null) {
			Neo4jMgr.instance().init();
			inst = new InfoMBaseServiceImplNeo4j();
			
			inst.begin();
			try {
				if (!inst.get(new BTiaoRoot())) {
					inst.add(new BTiaoRoot());
				}
				inst.success();
			} catch (BTiaoExp e) {
				inst.failed();
				BTiaoLog.logExp(BTiaoLog.get(), e, "failed to create root object in neo4j!");
				e.printStackTrace();
			} finally {
				inst.finish();
			}
		}
		return inst;
	}

	static private InfoMBaseService inst = null;
	
	/**
	 * if there the object of u does not exist in info model, then add it.<br>
	 * otherwise, it will throw a exception with error code<br> 
	 * ADD_DUP_REL_TO_INFO_MODEL.<br>
	 * @param u object infomation.
	 * @throws BTiaoExp
	 */
	public abstract void add(InfoMObject u) throws BTiaoExp;
	
	/**
	 * query the infomation of one object from info model.<br>
	 * @param u the object key attribute must be setted in this argument.<br>
	 *          other attributes may not be setted.<br>
	 * @return if the object doesn't exist, return false.<br>
	 *         if it get to be success , return true.<br>
	 *         otherwise, a exception will be throw.<br>
	 * @throws BTiaoExp 
	 */
	public abstract boolean get(InfoMObject u) throws BTiaoExp;
	
	public abstract void delAllInRelShip(InfoMObject u) throws BTiaoExp;
	
	public abstract Collection<?extends InfoMObject> getAll(Class<?extends InfoMObject> objClz) throws BTiaoExp;
	
	/**
	 * delete a object exists in info model.<br>
	 * if there are some constrain which forbid this action, <br>
	 * then a exception will be throwed.<br>
	 * @param u only the key attribute of u must be setted.
	 * @throws BTiaoExp
	 */
	public abstract void del(InfoMObject u) throws BTiaoExp;
	
	/**
	 * modify the information of specified object in info model fully as object 'u'.<br>
	 * if the object does not exist in info model, a exception with error <br>
	 * code OBJ_NOT_IN_INFO_MODEL will be throwed. <br>
	 * @param u 
	 * @throws BTiaoExp
	 */
	public abstract void mdf(InfoMObject u) throws BTiaoExp;
	
	/**
	 * make a uni-direction relation ship from object o1 to o2.<br>
	 * @param o1 only the key attribute must be setted.
	 * @param o2 only the key attribute must be setted.
	 * @param r
	 * @throws BTiaoExp
	 */
	public abstract void addRel(InfoMObject o1, InfoMObject o2, RelType r) throws BTiaoExp;

	/**
	 * check whether there is a relation ship between object o1 and o2.<br>
	 * @param o1 only the key attribute must be setted.
	 * @param o2 only the key attribute must be setted.
	 * @param r
	 * @return return true if there has the specified ship, otherwise return false
	 * @throws BTiaoExp
	 */
	public abstract boolean hasRel(InfoMObject o1, InfoMObject o2, RelType r) throws BTiaoExp;
	
	/**
	 * get first object which has a relationship with object 'o'.<br>
	 * @param o
	 * @param r
	 * @return if there is no specific relationship then return null;<br>
	 *         otherwise, convert the end node to specific object which has <br>
	 *         the same type with 'o'.<br>
	 * @throws BTiaoExp
	 */
	public abstract InfoMObject getFirstRelObj(InfoMObject o, RelType r, Class<?extends InfoMObject> relObjClz, boolean isOutRel) throws BTiaoExp;
	
	/**
	 * delete the relation ship between object o1 and o2.
	 * @param o1 only the key attribute must be setted.
	 * @param o2 only the key attribute must be setted.
	 * @param r
	 * @throws BTiaoExp
	 */
	public abstract void delRel(InfoMObject o1, InfoMObject o2, RelType r) throws BTiaoExp;

	/*
	 * for example,
	 * begin();
	 * try {
	 * 	...
	 * } catch (BTiaoExp e) {
	 * 	failed();
	 * } finally {
	 * 	finish();
	 * }
	 * 
	 * they alse support reenter usage, e.g.
	 * func() {
	 * 	begin();
	 * 	try {
	 * 		...
	 * 	} catch (BTiaoExp e) {
	 * 		failed();	
	 * 	} finally {
	 * 		finish();
	 * 	}
	 * }
	 * 
	 * begin();
	 * try { // reenter usage, all statements in try block are in a transaction.
	 * 	func();
	 * 	...
	 * } catch (BTiaoExp e) {
	 * 	failed();
	 * } finally {
	 * 	finish();
	 * }
	 */
	public abstract void begin();
	public abstract void finish();
	public abstract void success();
	public abstract void failed();
	
	//public abstract void setRelProp(Object o1, Object o2, RelType r) throws BTiaoExp;
	
	//public abstract void delRelProp(Object o1, Object o2, RelType r) throws BTiaoExp;
	
	//public abstract void getRelProp(Object o1, Object o2, RelType r) throws BTiaoExp;
	
	protected InfoMBaseService(){}
}
