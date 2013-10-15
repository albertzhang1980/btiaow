package com.btiao.product.restlet;

import java.util.ArrayList;
import java.util.Collection;

import org.restlet.data.Form;

import com.btiao.base.exp.BTiaoExp;
import com.btiao.base.oif.restlet.JsonCvtInfo;
import com.btiao.common.service.CommonMgr;
import com.btiao.infomodel.InfoMObject;
import com.btiao.product.domain.BlockInfo;
import com.btiao.product.domain.Position;

public class ResBTiaoBlockInfo extends ResBTiaoPosition {
	@Override
	protected void pre() {
		String posId = this.getAttribute("positionId");
		String infoId = this.getAttribute("infoId");
		urlIds.add(posId);
		urlIds.add(infoId);
	}
	
	@Override
	protected Object get(Form form) throws BTiaoExp {
		return CommonMgr.instance().getInfoObject(BlockInfo.class, urlIds);
	}

	@Override
	@JsonCvtInfo(objClassName="com.btiao.product.domain.BlockInfo")
	protected Object put(Object arg) throws BTiaoExp {
		super.put(arg);
		Position pos = new Position();
		pos.initId(new ArrayList<String>(urlIds.subList(0, urlIds.size()-2)));
		CommonMgr.instance().addObjectRel("blockInfo", pos, (InfoMObject)arg);
		return null;
	}

	@Override
	@JsonCvtInfo(objClassName="com.btiao.product.domain.BlockInfo")
	protected Object post(Object arg, Collection<String> attrList)
			throws BTiaoExp {
		return super.put(arg);
	}

	@Override
	protected Object del(Object arg) throws BTiaoExp {
		//TODO release the relationship between postion obj and blockinfo obj.
		return super.put(arg);
	}

}