package com.btiao.product.restlet;

public class RelName {
	//relationship between objects of different relationships
	//of root
	static public final String pos_of_root = "pos_of_root";
	static public final String usrExtInfo_of_root = "usrExtInfo_of_root";
	//of position
	static public final String blockInfo_of_position = "blockInfo_of_position";
	static public final String order_of_position = "order_of_position";
	static public final String historyOrder_of_position = "historyOrder_of_position";
	static public final String historyBlockInfo_of_position = "historyBlockInfo_of_position";
	static public final String subPos_of_pos = "subPos_of_pos";
	//of user
	static public final String order_of_user = "order_of_user";
	static public final String historyOrder_of_user = "historyOrder_of_user";
	//of any
	static public final String right_of_ = "right_of_";
	
	//self relationships
	static public final String historyOfUser_order = "historyUser_order";
	static public final String ofUser_order = "userOrder";
	static public final String timeSeq = "timeSeq";	
	static public final String timeSeqHistory = "timeSeqHistory";
	static public final String neighborPos = "neighborPos";
}
