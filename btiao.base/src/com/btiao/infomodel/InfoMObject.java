package com.btiao.infomodel;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import com.btiao.base.exp.BTiaoExp;
import com.btiao.base.exp.ErrCode;
import com.btiao.base.utils.BTiaoLog;

public abstract class InfoMObject implements Cloneable {
	static public final String OWNER_USER_ATTR = "ownerUser";
	
	/**
	 * use urlId to initialize infomobject's key attributes.<br>
	 * note: there maybe more than 1 attributes which composite the urlId.<br>
	 * @param urlIds all the identifications from URL.<br>
	 *               e.g. URL='/users/${userId}/loginInfos/{infoId}', then <br>
	 *               urlIds equals to {$userId, $infoId} <br>
	 * @return
	 */
	public abstract boolean initId(List<String> urlIds);
	
	/**
	 * update some attributes from newObj
	 * @param newObj
	 * @param attrs only the specified attributes will be modified.
	 * @throws BTiaoExp
	 */
	public void update(InfoMObject newObj, Collection<String> attrs) throws BTiaoExp{
		for (String attrName : attrs) {
			try {
				Field f = newObj.getClass().getField(attrName);
				Object v = f.get(newObj);
				f.set(this, v);
			} catch (Exception e) {
				throw new BTiaoExp(ErrCode.INTERNEL_ERROR, e);
			}
		}
	}
	
	public int hashCode() {
		int r = 0;
		
		Field[] fs = this.getClass().getFields();
		for (Field f : fs) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			
			try {
				InfoMObjAttrDesc an = f.getAnnotation(InfoMObjAttrDesc.class);
				if (an == null || !an.key()) continue;
				
				Object v = f.get(this);
				r += v.hashCode();
			} catch (Exception e) {
				e.printStackTrace();
				BTiaoLog.logExp(BTiaoLog.get(), e, InfoMObject.class.getName()+" failed to exe isSame!");
			}
		}
		
		return r;
	}
	
	@Override
	public boolean equals(Object oo) {
		InfoMObject o = (InfoMObject)oo;
		if (o.getClass() != this.getClass()) return false;
		
		Field[] fs = this.getClass().getFields();
		for (Field f : fs) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			
			try {
				InfoMObjAttrDesc an = f.getAnnotation(InfoMObjAttrDesc.class);
				if (an == null || !an.key()) continue;
				
				if (!f.get(this).equals(f.get(o))) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				BTiaoLog.logExp(BTiaoLog.get(), e, InfoMObject.class.getName()+" failed to exe isSame!");
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public InfoMObject clone() {
		try {
			return (InfoMObject)(super.clone());
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append(this.getClass().getName());
		sb.append(":");
		
		Field[] fs = this.getClass().getFields();
		for (Field f : fs) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			
			try {
				Object v = f.get(this);
				String name = f.getName();
				sb.append(name);
				sb.append("=");
				sb.append(v);
				sb.append(",");
			} catch (Exception e) {
				e.printStackTrace();
				BTiaoLog.logExp(BTiaoLog.get(), e, "InfoMObject failed to exe toString!");
			}
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	public String ownerUser = "";
}
